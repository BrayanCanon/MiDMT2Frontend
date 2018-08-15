package juan.example.com.diabetest2.profesional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private  int codcategoria;
    private String nomcategoria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisg3);
        ctrear=(Button)findViewById(R.id.agregar45);
        seguir=(Button)findViewById(R.id.button43);
        ctrear.setEnabled(false);
        seguir.setEnabled(false);
        nomcategoria=getIntent().getStringExtra("categoria");
        Intent parametros=getIntent();
        if (parametros != null) {
            //
            // Log.d("recibidos",getIntent().getExtras().getString("categoria"));
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view45);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
        
    }

    private void prepareMovieData() {

        ArrayList<String> nombres=new ArrayList<String>();
        ArrayList<String> valores=new ArrayList<String>();

        nombres.add("nomCategoria");
        valores.add(getIntent().getStringExtra("categoria"));

        new Conexion("consultarpanom", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Log.d("salidad",output);

                   JSONArray jsonArray=new JSONArray(output);
                   Boolean primero=true;

                    for(int a=0;a<jsonArray.length();a++){
                        JSONObject salida=jsonArray.getJSONObject(a);
                        if(primero){
                            codcategoria=Integer.parseInt(salida.get("categoria").toString());
                            primero=false;
                        }
                        Movie movie = new Movie(salida.get("nombre").toString(), salida.get("nombre").toString(), "0",0,false);
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
        datos.putString("cadnom",nomcategoria);
        ag.putExtras(datos);
        startActivity(ag);
        this.finish();
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
           if (cortar) {
               while (contador < respuestas.size()) {
                   if (respuestas.get(comparador).getYear().equals("0")) {
                       respuestas.remove(contador);
                   } else {
                       break;
                   }
               }


               Intent envio = new Intent(this, Crearmisg5.class);
               envio.putExtra("seleccion", respuestas);
               envio.putExtra("codigo",getIntent().getStringExtra("codigo"));
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
}