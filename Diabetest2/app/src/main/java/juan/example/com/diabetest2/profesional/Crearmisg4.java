package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;

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
    int codcategoria;
    String cadnom,codmision;
    String nomcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //--------------------------------------
        try {
            Bundle params=getIntent().getExtras();
            codcategoria=params.getInt("catcod");
            cadnom=params.getString("cadnom");
            codmision=params.getString("codmision");

        }
        catch (Exception e){
            Log.d("errorpaso",e.toString());
        }


        //----------------------------------
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

    }

    public void creado(View v){
        //{"descripcion":"vase","nombre":"otrko","diasDuracion":1,"estado":"a","categoria":2,"misionPasoLogroList":[]}

        Bundle slida= getIntent().getExtras();
        int catcod=slida.getInt("catcod");
        nomcat=slida.getString("cadnom");
        Log.d("paramet",catcod+" "+nomcat);
        ArrayList nombres=new ArrayList<String>();
        ArrayList valores=new ArrayList<String>();

        JsonArray ja = new JsonArray();
        nombres.add("descripcion");valores.add(desc.getText().toString());
        nombres.add("nombre");valores.add(nom.getText().toString());
        nombres.add("diasDuracion");valores.add(dias.getText().toString());
        nombres.add("estado");valores.add("a");
        nombres.add("categoria");valores.add(""+catcod);
        nombres.add("misionPasoLogroList");valores.add(ja);

        new Conexion("crearPaso",nombres,new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Intent creado=new Intent(este,Crearmisg3.class);
                creado.putExtra("categoria",nomcat);
                creado.putExtra("codigo",codmision);
                creado.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(creado);
            }
        }).execute(valores);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent a = new Intent(this,Crearmisg3.class);
            Bundle slida= getIntent().getExtras();
            int catcod=slida.getInt("catcod");
            nomcat=slida.getString("cadnom");
            a.putExtra("categoria",nomcat);
            a.putExtra("codigo",codmision);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
