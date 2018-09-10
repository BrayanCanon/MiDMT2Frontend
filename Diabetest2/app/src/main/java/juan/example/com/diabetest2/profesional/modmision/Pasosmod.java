package juan.example.com.diabetest2.profesional.modmision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.AdapterPasoedit;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.Movie;
import juan.example.com.diabetest2.util.MoviesAdapter;

public class Pasosmod extends AppCompatActivity {

    RecyclerView pasos;
    AdapterPasoedit pasoedit;
    private ArrayList<String> logrosnNom=new ArrayList<>() ;
    private ArrayList<Integer> logrosId=new ArrayList<>();
    private ArrayList<Movie> movieList = new ArrayList<>();
    ArrayList<Movie> paso=new ArrayList<>();
    TextView pasosel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasosmod);
        pasos=(RecyclerView)findViewById(R.id.recycler_view452);
        pasosel=(TextView)findViewById(R.id.mispaso9);

        new Conexion("consultarLogros", new ArrayList(), new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson=new Gson();
                JsonArray salida=gson.fromJson(output,JsonArray.class);
                for (int a=0;a<salida.size();a++){
                    JsonObject logros=salida.get(a).getAsJsonObject();
                    logrosnNom.add(logros.get("nomLogro").getAsString());
                    Log.d("comilla",logros.get("nomLogro").getAsString());
                    logrosId.add(logros.get("idLogro").getAsInt());
                }

            }
        }).execute(new ArrayList<String>());
        //-----------------------------------------
        pasoedit = new AdapterPasoedit(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        pasos.setLayoutManager(mLayoutManager);
        pasos.setItemAnimator(new DefaultItemAnimator());
        pasos.setAdapter(pasoedit);

//-------------------------------------------------
        Mision sel=getIntent().getParcelableExtra("mision");
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList valores=new ArrayList();
        nombres.add("idMision");valores.add(sel.getIdMision());
        new Conexion("consultarTodosPasos", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Gson gson=new Gson();
                    JsonArray lista=gson.fromJson(output,JsonArray.class);
                    Boolean primero=true;
                    for (int a = 0; a < lista.size(); a++) {
                        JsonObject sal = lista.get(a).getAsJsonObject();
                        JsonObject salida = sal.get("idPaso").getAsJsonObject();
                        if(primero){
                            primero=false;
                            pasosel.setText("paso:"+salida.get("nombre").getAsString());
                        }
                        Movie movie = new Movie("dia #"+(a+1), salida.get("descripcion").getAsString(), "0", 0, false, salida.get("idPaso").getAsInt(), salida.get("diasDuracion").getAsInt(), 0, -1);
                        movieList.add(movie);

                    }
                    pasoedit.notifyDataSetChanged();
                }
                catch (Exception e){

                }

            }
        }).execute(valores);


    }

    public void cargar(){

    }

    public void modificar(View v){
        Intent mod=new Intent(this,Modpaso.class);
        mod.putExtra("mision",getIntent().getParcelableExtra("mision"));
        startActivity(mod);
    }
}
