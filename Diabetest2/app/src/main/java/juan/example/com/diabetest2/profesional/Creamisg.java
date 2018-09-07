package juan.example.com.diabetest2.profesional;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Movie;

public class Creamisg extends AppCompatActivity {
    EditText nombre;
    Spinner categmenu;
    Spinner tipomis;
    Spinner nivelmis;
    String codmision;
    List<String> catlist;
    Context este=this;
    Intent intento;
    EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creamisg);
        //---------------------------------
        nombre=(EditText)findViewById(R.id.editTextnom);
        //------------------------------
        catlist=new ArrayList<String>();
        categmenu=(Spinner) findViewById(R.id.spinner1);
        //-------------------------------------
        tipomis=(Spinner) findViewById(R.id.spinner3);
        nivelmis=(Spinner)findViewById(R.id.spinner4);
        desc=(EditText)findViewById(R.id.editText);



    }
    public void crear2(View v) {
        intento = new Intent(this, BuscLogro.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); }
        else{
            Log.d("categoria sel",categmenu.getSelectedItem().toString());

            ArrayList<String> nombres=new ArrayList<>();
            ArrayList valores=new ArrayList();

                JsonObject codigo = new JsonObject();
                JsonObject codigomis = new JsonObject();
                JsonObject codigonivel=new JsonObject();
                codigo.addProperty("idCategoria",1+categmenu.getSelectedItemPosition());
                codigomis.addProperty("idTipoMision",1+tipomis.getSelectedItemPosition());
                codigonivel.addProperty("idNivel",1+nivelmis.getSelectedItemPosition());

                nombres.add("nombre");valores.add(nombre.getText().toString());
            nombres.add("descripcion");valores.add(desc.getText());
            nombres.add("estado");valores.add("a");
            nombres.add("misionPasoLogroList");valores.add(new JsonArray());
            nombres.add("misionTipoRecursoList");valores.add(new JsonArray());
            nombres.add("idCategoria");valores.add(codigo);
            //nombres.add("idNivel");valores.add();
            nombres.add("idTipoMision");valores.add(codigomis);
            nombres.add("idNivel");valores.add(codigonivel);
            nombres.add("misionPacienteList");valores.add(new JsonArray());



            new Conexion("crearMision", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    try {
                        JSONObject codigo = new JSONObject(output);
                        codmision=codigo.get("cod").toString();
                        Log.d("codigomision",codmision);
                        Toast.makeText(este,"mision creada",Toast.LENGTH_SHORT).show();
                        intento.putExtra("categoria",categmenu.getSelectedItem().toString());
                        intento.putExtra("codigo",String.valueOf(codmision));
                        intento.putExtra("codcategoria",categmenu.getSelectedItemPosition()+1);
                        intento.putParcelableArrayListExtra("pasoslista",new ArrayList<Movie>());
                        Log.d("categoria",""+categmenu.getSelectedItem());
                        Log.d("codcategoria",""+(categmenu.getSelectedItemPosition()+1));

                        startActivity(intento);
                    }
                    catch (Exception e){

                    }
                }
            }).execute(valores);



        }
    }
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
