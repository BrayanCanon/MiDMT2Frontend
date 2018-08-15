package juan.example.com.diabetest2.profesional.misioncruds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class Medcrear extends AppCompatActivity {





    EditText nombre,docificacion,observaciones,posologia;
    Spinner seltipo;
    TimePicker toma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medcrear);
        nombre=(EditText)findViewById(R.id.id_nombre_medicamentoi);
        docificacion=(EditText)findViewById(R.id.id_dosificacion_medicamentoi);
        observaciones=(EditText)findViewById(R.id.id_observaciones_medicamentoi);
        posologia=(EditText)findViewById(R.id.id_posologiami);
        toma=(TimePicker)findViewById(R.id.timePicker);
        seltipo=(Spinner)findViewById(R.id.id_tipo_presentacioni);
        ArrayList<String> tipo = new ArrayList <String>();
        tipo.add("Inyección");
        tipo.add("Capsula");
        tipo.add("Pastilla");
        tipo.add("Pomada");
        tipo.add("Polvo");
        tipo.add("Gotas");
        tipo.add("Jarabe");
        tipo.add("Emulsión");
        tipo.add("Extracto");
        tipo.add("Loción");
        tipo.add("Solución");
        tipo.add("Comprimido");
        tipo.add("Otro");
        final ArrayAdapter bb = new  ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, tipo);
        seltipo.setAdapter(bb);

        final ArrayList<String> nombres=new ArrayList<String>();
        ArrayList<String> valores=new ArrayList<String>();
        nombres.add("codMedicamento");
        Bundle medseleccion=getIntent().getExtras();
        valores.add( medseleccion.getString("medicamentoid") );
        Log.d("captura",medseleccion.getString("medicamentoid"));
        new Conexion("consultarMedicamento", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                try {
                    JSONObject medicamento=new JSONObject(output);
                    nombre.setText(medicamento.get("nombre").toString());
                    docificacion.setText(medicamento.get("dosificacion").toString());
                    observaciones.setText(medicamento.get("observaciones").toString());
                    posologia.setText(medicamento.get("posologia").toString());
                    seltipo.setSelection(bb.getPosition(medicamento.get("tipo").toString()));
                    seltipo.notifyAll();
                }
                catch (Exception n){
                    Log.d("error",n.toString());
                }


            }
        }).execute(valores);

    }
}
