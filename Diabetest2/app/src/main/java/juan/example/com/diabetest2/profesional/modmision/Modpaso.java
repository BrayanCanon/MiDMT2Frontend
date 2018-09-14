package juan.example.com.diabetest2.profesional.modmision;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.security.spec.ECField;
import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.Pruebcon;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.Movie;


public class Modpaso extends AppCompatActivity {

    TextView nombre,descripcion,dias;
    boolean cambiodias=false;
    Mision selec;
    Integer codpaso;
    Context este=this;
    RadioGroup grupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modpaso);

        nombre=(TextView) findViewById(R.id.nom3);
        descripcion=(TextView)findViewById(R.id.desc3);
        dias=(TextView)findViewById(R.id.dias3);
        selec=(Mision) getIntent().getParcelableExtra("mision");
        dias.setEnabled(false);
        grupo=(RadioGroup)findViewById(R.id.grupores);
        final ArrayList<String> nombres=new ArrayList();
        ArrayList valores=new ArrayList();
        nombres.add("idMision");valores.add(selec.getIdMision());
        new Conexion("consultarPasoIdm", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    Gson gson = new Gson();
                    JsonObject entrada = gson.fromJson(output, JsonObject.class);
                    codpaso=entrada.get("idPaso").getAsInt();
                    nombre.setText(entrada.get("nombre").getAsString());
                    descripcion.setText(entrada.get("descripcion").getAsString());
                    dias.setText(entrada.get("diasDuracion").getAsString());
                }
                catch (Exception e){
                    Intent devolverse=new Intent(este,Pruebcon.class);
                    Toast.makeText(este, "Esta mision no cuenta con pasos que modificar!", Toast.LENGTH_LONG).show();
                    startActivity(devolverse);

                }
            }
        }).execute(valores);


    }

    public void modificacion(View v){

        ArrayList<String> nombres=new ArrayList<>();
        ArrayList valores=new ArrayList();
        if(cambiodias) {
            nombres.add("idMision");
            valores.add(selec.getIdMision());
            JsonObject paso = new JsonObject();
            paso.addProperty("nombre", nombre.getText().toString());
            paso.addProperty("descripcion", descripcion.getText().toString());
            paso.addProperty("estado", "a");
            paso.addProperty("diasDuracion", dias.getText().toString());
            nombres.add("paso");
            valores.add(paso);
            new Conexion("modpasos", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    int diasnum = Integer.parseInt(dias.getText().toString());
                    ArrayList<Movie> sal = new ArrayList<>();
                    for (int a = 0; a < diasnum; a++) {
                        Movie movie = new Movie("dia #" + (a + 1), " ", String.valueOf(a + 1), 0, false, Integer.parseInt(output), Integer.parseInt(dias.getText().toString()), selec.getIdCategoria(), -1);
                        sal.add(movie);
                    }
                    Intent devolverse = new Intent(este, Pasosmod.class);
                    devolverse.putParcelableArrayListExtra("parc", sal);
                    devolverse.putExtra("mision", getIntent().getParcelableExtra("mision"));
                    startActivity(devolverse);
                }
            }).execute(valores);
        }
        else {
            ArrayList<String> nombres1=new ArrayList<>();
            ArrayList valores1=new ArrayList<>();
            nombres1.add("idPaso");valores1.add(codpaso);
            nombres1.add("nombre");valores1.add( nombre.getText().toString());
            nombres1.add("descripcion");valores1.add( descripcion.getText().toString());
            nombres1.add("estado");valores1.add( "a");
            nombres1.add("diasDuracion");valores1.add( dias.getText().toString());
            new Conexion("modificarPaso", nombres1, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Intent msa=new Intent(este,Pasosmod.class);
                    msa.putExtra("mision",getIntent().getParcelableExtra("mision"));
                    msa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(msa);
                }
            }).execute(valores1);
        }
      /*
        }
    */
    }
    public void onRadioButtonClicked(View view) {

        switch (view.getId()){
            case R.id.radiosi:
                String[] si={"si","no"};
                AlertDialog.Builder b = new AlertDialog.Builder(este);
                TextView textView = new TextView(este);
                textView.setText("¿Si modifica los dias se borrara el progreso del paciente que esten realizando esta mision esta seguro? ");
                b.setCustomTitle(textView);
                b.setItems(si,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                cambiodias=true;
                                dias.setEnabled(true);
                                break;
                            case 1:
                                dias.setEnabled(false);
                                grupo.check(R.id.radiono);
                                cambiodias=false;
                                break;
                        }
                    }
                });
                b.show();
                break;
            case R.id.radiono:
                dias.setEnabled(false);
                cambiodias=false;
                break;
        }

    }
}
