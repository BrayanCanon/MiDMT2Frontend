package juan.example.com.diabetest2.profesional.logros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

import static android.widget.Toast.makeText;

public class EditLog extends AppCompatActivity {

    EditText ids;
    EditText nom;
    EditText puntos;
    EditText descripcion;
    Button envio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_log);
        ids=(EditText)findViewById(R.id.id2);
        nom=(EditText)findViewById(R.id.nombre2);
        puntos=(EditText)findViewById(R.id.puntos2);
        descripcion=(EditText)findViewById(R.id.descripcion2);

        llenar();
    }

    public void llenar(){

        Bundle bundleInt = this.getIntent().getExtras();
        String id=Integer.toString(bundleInt.getInt("id"));
        ids.setText(id);
        ArrayList noms=new ArrayList<String>();
        ArrayList vals=new ArrayList<String>();
        noms.add("idLogro");vals.add(id);
        new Conexion("consultarLogroId",noms,new Conexion.Comunicado() {

            @Override
            public void salidas(String output) {

                try {

                    JSONObject salida = new JSONObject(output);
                    nom.setText(salida.get("nomLogro").toString());
                    puntos.setText(salida.get("puntos").toString());
                    descripcion.setText(salida.get("descripcion").toString());
                }
                catch (Exception a){
                    Log.d("error a1",a.toString());
                }
            }
        }).execute(vals);

    }

    public void enviar(View v){
        ArrayList nombre=new ArrayList();
        ArrayList valores=new ArrayList();
        JSONArray ja = new JSONArray();
        nombre.add("idLogro");valores.add(ids.getText().toString());
        nombre.add("nomLogro");valores.add(nom.getText().toString());
        nombre.add("descripcion");valores.add(descripcion.getText().toString());
        nombre.add("puntos");valores.add(puntos.getText().toString());
        nombre.add("estado");valores.add(true);
        nombre.add("misionPasoLogroCollection");valores.add(ja);
        new Conexion("modificarLogro", nombre, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {

            }
        }).execute(valores);
    }
}
