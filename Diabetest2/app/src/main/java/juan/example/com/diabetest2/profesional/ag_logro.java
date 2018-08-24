package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.util.Conexion;


public class ag_logro extends AppCompatActivity {

    TextView sal;
    String nom;
    String desc;
    String puntos;
    Context n=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_logro);
    }


    public void abrir(View v) {
        ArrayList<String> valores=new ArrayList<String>();
        ArrayList<String> nombres=new ArrayList<String>();
        desc=((TextView)findViewById(R.id.descripcion)).getText().toString();
        puntos=((TextView)findViewById(R.id.puntos)).getText().toString();
        nom=(((TextView)findViewById(R.id.nombre)).getText().toString());
        new ag_logro.Consultar().execute();
        Log.d("repeticiones","abierto");



    }



    String respuesta="";
    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JsonObject json = new JsonObject();
                JsonArray ja = new JsonArray();
                json.addProperty ("nomLogro",nom);
                json.addProperty("descripcion",desc);
                json.addProperty("puntos",puntos);
                json.addProperty("estado","a");
                json.add("misionPasoLogroCollection",ja);
                Log.d("jsonString",json.toString());
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearLogro");
                solicitud.addProperty("jsonString", json.toString());
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearLogro", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Intent volver=new Intent(n,BuscLogro.class);
                volver.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                startActivity(volver);
            }
        }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}






