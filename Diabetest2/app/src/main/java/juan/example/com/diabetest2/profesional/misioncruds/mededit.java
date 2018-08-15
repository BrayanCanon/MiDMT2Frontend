package juan.example.com.diabetest2.profesional.misioncruds;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class mededit extends AppCompatActivity {

    EditText fecha;
    EditText cod_med,dosificacion,posologia,id_observaciones_medicamentom,id_nombre_medicamento;
    DatePickerDialog datePickerDialog;
    Spinner tipomed;
    Switch id_recordar_medicamentom;
    TimePicker tiempo;
    EditText codmed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mededit);
        id_nombre_medicamento=(EditText)findViewById(R.id.id_nombre_medicamento);
        tipomed =(Spinner)findViewById(R.id.id_tipo_presentacionm);
        dosificacion=(EditText)findViewById(R.id.id_dosificacion_medicam);
        posologia=(EditText)findViewById(R.id.id_posologiai);
        id_observaciones_medicamentom=(EditText)findViewById(R.id.id_observaciones_medicamentom);
        id_recordar_medicamentom=(Switch)findViewById(R.id.id_recordar_medicamentom);
        tiempo=(TimePicker)findViewById(R.id.timePicker);
        codmed=(EditText)findViewById(R.id.codmed);
        id_recordar_medicamentom=(Switch)findViewById(R.id.id_recordar_medicamentom);

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
        ArrayAdapter bb = new  ArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_1, tipo);

        tipomed.setAdapter(bb);


    }

    public void guardar(View v){

        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> valores=new ArrayList<>();
        nombres.add("codMedicamento");valores.add(codmed.getText().toString());
        nombres.add("nombre"); valores.add(id_nombre_medicamento.getText().toString());
        nombres.add("tipo");valores.add(tipomed.getSelectedItem().toString());

        nombres.add("idMedicamentoMision");valores.add(getIntent().getStringExtra("codigo").toString());


        nombres.add("fecha");valores.add(String.valueOf(tiempo.getCurrentHour()));


        nombres.add("dosificación");valores.add(dosificacion.getText().toString());

        nombres.add("posologia");valores.add(posologia.getText().toString());


        nombres.add("recordar"); valores.add(id_recordar_medicamentom.getText().toString());
        nombres.add("ultimaToma");valores.add("0");

        //nombres.add("observaciones"); valores.add(id_observaciones_medicamentom.getText().toString());
        Log.d("params1",""+valores.size());

       new Conexion("crearmedmision", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                

            }
        }).execute(valores);



    }

    public void borrar(View v){
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> valores=new ArrayList<>();
        nombres.add("codMedicamento");valores.add(codmed.getText().toString());
        nombres.add("idMedicamentoMision");

    }
}
