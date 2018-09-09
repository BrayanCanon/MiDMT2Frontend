package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Movie;
import juan.example.com.diabetest2.util.MoviesAdapter;

public class Crearmisg3 extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private Button ctrear,seguir;
    private ArrayList<String> ListViewItems ;
    private ArrayList<Integer> listId;
    private int codcategoria;
    private String nomcategoria;
    private String codmision;
    private Context contexto=this;
    private ArrayList<Movie> salida;


    @Override
    protected void onResume() {

        super.onResume();
        //this.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisg3);
        listId=new ArrayList<>();
        ListViewItems=new ArrayList<>();
        ctrear=(Button)findViewById(R.id.agregar45);
        seguir=(Button)findViewById(R.id.button43);
        ctrear.setEnabled(true);
        seguir.setEnabled(true);
        nomcategoria=getIntent().getStringExtra("categoria");
        codmision=getIntent().getStringExtra("codigo");
        Log.d("estele",codmision);
        Log.d("nommision",nomcategoria);
        Intent parametros=getIntent();
        if (parametros != null) {
            //
            // Log.d("recibidos",getIntent().getExtras().getString("categoria"));
        }

        new Conexion("consultarLogros", new ArrayList(), new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson=new Gson();
                JsonArray salida=gson.fromJson(output,JsonArray.class);
                for (int a=0;a<salida.size();a++){
                    JsonObject logros=salida.get(a).getAsJsonObject();
                    ListViewItems.add(logros.get("nomLogro").getAsString());
                    Log.d("comilla",logros.get("nomLogro").getAsString());
                    listId.add(logros.get("idLogro").getAsInt());
                }

            }
        }).execute(new ArrayList<String>());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view45);


        //-------------

        //------------

        prepareMovieData();
        
    }


    public void recargar(View v){
        movieList = new ArrayList<>();
        prepareMovieData();
    }
    private void prepareMovieData() {

        ArrayList<String> nombres=new ArrayList<String>();
        ArrayList<String> valores=new ArrayList<String>();

        nombres.add("nomCategoria");
        valores.add(getIntent().getStringExtra("categoria"));
        codcategoria=getIntent().getIntExtra("codcategoria", 1);
        Log.d("codigoenvio",""+getIntent().getIntExtra("codcategoria", 1));
        if(getIntent().getParcelableArrayListExtra("parc")!=null) {
            salida=getIntent().getParcelableArrayListExtra("parc");
            mAdapter = new MoviesAdapter(salida,ListViewItems,listId,contexto);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            ctrear.setEnabled(true);
            seguir.setEnabled(true);
        }



    }

    public void registro(View v){
        List<Movie> respuestas= mAdapter.res();

        Log.d("val 1", ""+respuestas.get(0).getYear());
    }


    public void agregar(View v){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        ArrayList<String> nombre=new ArrayList<>();
                        ArrayList valores=new ArrayList<>();
                        nombre.add("idPaso");valores.add(salida.get(0).getId());
                            new Conexion("borrarPaso", nombre, new Conexion.Comunicado() {
                                @Override
                                public void salidas(String output) {

                                }
                            }).execute(valores);
                        creador();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        if(getIntent().getParcelableArrayListExtra("parc")!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            builder.setMessage("¿Está seguro que desea asignar otro paso?").setPositiveButton("Si", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        else {
            creador();
        }

    }

    public void creador(){
        Intent ag=new Intent(contexto

                ,Crearmisg4.class);
        Bundle datos=new Bundle();
        datos.putInt("catcod",codcategoria);
        Log.d("codcategoria",String.valueOf(codcategoria));
        datos.putString("cadnom",nomcategoria);
        datos.putString("codmision",codmision);
        Log.d("codmision",codmision);
        ag.putExtras(datos);
        startActivity(ag);

    }
    public void seguirbot(View v){
        ArrayList<Movie> respuestas= new ArrayList<>(mAdapter.res());
               ArrayList<String> nombres=new ArrayList<>();
               ArrayList valores=new ArrayList();
                //new Conexion("")
               //----------------------------------------
               JsonArray info=new JsonArray();
               //--------------------------------
               for(int a=0;a<respuestas.size();a++) {
                   JsonObject adaptador = new JsonObject();
                   Movie salida=respuestas.get(a);
                   //-----------------------------------------
                   adaptador.addProperty("mision",codmision);
                   adaptador.addProperty("paso",salida.getId());
                   adaptador.addProperty("logro",salida.getLogro());
                   adaptador.addProperty("pasoNumero",Integer.parseInt(salida.getYear()));
                   adaptador.addProperty("estado","a");
                   //-----------------------------------------
                   info.add(adaptador);

               }

               String prueba=info.toString();
               Log.d("asd",prueba);
               nombres.add("cod");valores.add(info);
               new Conexion("crearMPLogro", nombres, new Conexion.Comunicado() {
                   @Override
                   public void salidas(String output) {

                   }
               }).execute(valores);
               //--------------------------------
               Intent envio = new Intent(this, Mision_Gen_Prof.class);
               envio.putExtra("seleccion", respuestas);
               envio.putExtra("codigo",String.valueOf(codmision));
               startActivity(envio);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Toast toast1 =
                    Toast.makeText(this,
                            "Por favor termine el proceso antes de continuar", Toast.LENGTH_SHORT);
            toast1.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}