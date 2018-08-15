package juan.example.com.diabetest2.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import juan.example.com.diabetest2.administrador.Inicio;


public class Conexion extends AsyncTask<ArrayList<String >,Integer,String> {

    ArrayList<String> noms;
    String recurso;

    public interface Comunicado {
        void salidas(String output);
    }
    public Comunicado delegado=null;
    public Conexion(String recurso1,ArrayList nombres,Comunicado delegado){
        noms=nombres;
        recurso=recurso1;
        this.delegado=delegado;

    }

    @Override
    protected String doInBackground(ArrayList... params) {
        String respuesta=null;
        try {
            ArrayList<String> parametros=params[0];
            Log.d("conexion",recurso);
            SoapObject solicitud = new SoapObject(Inicio.namespace, recurso);
            JSONObject json = new JSONObject();
            for(int a=0;a<parametros.size();a++){
                json.put(noms.get(a),parametros.get(a));
            }
            Log.d("paramssize",""+parametros.size());
            if(parametros.size()>0) {
                solicitud.addProperty("jsonString", json.toString());
                Log.d("conectando",json.toString());
            }
            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.setOutputSoapObject(solicitud);
            HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
            transporte.debug = true;
            transporte.call("http://Servicios/"+recurso, sobre);
            String requestDump = transporte.requestDump;
            Log.d("errorconect",requestDump);
            //SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
            //respuesta = resultado.toString();
            SoapPrimitive res=(SoapPrimitive) sobre.getResponse();
            respuesta=res.toString();
            Log.d("salidacon",respuesta);
            return respuesta;


        } catch (Exception e) {
            Log.d("jsr",e.toString());
        }
        return respuesta;
    }
    @Override
    protected void onPostExecute(String salida) {
        delegado.salidas(salida);
    }
}