package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;


// Autor: Juan David Velásquez Bedoya

public class CrearPaciente extends AppCompatActivity {

    static String paciente;
    Context ctx;
    static EditText correo;
    static EditText identificacion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_paciente);
        Inicio.tiempoInicial();
        ctx = this;

        correo = (EditText) findViewById(R.id.id_correoPaciente);
        identificacion = (EditText) findViewById(R.id.id_identificacionPa);
    }


    //asdajsbdasbdbdaubd
    String respuesta;
    String a, b;
    public void procesar (View v) {
        if(!correo.getText().toString().matches("") && !identificacion.getText().toString().matches("")) {
            a = correo.getText().toString();
            if (isEmailValid(a)) {
                b = identificacion.getText().toString();
                paciente = identificacion.getText().toString();
                CrearPaciente.consultar co = new CrearPaciente.consultar();
                if (probarInternet() == false) {
                    Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                } else {
                    co.execute();
                }
            }
            else {
                Toast toast1 =
                        Toast.makeText(this,
                                "correo invalido", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }

        else {
            Toast toast1 =
                    Toast.makeText(this,
                            "Por favor llene los datos antes de continuar", Toast.LENGTH_SHORT);
            toast1.show();
        }

    }

    private class consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearPaciente");
                solicitud.addProperty("correo", a);
                solicitud.addProperty("identificacion", b);
                solicitud.addProperty("id", ServicioDT2.idLocal);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearPaciente", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
                Log.e("SOBREEEEEEEE", sobre.getResponse()+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Log.e("ERROOOOOOOOOOOOOOOOOOOR", respuesta+" <- RESPUESTA");
                if(respuesta.contains("existe")){
                    Toast.makeText(getApplicationContext(), ""+respuesta, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                   abrir(null);

                    //Envio del correo de notificación
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{a});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Confirmación de acceso a MiDT2 con Psicoeducación");
                    i.putExtra(Intent.EXTRA_TEXT, "Hola, bienvenido/a a la App MiDT2 con Psicoeducación, para poder acceder su usuario será su email y su clave temporal será: bosque \n La App, se puede descargar aquí:\nhttps://drive.google.com/drive/folders/1AiRVZBEQR7mObYK61FbDVyyNV4EE4y9g?usp=sharing\n\n\nCordialmente\n\nEquipo de Investigación de Ingeniería de sistemas y psicología");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "¡No hay cliente de correo!, el paciente no sabrá cómo entrar.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    //Conector entre actividades
    public void abrir(View v) {
        Intent intento = new Intent(this, DiagnosticoInicial.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); this.finish(); }
    }


    //Advertencia de retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Paciente incompleto será borrado!", Toast.LENGTH_SHORT).show();

        }
        return super.onKeyDown(keyCode, event);
    }


    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

}




/*


    private RequestQueue colaDeSolicitudes;
    private StringRequest peticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_paciente);

        ctx = CrearPaciente.this;
        colaDeSolicitudes = Volley.newRequestQueue(ctx);
        correo = (EditText) findViewById(R.id_destinatario.id_correoPaciente);
        identificacion = (EditText) findViewById(R.id_destinatario.id_identificacionPa);
    }



    public void procesar(View v) {
        peticion = new StringRequest(Request.Method.POST, Inicio.url,
                new Response.Listener<String>() {public void onResponse(String response) {
                    Log.d("rta_servidor", response);
                    Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show(); }},
                new Response.ErrorListener() {public void onErrorResponse(VolleyError error) {
                    Log.d("error_servidor", error.toString());}
                }) {
            public Map<String, String> getParams()  {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("a", correo.getText().toString());
                parametros.put("b", identificacion.getText().toString());
                parametros.put("op", "crearpaciente");
                return parametros;
            }
        };
        colaDeSolicitudes.add(peticion);
        abrir(null);
    }
*/