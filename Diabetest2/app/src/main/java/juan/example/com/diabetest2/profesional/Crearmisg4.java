package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class Crearmisg4 extends AppCompatActivity {

    Button abrir;
    TextView nom,desc,dias;
    Spinner logros;
    ArrayList<String> ListViewItems ;
    ArrayAdapter<String> adapter;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisg4);
        abrir=(Button)findViewById(R.id.creacionb1);
        nom=(TextView)findViewById(R.id.nom);
        desc=(TextView)findViewById(R.id.desc);
        dias=(TextView)findViewById(R.id.dias);
        logros=(Spinner)findViewById(R.id.logros);

        ArrayList nombres=new ArrayList<String>();
        ArrayList valores=new ArrayList<String>();
        ListViewItems=new ArrayList<String>();
        new Conexion("consultarLogros", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    for(int a=0;a<jsonArray.length();a++){
                        JSONObject n=jsonArray.getJSONObject(a);
                        ListViewItems.add(n.get("nomLogro").toString());
                    }
                adapter=new ArrayAdapter<String>(este,android.R.layout.simple_spinner_dropdown_item,ListViewItems);
                logros.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                }
                catch (Exception e){

                }

            }
        }).execute(valores);

    }

    public void creado(View v){
        //{"descripcion":"vase","nombre":"otrko","diasDuracion":1,"estado":"a","categoria":2,"misionPasoLogroList":[]}

        Bundle slida= getIntent().getExtras();
        int catcod=slida.getInt("catcod");
        String nomcat=slida.getString("cadnom");
        Log.d("paramet",catcod+" "+nomcat);
        ArrayList nombres=new ArrayList<String>();
        ArrayList valores=new ArrayList<String>();

        JSONArray ja = new JSONArray();
        nombres.add("descripcion");valores.add(""+desc.getText().toString());
        nombres.add("nombre");valores.add(nom.getText().toString());
        nombres.add("diasDuracion");valores.add(dias.getText().toString());
        nombres.add("estado");valores.add("a");
        nombres.add("categoria");valores.add(""+catcod);
        nombres.add("misionPasoLogroList");valores.add(ja);

        new Conexion("crearPaso",nombres,new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
            }
        }).execute(valores);




        Intent checkeado=new Intent(this,Crearmisg3.class);

        startActivity(checkeado);

    }
}
