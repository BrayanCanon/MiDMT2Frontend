package juan.example.com.diabetest2.profesional;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;

public class BuscLogro extends AppCompatActivity {

    ListView listview ;
    ArrayList<String> ListViewItems ;
    ArrayList<Integer> listId;
    ArrayList<String> puntos=new ArrayList<>();
    ArrayList<String> descripcion=new ArrayList<>();

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
                            if(!explrObject.get("nomLogro").toString().equals("Vacio")) {

                                listId.add(Integer.parseInt(explrObject.get("idLogro").toString()));
                                ListViewItems.add(explrObject.get("nomLogro").toString());
                                if (explrObject.has("puntos")) {
                                    Log.d("puntos", explrObject.get("puntos").toString());
                                    puntos.add(explrObject.get("puntos").toString());
                                } else {
                                    puntos.add("0");
                                }
                                if (explrObject.has("descripcion")) {
                                    descripcion.add(explrObject.get("descripcion").toString());
                                } else {
                                    descripcion.add("");
                                }
                            }}
                    adapter = new ArrayAdapter<String>
                            (BuscLogro.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    android.R.id.text1, ListViewItems );
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    } catch (JSONException e1) {
                    e1.printStackTrace();
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
                b.putString("puntos",puntos.get(position));
                b.putString("descripcion",descripcion.get(position));

                controles.putExtras(b);
                startActivity(controles);
            }
        });

    }

    public void creacion(View v){
        Intent n=new Intent(BuscLogro.this,ag_logro.class);
        n.putExtra("categoria",getIntent().getStringExtra("categoria"));
        n.putExtra("codcategoria",getIntent().getStringExtra("codcategoria"));
        n.putExtra("codigo",getIntent().getStringExtra("codigo"));
        startActivity(n);
    }

    public void pasos(View v){
        Intent ne=new Intent(this,Crearmisg3.class);
        ne.putExtra("categoria",getIntent().getStringExtra("categoria"));
        ne.putExtra("codcategoria",getIntent().getIntExtra("codcategoria", 1));
        ne.putExtra("codigo",getIntent().getStringExtra("codigo"));
        Log.d("codcate",""+getIntent().getIntExtra("codcategoria", 1));
        startActivity(ne);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent atras=new Intent(this, Mision_Gen_Prof.class);
            startActivity(atras);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
