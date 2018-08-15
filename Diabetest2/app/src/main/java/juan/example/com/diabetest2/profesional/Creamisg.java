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
import android.widget.Toast;

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
    ArrayAdapter<String> comboAdapter2;
    ArrayAdapter<String> comboAdapter3;
    JSONObject codigo;
    JSONObject codigomis;
    //--------------------------------------
    String codmision;
    //--------------------------------------
    String[] categorias;
    List<String> catlist;
    List<Integer> catid;
    ArrayAdapter<String> comboAdapter;
    Context este=this;
    //---------------------------------------

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

        catlist.add("Educación en diabetes");
        catlist.add("Ejercicio");
        catlist.add("Dieta");
        catlist.add("Uso de los medicamentos");
        comboAdapter = new ArrayAdapter<>(este,android.R.layout.simple_spinner_item, catlist);
        categmenu.setAdapter(comboAdapter);

        //-------------------------------------

       List<String> listtipo =new ArrayList <String>();
       listtipo.add("generica");
       listtipo.add("especifica");
       comboAdapter2=new ArrayAdapter<>(este,android.R.layout.simple_spinner_item,listtipo);
        tipomis.setAdapter(comboAdapter);
        comboAdapter2.notifyDataSetChanged();

        //-------------------------------------------------------------------
        List<String> nivel =new ArrayList <String>();
        nivel.add("basico");
        nivel.add("avanzado");
        nivel.add("nightmare");
        comboAdapter3=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listtipo);
        tipomis.setAdapter(comboAdapter2);
        comboAdapter2.notifyDataSetChanged();



    }
    public void crear2(View v) { Intent intento = new Intent(this, Crearmisg3.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); }
        else{
            Log.d("categoria sel",categmenu.getSelectedItem().toString());

            ArrayList<String> nombres=new ArrayList<>();
            ArrayList valores=new ArrayList();

                JSONObject codigo = new JSONObject();
                JSONObject codigomis = new JSONObject();
            try {
                codigo.put("idCategoria",9);
                codigomis.put("idTipoMision", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            nombres.add("nombre");valores.add(nombre.getText().toString());
            nombres.add("estado");valores.add("a");
            nombres.add("misionPasoLogroList");valores.add(new JSONArray());
            nombres.add("misionTipoRecursoList");valores.add(new JSONArray());
            nombres.add("idCategoria");valores.add(codigo);
            //nombres.add("idNivel");valores.add();
            nombres.add("idTipoMision");valores.add(codigomis);
            nombres.add("misionPacienteList");valores.add(new JSONArray());



            new Conexion("crearMision", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    try {
                        JSONObject codigo = new JSONObject(output);
                        codmision=codigo.get("cod").toString();
                    }
                    catch (Exception e){

                    }
                }
            }).execute(valores);
            Toast.makeText(este,"mision creada",Toast.LENGTH_SHORT).show();
            intento.putExtra("categoria",categmenu.getSelectedItem().toString());
            intento.putExtra("codigo",String.valueOf(codmision));
            intento.putParcelableArrayListExtra("pasoslista",new ArrayList<Movie>());
            startActivity(intento);

        }
    }
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
