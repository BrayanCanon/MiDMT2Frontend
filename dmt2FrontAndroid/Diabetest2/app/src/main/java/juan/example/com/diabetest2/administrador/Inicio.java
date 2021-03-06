
package juan.example.com.diabetest2.administrador;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import juan.example.com.diabetest2.comunes.CrearRecurso;
import juan.example.com.diabetest2.paciente.Consentimiento;
import juan.example.com.diabetest2.profesional.ConsentimientoPro;
import juan.example.com.diabetest2.profesional.EncuestaProfesionales;
import juan.example.com.diabetest2.paciente.EstiloVida;
import juan.example.com.diabetest2.paciente.FyC;
import juan.example.com.diabetest2.paciente.Hba1c;
import juan.example.com.diabetest2.familiar.MenuFamiliar;
import juan.example.com.diabetest2.paciente.MenuPaciente;
import juan.example.com.diabetest2.profesional.MenuProfesional;
import juan.example.com.diabetest2.paciente.PesoIMC;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.comunes.Usuario;
import juan.example.com.diabetest2.paciente.ActividadFisica;
import juan.example.com.diabetest2.paciente.Aep;
import juan.example.com.diabetest2.paciente.Animo;
import juan.example.com.diabetest2.paciente.ApoyoSocial;
import juan.example.com.diabetest2.util.Conexion;

// Autor: Juan David Velásquez Bedoya

public class Inicio extends AppCompatActivity {
    public static final String metodo = "acceso";
    public static final String namespace = "http://Servicios/";
    public static final String accionSoap = "http://Servicios/acceso";
    //public static final String url = "http://192.168.1.5:8080/DT2/Procesos?wsdl";
    //public static final String urlImagenes = "http://18.218.252.83:8080/DT2/Imagenes/";
    //23.102.159.225
    //public static final String url = "http://192.168.56.1" +
      //      ":8080/DT3/Procesos?wsdl";
    public static final String url = "http://192.168.0.10:8080/DT3/Procesos?wsdl";
    public static final String urlVerificacion = "http://192.168.0.10" +
            ":8080/DT3/VerificacionRutinaWS?wsdl";
    public static final String domain="http://192.168.0.10:8080/DT3";
    public static final String urlImagenes = "http://192.168.0.10:8080/DT3/Imagenes/";

    public static long id,idPaciente;
    public static String rol, preguntarAnimo,preguntarPeso,preguntarHba1c;
    public static int completado, consentimiento;
    public String mensaje="";
    public Context este=this;
    public static Long idLocal;
    public Button logearse;

    static EditText correo;
    EditText clave;
    public String respuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        correo = (EditText) findViewById(R.id.id_correo);
        clave = (EditText) findViewById(R.id.id_clave);
        logearse=(Button)findViewById(R.id.button13);

        //Créditos
        TextView ad = (TextView) findViewById(R.id.abrirCreditos);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getApplicationContext(), acercade.class);
                startActivity(intento);
            }
        });

        //Conexion temporal--------------------------------------------=======================
        //startActivity(new Intent(this, ActividadFisica.class));

        //Probar conexión
        if(probarInternet() == false){
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
        } else{
                chequearSesion();
        }
        //-----------------------------------------------------
        //-----------------------------------------------------
    }




