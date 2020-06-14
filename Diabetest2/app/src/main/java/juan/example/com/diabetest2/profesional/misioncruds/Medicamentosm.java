package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.BuscLogro;
import juan.example.com.diabetest2.util.Conexion;

public class Medicamentosm extends AppCompatActivity {

    ListView listview;
    ArrayList<String> ListViewItemsm=new ArrayList<String>();
    ArrayList<String> listIdm=new ArrayList<String>();
    SparseBooleanArray sparseBooleanArray;
    Button busquedam;
    EditText buscarcadm;
    ArrayAdapter<String> adapterm;
    Button recargam;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentosm);

        busquedam = (Button) findViewById(R.id.buscarm);
        buscarcadm = (EditText) findViewById(R.id.cadenam);
        buscarcadm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarcadm.setText("");
            }
        });
        busquedam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(!buscarcadm.getText().toString().matches("")) {
                        Pattern p;
                        Matcher m;
                        for (int a = 0; a < ListViewItemsm.size(); a++) {
                            p = Pattern.compile(buscarcadm.getText().toString());
                            m = p.matcher(ListViewItemsm.get(a));
                            if (!m.find()) {
                                ListViewItemsm.remove(a);
                                listIdm.remove(a);
                                a--;
                            }
                            adapterm.notifyDataSetChanged();
                        }
                    }
                    else {
                        llenar();
                    }
                }
                catch (Exception e){
                    Log.d("error eliminar",e.toString());
                }

            }
        });
        recargam = (Button) findViewById(R.id.recargarm);
        recargam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarcadm.setText("");
                llenar();
            }
        });
        llenar();
    }
    public void llenar(){
        ArrayList<String> valores=new ArrayList<String>();
        ArrayList<String> nombres=new ArrayList<String>();
        listview = (ListView)findViewById(R.id.listm);
       ListViewItemsm=new ArrayList<String>();
       listIdm=new ArrayList<String>();
        new Conexion("consultarMedicamentodos", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Log.d("salidee",output);
                    JSONArray jsonArray = new JSONArray(output);
                    for(int b=0;b<jsonArray.length();b++){
                        JSONObject met=jsonArray.getJSONObject(b);
                        ListViewItemsm.add(met.get("nombre").toString());
                        listIdm.add(met.get("codMedicamento").toString());
                        Log.d("elementos",met.get("nombre").toString());
                    }
                    adapterm = new ArrayAdapter<String>
                            (Medicamentosm.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    android.R.id.text1, ListViewItemsm );

                    listview.setAdapter(adapterm);

                    adapterm.notifyDataSetChanged();

                }catch (Exception e){
                    Log.d("erroresmedi",e.toString());
                }

            }
        }).execute(valores);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent crear=new Intent(este,Medcrear.class);
               Bundle contenido=new Bundle();
               contenido.putString("medicamentoid",listIdm.get(i));
               contenido.putString("codigo",getIntent().getStringExtra("codigo"));
               crear.putExtras(contenido);
               startActivity(crear);

            }
        });
    }

    public void crear(View v){
        Intent mover=new Intent(this,mededit.class);
        mover.putExtra("codigo",getIntent().getStringExtra("codigo"));
        startActivity(mover);
    }

}