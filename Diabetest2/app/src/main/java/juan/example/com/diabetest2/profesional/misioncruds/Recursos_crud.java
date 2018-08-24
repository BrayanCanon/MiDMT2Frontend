package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.RecursoVo;

public class Recursos_crud extends AppCompatActivity {
    ArrayList<RecursoVo> listaRecursos;
    RecyclerView recyclerRecursos;
    AdaptadorRecursos adapter;
    Context vista = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos_crud);
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



        new Conexion("consultarRecursos", vacio, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {

                try {
                    JSONArray jsonArray = new JSONArray(output);

                    for(int a=0;a<jsonArray.length();a++){
                        JSONObject salida=jsonArray.getJSONObject(a);



                        RecursoVo rec = new RecursoVo(salida.get("tituloRec").toString(),salida.get("contenidoApoyo").toString(),Integer.parseInt(salida.get("imagen").toString()));
                        listaRecursos.add(rec);


                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).execute(vacio);


    }

}