//Consulta del usuario ----------------------------------------------------------------
    public static String a,b;
    public void procesar (View v) {
        stopService(new Intent(this, ServicioDT2.class)); //Prueba servicio

        a = correo.getText().toString();
        b = clave.getText().toString();
        if(a.matches("admin")&& b.matches("azerty123")){
            Intent intento = new Intent(this, Administracion.class);
            startActivity(intento);

        }
        else {
            Consultar co = new Consultar();

            if (probarInternet() == false) {
                Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
            } else {
                logearse.setEnabled(false);
                co.execute();
            }
        }

    }

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(namespace, metodo);
                solicitud.addProperty("correo", a);
                solicitud.addProperty("clave", b);
                Log.e("Resultados", ""+ a + " - " + b );
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                Log.e("Cero", "Pasa");
                sobre.setOutputSoapObject(solicitud);
                Log.e("Primero", "Pasa");
                HttpTransportSE transporte = new HttpTransportSE(url, 720000000);
                Log.e("Segundo", transporte.toString()+"");
                transporte.call(accionSoap, sobre);
                Log.e("Tercero", "Pasa");
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                Log.e("Cuarto", "Pasa");
                respuesta = resultado.toString();
                Log.e("Resultado2", ""+ respuesta);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                //Separación de la respuesta
                if (respuesta.contains(";")) {
                    String[] r = respuesta.split(";");
                    ServicioDT2.idLocal = Long.valueOf(r[0].trim());
                    id = Long.valueOf(r[0].trim());
                    idLocal=id;
                    rol = r[1];
                    Log.d("rol",r[1]);
                    consentimiento = Integer.valueOf(r[2]);
                    completado = Integer.decode(r[3].trim());
                    if (rol.contains("paciente")) {
                        preguntarAnimo = r[4];
                        preguntarPeso = r[5];
                        preguntarHba1c = r[6];
                    }
                    if(rol.contains("familiar")){
                        idPaciente = Long.valueOf(r[4].trim());
                    }
                    Log.i("INFORMACIÓN MIDT2:", "Rol de la bd: " + rol);
                    Toast.makeText(getApplicationContext(), "¡Hola!", Toast.LENGTH_LONG).show();
                }else {
                    logearse.setEnabled(true);
                    Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                }
                if (rol != null) { abrir(null); }
                //Toast.makeText(getApplicationContext(), "rol:" + rol + " id: "+id +" completado:"+completado + " animo"+preguntarAnimo, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void persistirID() {
        if (idLocal != null) {
            try {
    /*
            OutputStream salida = new FileOutputStream("/sdcard/id.dt2");
            byte[] arreglo = String.valueOf(Inicio.id).getBytes();
            salida.write(arreglo);
    */
                //FileOutputStream salida = openFileOutput("id.dt2", Context.MODE_PRIVATE);

                FileOutputStream salida = new FileOutputStream(new File(getFilesDir(), "id.dt2"));
                ObjectOutputStream oos = new ObjectOutputStream(salida);
                oos.writeObject(idLocal);
                oos.close();

                FileOutputStream salida2 = new FileOutputStream(new File(getFilesDir(), "correo.dt2"));
                ObjectOutputStream oos2 = new ObjectOutputStream(salida2);
                oos2.writeObject(a);
                oos2.close();

                FileOutputStream salida3 = new FileOutputStream(new File(getFilesDir(), "clave.dt2"));
                ObjectOutputStream oos3 = new ObjectOutputStream(salida3);
                oos3.writeObject(b);
                oos3.close();


            } catch (java.io.IOException ex) {
                Logger.getLogger(CrearRecurso.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


        public void abrir(View v) {
            startService(new Intent(this, ServicioDT2.class)); //Inicio del servicio notificaciones
            persistirID();
            Intent intento = null;
            if (rol.contains("administrador")) {
                intento = new Intent(this, Administracion.class);
            }
            //Profesional
            if (rol.contains("psicologo") && consentimiento == 1 && completado == 1) {
                intento = new Intent(this, EncuestaProfesionales.class);
            }
            if (rol.contains("psicologo") && consentimiento == 0) {
                intento = new Intent(this, ConsentimientoPro.class);
            }
            if (rol.contains("psicologo") && consentimiento == 1 && completado != 1) {
                intento = new Intent(this, MenuProfesional.class);
            }

            //Familiar

            if (rol.contains("familiar") && completado == 0) {
                intento = new Intent(this, Usuario.class);
            }
            if (rol.contains("familiar") && completado == 1) {
                Toast.makeText(this, ""+ idPaciente, Toast.LENGTH_SHORT).show();
                intento = new Intent(this, MenuFamiliar.class);
            }
            //Paciente
            if (rol.contains("paciente") && consentimiento == 0) {
                intento = new Intent(this, Consentimiento.class);
            }
            if (rol.contains("paciente") && consentimiento == 1) {
                if (rol.contains("paciente") && completado == 0) {
                    intento = new Intent(this, Usuario.class);
                }
                if (rol.contains("paciente") && completado == 1) {
                    intento = new Intent(this, FyC.class);
                }
                if (rol.contains("paciente") && completado == 2) {
                    intento = new Intent(this, ApoyoSocial.class);
                }
                if (rol.contains("paciente") && completado == 3) {
                    intento = new Intent(this, EstiloVida.class);
                }

                if (rol.contains("paciente") && completado == 4 && preguntarAnimo.contains("1")) {
                    intento = new Intent(this, ActividadFisica.class);
                }
                if (rol.contains("paciente") && completado == 4 && preguntarAnimo.contains("0")) {
                    intento = new Intent(this, MenuPaciente.class);
                }
                if (rol.contains("paciente") && completado == 5 && preguntarAnimo.contains("1")) {
                    intento = new Intent(this, Aep.class);
                }
                if (rol.contains("paciente") && completado == 5 && preguntarAnimo.contains("0")) {
                    intento = new Intent(this, MenuPaciente.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("1")) {
                    intento = new Intent(this, Animo.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("0")) {
                    intento = new Intent(this, MenuPaciente.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("0") && preguntarPeso.contains("1")) {
                    intento = new Intent(this, PesoIMC.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("0") && preguntarPeso.contains("0")) {
                    intento = new Intent(this, MenuPaciente.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("0") && preguntarHba1c.contains("1")) {
                    intento = new Intent(this, Hba1c.class);
                }
                if (rol.contains("paciente") && completado == 6 && preguntarAnimo.contains("0") && preguntarHba1c.contains("0")) {
                    intento = new Intent(this, MenuPaciente.class);
                }


            }
            //Toast.makeText(getApplicationContext(), "actividad:"+intento.toString(), Toast.LENGTH_LONG).show();
            startActivity(intento);
            this.finish();
        }

        public void creditos(){
            Intent intento = new Intent(this, acercade.class);
            startActivity(intento);
        }



    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //Chequear sesion y reactivarla ------------------------------------
    public void chequearSesion(){
        Long idPersistido;
        String correoGuadado = "";
        String claveGuardada = "";
        try {
            FileInputStream in = openFileInput("id.dt2");
            ObjectInputStream ois = new ObjectInputStream(in);
            Object idGuardado = ois.readObject();
            ois.close( );
            Log.e("Chequeo de sesión: ","Id guardado: "+idGuardado);

            FileInputStream in2 = openFileInput("correo.dt2");
            ObjectInputStream ois2 = new ObjectInputStream(in2);
            correoGuadado = (String) ois2.readObject();
            ois2.close( );
            Log.e("Chequeo de sesión: ","Correo guardado: "+correoGuadado);

            FileInputStream in3 = openFileInput("clave.dt2");
            ObjectInputStream ois3 = new ObjectInputStream(in3);
            claveGuardada = (String) ois3.readObject();
            ois3.close( );
            Log.e("Chequeo de sesión: ","Clave guardada: "+claveGuardada);


            //Vuelve a chequear con los datos guardados las credenciales en el servidor
            if(idGuardado != null && correoGuadado != null){
                Toast.makeText(getApplicationContext(), "Cargando sesión anterior", Toast.LENGTH_LONG).show();

                idPersistido = Long.valueOf(idGuardado.toString().trim());
                id = idPersistido;
                ServicioDT2.idLocal = id;
                a = correoGuadado;
                b = claveGuardada;
                //Vuelve a consultar con lo que tiene guardado a ver si corresponde con la bd
                Consultar co = new Consultar();
                co.execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("Ficheros", "No se pudo leer el id o el rol");
        }


    }

    //----------------------------------------------------------------------------------------------
    // Captura del tiempo inicial
    public static void tiempoInicial(){
    Inicio.Ti ti = new Inicio.Ti();
        ti.execute();
    }
    private static class Ti extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(namespace, "ti");
                solicitud.addProperty("id", id);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(url);
                transporte.call(accionSoap, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
            }
        }
    }
    //----------------------------------------------------------------------------------------------



}









// Con volley
        /*
        public static String url = "http://192.168.1.6:8080/DT2/Servlet1";

    private RequestQueue colaDeSolicitudes;
    private StringRequest peticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ctx = Inicio.this;
        colaDeSolicitudes = Volley.newRequestQueue(ctx);
        correo = (EditText) findViewById(R.id_destinatario.id_correo);
        clave = (EditText) findViewById(R.id_destinatario.id_clave);

        Intent intento = new Intent(this, prueba.class);
        startActivity(intento);
    }






        peticion = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {public void onResponse(String respuesta) {
                    if(respuesta.contains(";")) {
                        String [] r = respuesta.split(";");
                        id_destinatario = Long.valueOf(r[0].trim());
                        rol = r[1];
                        if(r.length == 3) {completado = Integer.decode(r[2].trim());}
                    }else{Toast.makeText(ctx, respuesta, Toast.LENGTH_SHORT).show(); } }},
                new Response.ErrorListener() {public void onErrorResponse(VolleyError error) {
                    Log.d("error_servidor", error.toString());}
                }) {
            public Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("a", correo.getText().toString());
                parametros.put("b", clave.getText().toString());
                parametros.put("op", "identificar");
                return parametros;
            }
        };
        colaDeSolicitudes.add(peticion);
        */
