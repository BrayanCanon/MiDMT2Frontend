package juan.example.com.diabetest2.profesional.modmision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.Pruebcon;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;

public class Basico extends AppCompatActivity {

    Mision seleccion;
    EditText modTextnom;
    Spinner categoria,tipo,nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basico);
        seleccion=(Mision) getIntent().getParcelableExtra("mision");

        //------------
        modTextnom=(EditText)findViewById(R.id.modTextnom);
        categoria=(Spinner)findViewById(R.id.modspinner1);
        tipo=(Spinner)findViewById(R.id.modspinner3);
        nivel=(Spinner)findViewById(R.id.modspinner4);
        //-------------
        ArrayAdapter cat1 = (ArrayAdapter) categoria.getAdapter();
        ArrayAdapter tip1 = (ArrayAdapter) tipo.getAdapter();
        ArrayAdapter cd=(ArrayAdapter)nivel.getAdapter();

        int pos=cat1.getPosition(seleccion.getCategoria());
        int pos2=tip1.getPosition(seleccion.getTipo());
        int pos3=cd.getPosition(seleccion.getNomnivel());
        Log.d("nivel",seleccion.getNomnivel());
        modTextnom.setText(seleccion.getNombre());
        categoria.setSelection(pos);
        tipo.setSelection(pos2);
        nivel.setSelection(pos3);


    }

    public void continuar(View v){
        ArrayList<String> nombre=new ArrayList<>();
        ArrayList valores=new ArrayList();
        JsonObject categoria1=new JsonObject();
        JsonObject nivel1=new JsonObject();
        JsonObject tipomis1=new JsonObject();
        nombre.add("idMision");valores.add(seleccion.getIdMision());
        nombre.add("nombre");valores.add(modTextnom.getText());
        nombre.add("estado");valores.add("a");
        categoria1.addProperty("idCategoria",categoria.getSelectedItemPosition()+1);
        nombre.add("idCategoria");valores.add(categoria1);
        nivel1.addProperty("idNivel",nivel.getSelectedItemPosition()+1);
        nombre.add("idNivel");valores.add(nivel1);
        tipomis1.addProperty("idTipoMision",tipo.getSelectedItemPosition()+1);
        nombre.add("idTipoMision");valores.add(tipomis1);

        new Conexion("modificarMision", nombre, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {

            }
        }).execute(valores);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent a = new Intent(this,Pruebcon.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
