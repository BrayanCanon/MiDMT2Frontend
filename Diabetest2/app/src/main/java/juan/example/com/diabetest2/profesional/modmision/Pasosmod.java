package juan.example.com.diabetest2.profesional.modmision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.AdapterPasoedit;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Movie;

public class Pasosmod extends AppCompatActivity {

    RecyclerView pasos;
    AdapterPasoedit pasoedit;
    private ArrayList<String> logrosnNom=new ArrayList<>() ;
    private ArrayList<Integer> logrosId=new ArrayList<>();
    ArrayList<Movie> paso=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasosmod);
        pasos=(RecyclerView)findViewById(R.id.recycler_view452);

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



    }

    public void cargar(){

    }
}
