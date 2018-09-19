package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.RecursoVo;

public class RecursosAsignadosMis extends AppCompatActivity {
    ArrayList<RecursoVo> listaRecursos;
    RecyclerView recyclerRecursos;
    AdaptadorRecursos adapter;
    Context vista = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos_asignados_mis);
        listaRecursos = new ArrayList<>();
        recyclerRecursos=findViewById(R.id.recyclerRecursos);
        recyclerRecursos.setItemAnimator(new DefaultItemAnimator());
        recyclerRecursos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdaptadorRecursos(listaRecursos,vista);
        recyclerRecursos.setAdapter(adapter);
        this.llenarRecursos();
    }
    private void llenarRecursos(){




        ArrayList vacio = new ArrayList<>();



        new Conexion("consultarRecursoMision", vacio, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {

                Gson gson = new Gson();
                JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                JsonObject salida,usuario;
                for(int i = 0; i<arreglo.size();i++){
                    salida=arreglo.get(i).getAsJsonObject();
                    usuario=salida.get("nomUsuario").getAsJsonObject();
                    RecursoVo rec = new RecursoVo(salida.get("tituloRec").getAsString(),usuario.get("nomUsuario").getAsString(),salida.get("contenidoApoyo").getAsString(),salida.get("imagen").getAsString(),salida.get("fecha").getAsString(),salida.get("video").getAsString());
                    listaRecursos.add(rec);

                }
                adapter.notifyDataSetChanged();


            }
        }).execute(vacio);


    }

}
