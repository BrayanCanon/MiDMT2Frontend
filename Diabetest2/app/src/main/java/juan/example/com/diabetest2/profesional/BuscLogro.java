package juan.example.com.diabetest2.profesional;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class BuscLogro extends AppCompatActivity {

    ListView listview ;
    ArrayList<String> ListViewItems ;
    ArrayList<Integer> listId;
    SparseBooleanArray sparseBooleanArray ;
    Button busqueda;
    EditText buscarcad;
    ArrayAdapter<String> adapter;
    Button recarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busc_logro);
        llenar();
        busqueda=(Button)findViewById(R.id.buscar);
        buscarcad=(EditText)findViewById(R.id.cadena);
        recarga=(Button)findViewById(R.id.recargar);
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Pattern p;
                    Matcher m;
                    for(int a=0;a<ListViewItems.size();a++) {
                        p = Pattern.compile(buscarcad.getText().toString());
                        m = p.matcher(ListViewItems.get(a));
                        if(!m.find())
                        {
                            ListViewItems.remove(a);
                            listId.remove(a);
                            a--;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e){
                    Log.d("error eliminar",e.toString());
                }

            }
        });
        recarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarcad.setText("");
                llenar();
            }
        });

    }

    public void llenar(){
        ArrayList<String> valores=new ArrayList<String>();
        ArrayList<String> nombres=new ArrayList<String>();
        listview = (ListView)findViewById(R.id.list);
        new Conexion("consultarLogros",nombres,new Conexion.Comunicado(){
            @Override
            public void salidas(String out) {
                try {

                    JSONArray jsonArray = new JSONArray(out);
                    int tam2=jsonArray.length();
                    listId=new ArrayList<Integer>();
                    ListViewItems=new ArrayList<String>();
                    int contador=0;
                    for(int a=0;a<tam2;a++) {
                            JSONObject explrObject = jsonArray.getJSONObject(a);
                            listId.add(Integer.parseInt(explrObject.get("idLogro").toString()));
                            ListViewItems.add(explrObject.get("nomLogro").toString());
                    }
                     adapter = new ArrayAdapter<String>
                            (BuscLogro.this,
                                    android.R.layout.simple_list_item_multiple_choice,
                                    android.R.id.text1, ListViewItems );
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                catch (Exception e){
                    Log.d("errorraro",e.toString());
                }

            }
        }).execute(valores);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent controles=new Intent(BuscLogro.this,controlesLog.class);
                Bundle b = new Bundle();
                Log.d("enviocontrol",""+listId.get(position));
                b.putInt ("codigo",listId.get(position));
                b.putString("nombre",ListViewItems.get(position));
                controles.putExtras(b);
                startActivity(controles);
            }
        });

    }

    public void creacion(View v){
        Intent n=new Intent(BuscLogro.this,ag_logro.class);
        startActivity(n);
    }

    public void pasos(View v){
        Intent ne=new Intent(this,Crearmisg3.class);
        ne.putExtra("categoria",getIntent().getStringExtra("categoria"));
        ne.putExtra("codigo",getIntent().getStringExtra("codigo"));
        startActivity(ne);
    }
}
