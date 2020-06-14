package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class Encuesta1 extends AppCompatActivity {

    TextView metas;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta1);
        metas=(TextView)findViewById(R.id.editText9);
    }

    public void procesar(View v){
        if(!metas.getText().toString().matches("")) {
            ArrayList<String> nombres = new ArrayList<>();
            ArrayList<String> valores = new ArrayList<>();
            nombres.add("paciente");
            valores.add(CrearPaciente.paciente);
            nombres.add("metas");
            valores.add(metas.getText().toString());
            new Conexion("encuesta1", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Intent salir = new Intent(este, Encuesta2.class);
                    startActivity(salir);
                }
            }).execute(valores);
        }
        else {
            Toast.makeText(getApplicationContext(), "Por favor complete el diagnóstico!", Toast.LENGTH_SHORT).show();
        }

    }

    //Advertencia de retroceso
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Toast.makeText(getApplicationContext(), "Por favor complete el diagnóstico!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
