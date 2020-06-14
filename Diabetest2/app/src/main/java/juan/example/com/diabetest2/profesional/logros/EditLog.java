package juan.example.com.diabetest2.profesional.logros;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.BuscLogro;
import juan.example.com.diabetest2.profesional.Pruebcon;
import juan.example.com.diabetest2.util.Conexion;

import static android.widget.Toast.makeText;

public class EditLog extends AppCompatActivity {

    EditText ids;
    EditText nom;
    EditText puntos;
    EditText descripcion;
    Context este=this;
    Button envio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_log);
        ids=(EditText)findViewById(R.id.id2);
        nom=(EditText)findViewById(R.id.nombre2);
        puntos=(EditText)findViewById(R.id.puntos2);
        descripcion=(EditText)findViewById(R.id.descripcion2);
        ids.setEnabled(false);
        llenar();
    }

    public void llenar(){

        Bundle bundleInt = this.getIntent().getExtras();
        String id=Integer.toString(bundleInt.getInt("id"));
        String nombre= bundleInt.getString("nombre");
        String punto=bundleInt.getString("puntos");
        String desc=bundleInt.getString("descripcion");
        ids.setText(id);
        ArrayList noms=new ArrayList<String>();
        ArrayList vals=new ArrayList<String>();
        noms.add("idLogro");vals.add(id);
        nom.setText(nombre);
        puntos.setText(punto);
        descripcion.setText(desc);

    }

    public void enviar(View v){
        ArrayList nombre=new ArrayList();
        ArrayList valores=new ArrayList();
        JsonArray ja = new JsonArray();
        nombre.add("idLogro");valores.add(ids.getText().toString());
        nombre.add("nomLogro");valores.add(nom.getText().toString());
        nombre.add("descripcion");valores.add(descripcion.getText().toString());
        nombre.add("puntos");valores.add(puntos.getText().toString());
        nombre.add("estado");valores.add("a");
        nombre.add("misionPasoLogroCollection");valores.add(ja);
        new Conexion("modificarLogro", nombre, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Intent devolverse=new Intent(este, BuscLogro.class);
                devolverse.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(devolverse);

            }
        }).execute(valores);
    }
}
