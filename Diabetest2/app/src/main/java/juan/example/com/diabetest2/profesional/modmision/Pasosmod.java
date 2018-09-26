package juan.example.com.diabetest2.profesional.modmision;

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
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.Mision_Gen_Prof;
import juan.example.com.diabetest2.profesional.Pruebcon;
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
    Mision seleccion;
    boolean continuar = false;
    Context este=this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasosmod);
        pasos=(RecyclerView)findViewById(R.id.recycler_view452);
        pasosel=(TextView)findViewById(R.id.mispaso9);
        seleccion=(Mision) getIntent().getParcelableExtra("mision");
        pasoedit=new AdapterPasoedit(movieList,logrosnNom,logrosId,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        pasos.setLayoutManager(mLayoutManager);
        pasos.setItemAnimator(new DefaultItemAnimator());
        pasos.setAdapter(pasoedit);

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


//-------------------------------------------------
        Mision sel=getIntent().getParcelableExtra("mision");
        if(getIntent().getParcelableArrayListExtra("parc")==null) {
            ArrayList<String> nombres = new ArrayList<>();
            ArrayList valores = new ArrayList();
            nombres.add("idMision");
            valores.add(sel.getIdMision());
            new Conexion("consultarTodosPasos", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    try {
                        Gson gson = new Gson();
                        JsonArray lista = gson.fromJson(output, JsonArray.class);
                        Boolean primero = true;
                        for (int a = 0; a < lista.size(); a++) {
                            JsonObject sal = lista.get(a).getAsJsonObject();
                            JsonObject salida = sal.get("idPaso").getAsJsonObject();
                            JsonObject logro= sal.get("idLogro").getAsJsonObject();
                            if (primero) {
                                primero = false;
                                pasosel.setText("paso:" + salida.get("nombre").getAsString());
                            }
                            Movie movie = new Movie("dia #" + (a + 1), logro.get("nomLogro").getAsString(), "0", 0, false, salida.get("idPaso").getAsInt(), salida.get("diasDuracion").getAsInt(), 0, logro.get("idLogro").getAsInt());
                            movieList.add(movie);

                        }
                        pasoedit.notifyDataSetChanged();
                    } catch (Exception e) {

                    }

                }
            }).execute(valores);
        }
        else {

            ArrayList<Movie> movieL=getIntent().getParcelableArrayListExtra("parc");
            for(int a=0;a<movieL.size();a++){
                movieList.add(movieL.get(a));
            }

            pasoedit.notifyDataSetChanged();
        }

    }

    public void cargar(){

    }

    public void enviar(View v){
        String[] si={"si","no"};
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        TextView textView = new TextView(this);
        textView.setText("¿Si modifica los dias se borrara el progreso del paciente que esten realizando esta mision esta seguro? ");
        b.setCustomTitle(textView);
        b.setItems(si,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0: {
                        continuar = true;
                        ArrayList<Movie> respuestas = new ArrayList<>(pasoedit.retsal());
                        ArrayList<String> nombres = new ArrayList<>();
                        ArrayList valores = new ArrayList();
                        //new Conexion("")
                        //----------------------------------------
                        JsonArray info = new JsonArray();
                        //--------------------------------
                        for (int a = 0; a < respuestas.size(); a++) {
                            JsonObject adaptador = new JsonObject();
                            Movie salida = respuestas.get(a);
                            //-----------------------------------------
                            adaptador.addProperty("mision", seleccion.getIdMision());
                            adaptador.addProperty("paso", salida.getId());
                            adaptador.addProperty("logro", salida.getLogro());
                            adaptador.addProperty("pasoNumero", Integer.parseInt(salida.getYear()));
                            adaptador.addProperty("estado", "a");
                            //-----------------------------------------
                            info.add(adaptador);

                        }

                        String prueba = info.toString();
                        Log.d("asd", prueba);
                        nombres.add("cod");
                        valores.add(info);
                        new Conexion("modificarMPLogro", nombres, new Conexion.Comunicado() {
                            @Override
                            public void salidas(String output) {

                            }
                        }).execute(valores);
                        //--------------------------------
                        Intent envio = new Intent(este, Pruebcon.class);
                        startActivity(envio);
                        break;
                    }
                    case 1:
                        continuar =false;
                        break;
                }
            }
        });
        b.show();

    }

    public void modificar(View v){
        Intent mod=new Intent(this,Modpaso.class);
        mod.putExtra("mision",getIntent().getParcelableExtra("mision"));
        startActivity(mod);
    }
}
