package juan.example.com.diabetest2.comunes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;
import juan.example.com.diabetest2.util.Conexion;


// Autor: Juan David Velásquez Bedoya

public class Mensajes extends AppCompatActivity {

    Context ctx;
    static Timer ti;
    public EditText mensajesd;
    public int numeros;
    public static long idDestino;
    public static String destinatario;
    public ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        mensajesd=(EditText)findViewById(R.id.mensajet);
        mensajesd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajesd.setText("");
            }
        });
        ctx = this;
        ti = new Timer();
        Tarea tt = new Tarea();

        if(probarInternet() == false){
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        } else{
            ti.scheduleAtFixedRate(tt,0,10000);
        }
        //--------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mensajes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_boton_eliminar_mensajes) {
            borrarMensajes bo = new borrarMensajes();
            bo.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
    class Tarea extends TimerTask {

        @Override
        public void run() {
            //Consultar co = new Consultar();
            //co.execute();
            fechas.clear();
            remitentes.clear();
            mensajes.clear();
            repetidos.clear();
            ArrayList nombres=new ArrayList();
            ArrayList valores=new ArrayList();
            nombres.add("id_usuario");valores.add(ServicioDT2.idLocal);
            nombres.add("id_emi_usuario");valores.add(idDestino);
            final ArrayList<String> fechas=new ArrayList();
            final ArrayList<String> contenido=new ArrayList();
            //final ArrayList<String> remitentes=new ArrayList();
            final ArrayList<String> remitentes=new ArrayList<>();

            new Conexion("consultachat", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Gson gson=new Gson();
                    JsonArray mensajes=gson.fromJson(output,JsonArray.class);
                    JsonObject mensaje;
                    for(int a=0;a<mensajes.size();a++){
                       mensaje= mensajes.get(a).getAsJsonObject();
                       fechas.add(mensaje.get("fecha").getAsString());
                       contenido.add(mensaje.get("mensaje").getAsString());
                       //remitentes.add(mensaje.get("destinatario").getAsString());
                        remitentes.add(mensaje.get("remitente").getAsString());

                    }


                    String fechitas[]=new String[fechas.size()];
                    String conteniditos[]=new String[contenido.size()];
                    String remitensitos[]=new String[remitentes.size()];



                    ArrayList al=new ArrayList();
                    lv = (ListView) findViewById(R.id.id_mensajes_recibidos);
                    lv.setAdapter(new AaMensajes((Activity) ctx,fechas.toArray(fechitas),
                            remitentes.toArray(remitensitos)
                            ,contenido.toArray(conteniditos)

                    ));
                }

            }).execute(valores);


        }
    }


    //--------------------------------------------------------------------------------------------------
    Vector listadoX = new Vector();
    Vector fechas = new Vector();
    static Vector remitentes = new Vector();
    Vector mensajes = new Vector();
    Vector id_remitentes = new Vector();
    Vector <Integer> repetidos = new Vector<Integer>();


    private class Consultar extends AsyncTask<Void, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            SoapObject solicitud = new SoapObject(Inicio.namespace, "listaMensajes");
            solicitud.addProperty("id", ServicioDT2.idLocal);
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(solicitud);
            HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
            transporte.call("http://Servicios/listaMensajes", sobre);
            SoapObject temp=(SoapObject) sobre.bodyIn;
            listadoX=new Vector();
            for(int a=0;a<temp.getPropertyCount();a++){
                listadoX.add(temp.getProperty(a).toString());
            }
            } catch (Exception e) {}
        return true;
    }
    @Override
    protected void onPostExecute(final Boolean success) {
        if (success == true){
            //Acomodación de la consulta obtenida
            fechas.clear();
            remitentes.clear();
            mensajes.clear();
            repetidos.clear();
            int i = 0;
            if(listadoX != null) {
                while (i < listadoX.size()) {
                    id_remitentes.add(listadoX.get(i));
                    fechas.add(listadoX.get(i + 1));
                    remitentes.add(listadoX.get(i + 2).toString() +" "+ listadoX.get(i + 3).toString());
                    mensajes.add(listadoX.get(i + 4));
                    if(Long.parseLong((String) listadoX.get(i)) == ServicioDT2.idLocal){repetidos.add(i);}
                    i = i + 5;
                }
                //Llenado del ListView principal --------------------------------------------------------------------
                lv = (ListView) findViewById(R.id.id_mensajes_recibidos);
                lv.setAdapter(new AaMensajes((Activity) ctx,
                        (String[]) fechas.toArray(new String[fechas.size()]),
                        (String[]) remitentes.toArray(new String[remitentes.size()]),
                        (String[]) mensajes.toArray(new String[mensajes.size()])
                ));
                //--------------------------
//Cambio para los mensajes enviados por el que consulta
                for (int j = 0; j<repetidos.size() ; j++) {
                    Log.e("Chequeo de sesión: ","Se va a cambiar la posicion: "+repetidos.elementAt(j));
                }

                //Selector de item ----------------------------------------------------------------------------------
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CrearMensaje.idDestino = Long.parseLong((String) id_remitentes.get(position));
                        CrearMensaje.destinatario = (String) remitentes.get(position);
                        abrir(null);
                    }
                });
            }
        }
    }
}


//Borrar Mensajes
    String respuesta;
    private class borrarMensajes extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "borrarMensajes");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/borrarMensajes", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true){
                Toast.makeText(ctx, ""+respuesta, Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void abrir(View v) {
        Intent intento = new Intent(this, CrearMensaje.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //Salida por retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Mensajes.ti != null){
                Mensajes.ti.cancel();
                Mensajes.ti = null;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void sendchat(View v){
        Tarea de=new Tarea();
        ArrayList valores=new ArrayList();
        ArrayList nombres=new ArrayList();
        nombres.add("id");valores.add(ServicioDT2.idLocal);
        nombres.add("mensaje");valores.add(mensajesd.getText().toString());
        nombres.add("destinatario");valores.add(idDestino);
        new Conexion("crearMensaje", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                mensajesd.setText("");

            }
        }).execute(valores);
        de.run();

    }


}
