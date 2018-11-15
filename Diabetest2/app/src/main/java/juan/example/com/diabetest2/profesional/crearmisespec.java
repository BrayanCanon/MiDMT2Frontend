package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;
import juan.example.com.diabetest2.paciente.Evolucion;
import juan.example.com.diabetest2.util.Conexion;

public class crearmisespec extends AppCompatActivity {

    ListView listaP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisespec);

        crearmisespec.Consultar co = new crearmisespec.Consultar();
        co.execute();
    }

    Vector listadoX = new Vector();
    Vector pacientes = new Vector();
    Vector id_pacientes = new Vector();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "listaPacientes");
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/listaPacientes", sobre);
                listadoX = (Vector) sobre.getResponse();
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                if (listadoX != null) {
                    //Acomodación de la consulta obtenida
                    int i = 0;
                    while (i < listadoX.size()) {
                        pacientes.add(listadoX.get(i+1)+" "+listadoX.get(i+2));
                        i = i + 3;
                    }
                    i = 0;
                    while (i < listadoX.size()) {
                        id_pacientes.add(listadoX.get(i));
                        i = i + 3;
                    } //Vector indice coincidente con ListView, id_destinatario del Paciente
                    //Llenado del listView
                    ArrayAdapter aa = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, pacientes);
                    listaP = (ListView) findViewById(R.id.id_listaPacientes22);
                    listaP.setAdapter(aa);

                    final TextView mititulo = (TextView) findViewById(R.id.id_titulopacientes2);
                    listaP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int elegido, long id) {
                            Evolucion.id = (Long.valueOf((String) id_pacientes.get(elegido)));
                            abrir(null);
                        }
                    });
                }
            }
        }
    }

    public void abrir(View v) {
        //Intent intento = new Intent(this, Evolucion.class);
        //if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList valores=new ArrayList<>();

        nombres.add("codPaciente");valores.add(Evolucion.id);
        nombres.add("idMision");valores.add(getIntent().getStringExtra("codigo"));
        new Conexion("AsignarMisionPaciente", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {

            }
        }).execute(valores);
        Intent salir=new Intent(this,Mision_Gen_Prof.class);
        startActivity(salir);
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Toast toast1 =
                    Toast.makeText(this,
                            "Por favor termine el proceso antes de continuar", Toast.LENGTH_SHORT);
            toast1.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
