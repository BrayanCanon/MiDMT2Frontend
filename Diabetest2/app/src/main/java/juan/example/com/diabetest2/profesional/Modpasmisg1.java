package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class Modpasmisg1 extends AppCompatActivity {

    Button abrir;
    TextView nom,desc,dias;
    Spinner logros;
    ArrayList<String> ListViewItems ;
    ArrayAdapter<String> adapter;
    Context este=this;
    int codcategoria;
    String cadnom,codmision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modpasmisg1);

        abrir=(Button)findViewById(R.id.creacionb2);
        nom=(TextView)findViewById(R.id.nom2);
        desc=(TextView)findViewById(R.id.desc2);
        dias=(TextView)findViewById(R.id.dias2);
        logros=(Spinner)findViewById(R.id.logros2);
        //------------------------------------------
        nom.setText(getIntent().getStringExtra("nombre"));
        desc.setText(getIntent().getStringExtra("descripcion"));
        dias.setText(getIntent().getStringExtra("dias"));
        //------------------------------------------
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
                    logros.setSelection(6-Integer.parseInt(getIntent().getStringExtra("categoria")));
                    adapter.notifyDataSetChanged();

                }
                catch (Exception e){

                }

            }
        }).execute(valores);

    }
}
