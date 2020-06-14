package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.misionesadapter;

public class MisionRecursosLista extends AppCompatActivity {
   private   SwipeRefreshLayout refreshLayout;
    private RecyclerView vista;
    private EditText buscmisedit;
    private misionesadapter n;
    private ArrayList<Mision> prueba=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mision_recursos_lista);
        buscmisedit=(EditText)findViewById(R.id.buscmisedit);
        vista=(RecyclerView)findViewById(R.id.recyclerMis);
        // Obtener el refreshLayout
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        cargar();
// Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        recargar(null);

                    }
                }
        );

    }
    public void cargar(){

        final Mision elemento2=new Mision();
        prueba=new ArrayList<>();
        n=new misionesadapter(prueba,this,true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        vista.setLayoutManager(mLayoutManager);
        vista.setAdapter(n);

        new Conexion("consultarMisiones",new ArrayList<String>(), new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Gson gson=new Gson();
                    JsonArray salida=gson.fromJson(output,JsonArray.class);
                    Log.d("salidatam",String.valueOf(salida.size()));
                    for(int ajisnnef=0;ajisnnef<salida.size();ajisnnef++){
                        JsonObject em=salida.get(ajisnnef).getAsJsonObject();

                        if(em.has("nombre")) {
                            if (em.has("idCategoria")) {
                                JsonObject categoria = em.get("idCategoria").getAsJsonObject();
                                JsonObject tipomision= em.get("idTipoMision").getAsJsonObject();
                                JsonObject niv = em.get("idNivel").getAsJsonObject();

                                Mision elemento = new Mision();
                                elemento.setIdMision(em.get("idMision").getAsInt());
                                elemento.setNombre(em.get("nombre").getAsString());
                                elemento.setDescripcion(em.get("descripcion").getAsString());
                                elemento.setIdCategoria(Integer.parseInt(categoria.get("idCategoria").getAsString()));
                                elemento.setCategoria(categoria.get("nombreCategoria").getAsString());
                                elemento.setTipo(tipomision.get("nombreTipoMision").getAsString());
                                elemento.setNomnivel(niv.get("nombre").getAsString());

                                //elemento.setCategoria("sal");
                                prueba.add(elemento);
                            }
                        }

                    }

                    n.notifyDataSetChanged();

                }catch (Exception e){
                    Log.d("consul111",e.toString());
                }


            }
        }).execute(new ArrayList<String>());

    }
    public void busqueda(View v){

        Pattern p;
        Matcher m;
        for(int i=0;i<prueba.size();i++){
            p = Pattern.compile(buscmisedit.getText().toString());
            m = p.matcher(prueba.get(i).getNombre());
            if(!m.find())
            {
                prueba.remove(i);
                i--;
            }
            n.notifyDataSetChanged();
        }


    }
    public void recargar(View v){
        buscmisedit.setText("");
        cargar();
        refreshLayout.setRefreshing(false);
    }


    }

