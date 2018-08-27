package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.misionesadapter;

public class Pruebcon extends AppCompatActivity {

    RecyclerView vista;
    misionesadapter n;
    Context este=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebcon);
        final ArrayList<Mision> prueba=new ArrayList<>();


        final Mision elemento2=new Mision();

        vista=(RecyclerView)findViewById(R.id.recycler_view45);
        n=new misionesadapter(prueba,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        vista.setLayoutManager(mLayoutManager);
        vista.setAdapter(n);

        new Conexion("consultarMisiones",new ArrayList<String>(), new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    JSONArray salida=new JSONArray(output);
                    Log.d("salidatam",String.valueOf(salida.length()));
                    for(int ajisnnef=0;ajisnnef<salida.length();ajisnnef++){
                        JSONObject em=salida.getJSONObject(ajisnnef);

                        if(em.has("nombre")) {
                            if (em.has("idCategoria")) {
                                JSONObject categoria = em.getJSONObject("idCategoria");
                                //JSONObject tipomision= em.getJSONObject("idTipoMision");
                                JSONObject niv = em.getJSONObject("idNivel");

                                Mision elemento = new Mision();
                                elemento.setIdMision(Integer.parseInt(em.get("idMision").toString()));
                                elemento.setNombre(em.get("nombre").toString());
                                elemento.setIdCategoria(Integer.parseInt(categoria.get("idCategoria").toString()));
                                elemento.setCategoria(categoria.get("nombreCategoria").toString());
                                //elemento.setTipo(tipomision.getString("nombreTipoMision"));
                                elemento.setNomnivel(niv.getString("nombre"));

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

    }
}
