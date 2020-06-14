package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.administrador.ServicioDT2;

public class VerificacionGlucosa extends AppCompatActivity {
    Long id = ServicioDT2.idLocal;
    TextView valorVerificacionRutina;
    String valor;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion_glucosa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        valorVerificacionRutina = (TextView) findViewById(R.id.valor_verificacion_rutina);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void ingresar_verificacion_rutina(View v){
        valor = valorVerificacionRutina.getText().toString();
        if(valor.length()>1) {
            VerificacionGlucosa.Guardar guardarValorVerificacion = new VerificacionGlucosa.Guardar();
            guardarValorVerificacion.execute();
        }else {
            Toast.makeText(this, "Falta el valor a ingresar", Toast.LENGTH_SHORT).show();
        }
    }
    private class Guardar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearVerificacionRutina");
                solicitud.addProperty("codPaciente", ""+id);
                solicitud.addProperty("idRutina" , 2);
                solicitud.addProperty("valor" , valor);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.urlVerificacion);
                transporte.call("http://Servicios/crearVerificacionRutina", sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta = resultado.toString();

            } catch (Exception e) {   }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
                regresar();
            }
        }
    }
    public void regresar(View v) {
        Intent intento = new Intent(this, Evolucion.class);
        startActivity(intento);
    }
    public void regresar() {
        Intent intento = new Intent(this, Evolucion.class);
        startActivity(intento);
    }
    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
