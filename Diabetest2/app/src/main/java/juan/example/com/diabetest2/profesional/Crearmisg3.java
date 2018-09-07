package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
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
        ctrear.setEnabled(false);
        seguir.setEnabled(false);
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

        mAdapter = new MoviesAdapter(movieList,ListViewItems,listId,contexto);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
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
        new Conexion("consultarpanom", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Log.d("salidad",output);

                   JSONArray jsonArray=new JSONArray(output);
                   Boolean primero=true;

                    for(int a=0;a<jsonArray.length();a++){
                        JSONObject salida=jsonArray.getJSONObject(a);
                        Movie movie = new Movie(salida.get("nombre").toString(), salida.get("descripcion").toString(), "0",0,false,salida.getInt("idPaso"),salida.getInt("diasDuracion"),salida.getInt("categoria"),-1);
                        movieList.add(movie);

                    }

                    mAdapter.notifyDataSetChanged();
                    ctrear.setEnabled(true);
                    seguir.setEnabled(true);

                }
                catch (Exception e){
                    Log.d("errorsito",e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
                }
            }
        }).execute(valores);




    }

    public void registro(View v){
        List<Movie> respuestas= mAdapter.res();

        Log.d("val 1", ""+respuestas.get(0).getYear());
    }


    public void agregar(View v){
        Intent ag=new Intent(this,Crearmisg4.class);
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
        Collections.sort(respuestas, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie, Movie t1) {
                return t1.getYear().compareToIgnoreCase(movie.getYear());
            }
        });

       int tam =respuestas.size();
       int contador=0;
       Boolean cortar=true;
       int comparador=Integer.parseInt(respuestas.get(0).getYear());
        boolean vacio=true;
       for(int a=0;a<tam;a++){
           if(!respuestas.get(a).getYear().matches("0")){
               vacio=false;
           }
       }
       if(!vacio) {
           while (contador < tam && Integer.parseInt(respuestas.get(contador).getYear()) > 0) {
               Log.d("compar", respuestas.get(contador).getYear() + " " + comparador);
               if (Integer.parseInt(respuestas.get(contador).getYear()) == comparador) {
                   contador++;
                   comparador--;
               } else {
                   cortar = false;
                   Toast toast1 =
                           Toast.makeText(this,
                                   "ordene los pasos desde el primero al ultimo en orden ", Toast.LENGTH_SHORT);
                   toast1.show();
                   break;
               }

           }
           Log.d("compar2", respuestas.get(contador).getYear() + " " + comparador);
           if(comparador!=0){
               cortar = false;
               Toast toast1 =
                       Toast.makeText(this,
                               "ordene los pasos desde el primero al ultimo en orden ", Toast.LENGTH_SHORT);
               toast1.show();
           }
           contador=0;
           if (cortar) {
               for(int comp=0;comp<respuestas.size();comp++){
                   if (respuestas.get(comp).getYear().equals("0")) {
                       respuestas.remove(comp);
                       comp--;
                   }

               }



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

       }
       else {
           Toast toast1 =
                   Toast.makeText(this,
                           "seleccione al menos un paso", Toast.LENGTH_SHORT);
           toast1.show();
       }

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