package juan.example.com.diabetest2.profesional.modmision;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.Movie;


public class Modpaso extends AppCompatActivity {

    TextView nombre,descripcion,dias;
    Mision selec;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modpaso);

        nombre=(TextView) findViewById(R.id.nom3);
        descripcion=(TextView)findViewById(R.id.desc3);
        dias=(TextView)findViewById(R.id.dias3);
        selec=(Mision) getIntent().getParcelableExtra("mision");
        final ArrayList<String> nombres=new ArrayList();
        ArrayList valores=new ArrayList();
        nombres.add("idMision");valores.add(selec.getIdMision());
        new Conexion("consultarPasoIdm", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson=new Gson();
                JsonObject entrada=gson.fromJson(output,JsonObject.class);
                nombre.setText(entrada.get("nombre").getAsString());
                descripcion.setText(entrada.get("descripcion").getAsString());
                dias.setText(entrada.get("diasDuracion").getAsString());
            }
        }).execute(valores);


    }

    public void modificacion(View v){

        ArrayList<String> nombres=new ArrayList<>();
        ArrayList valores=new ArrayList();

        nombres.add("idMision");valores.add(selec.getIdMision());
        JsonObject paso=new JsonObject();
        paso.addProperty("nombre",nombre.getText().toString());
        paso.addProperty("descripcion",descripcion.getText().toString());
        paso.addProperty("estado","a");
        paso.addProperty("diasDuracion",dias.getText().toString());
        nombres.add("paso");valores.add(paso);
        new Conexion("modpasos", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                int diasnum=dias.length();
                ArrayList<Movie> sal=new ArrayList<>();
                for(int a=0;a<diasnum;a++) {
                    Movie movie = new Movie("dia #"+(a+1), " ", String.valueOf(a+1), 0, false, Integer.parseInt(output), Integer.parseInt(dias.getText().toString()), selec.getIdCategoria(), -1);
                    sal.add(movie);
                }
                Intent devolverse=new Intent(este,Modpaso.class);
                devolverse.putParcelableArrayListExtra("parc",sal);
                devolverse.putExtra("mision",getIntent().getParcelableExtra("mision"));
            }
        }).execute(valores);
      /*
        }
    */
    }
}
