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
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Movie;

public class Crearmisg4 extends AppCompatActivity {

    Button abrir;
    TextView nom,desc,dias;
    ArrayList<String> ListViewItems ;
    ArrayAdapter<String> adapter;
    Context este=this;
    int codcategoria;
    Spinner diasmarc;
    String cadnom,codmision;
    String nomcat;
    ArrayAdapter<String> adap;
    ArrayList<String> adp=new ArrayList<>();

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
        diasmarc=(Spinner)findViewById(R.id.spinnermar);
        for(int a=1;a<30;a++){
            adp.add(String.valueOf(a));
        }
        adap = new ArrayAdapter<String>
                (Crearmisg4.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        android.R.id.text1, adp );
        diasmarc.setAdapter(adap);
        adap.notifyDataSetChanged();
        ArrayList nombres=new ArrayList<String>();
        ArrayList valores=new ArrayList<String>();
        ListViewItems=new ArrayList<String>();

    }

    public void creado(View v){
        //{"descripcion":"vase","nombre":"otrko","diasDuracion":1,"estado":"a","categoria":2,"misionPasoLogroList":[]}
        if(!nom.getText().toString().matches("") && !desc.getText().toString().matches("")) {
            Bundle slida = getIntent().getExtras();
            final int catcod = slida.getInt("catcod");
            nomcat = slida.getString("cadnom");
            Log.d("paramet", catcod + " " + nomcat);
            ArrayList nombres = new ArrayList<String>();
            ArrayList valores = new ArrayList<String>();

            JsonArray ja = new JsonArray();
            nombres.add("descripcion");
            valores.add(desc.getText().toString());
            nombres.add("nombre");
            valores.add(nom.getText().toString());
            nombres.add("diasDuracion");
            valores.add(diasmarc.getSelectedItem().toString());
            nombres.add("estado");
            valores.add("a");
            nombres.add("categoria");
            valores.add("" + catcod);
            nombres.add("misionPasoLogroList");
            valores.add(ja);

            //final int diasnum=Integer.parseInt(dias.getText().toString());
            final int diasnum = Integer.parseInt(diasmarc.getSelectedItem().toString());
            new Conexion("crearPaso", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Intent creado = new Intent(este, Crearmisg3.class);
                    ArrayList<Movie> sal = new ArrayList<>();

                    for (int a = 0; a < diasnum; a++) {
                        Movie movie = new Movie("dia #" + (a + 1), " ", String.valueOf(a + 1), 0, false, Integer.parseInt(output), diasnum, catcod, -1);
                        sal.add(movie);
                    }

                    creado.putExtra("nompasus", nom.getText().toString());
                    creado.putExtra("categoria", nomcat);
                    creado.putExtra("codigo", codmision);
                    creado.putExtra("noexiste", 1);
                    creado.putParcelableArrayListExtra("parc", sal);
                    creado.putExtra("diasnum",diasnum);
                    creado.putExtra("cadcod",catcod);
                    creado.putExtra("output",output);
                    creado.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(creado);
                }
            }).execute(valores);

        }
        else {
            Toast toast1 =
                    Toast.makeText(this,
                            "Por favor llene los datos antes de continuar", Toast.LENGTH_SHORT);
            toast1.show();
        }
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
            a.putExtra("noexiste",1);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
