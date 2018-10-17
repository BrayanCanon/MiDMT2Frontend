package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class Encuesta2 extends AppCompatActivity {

    TextView sugerencias;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta2);
        sugerencias=(TextView)findViewById(R.id.editText12);
    }

    public void procesar(View v){
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> valores=new ArrayList<>();

        nombres.add("paciente");valores.add(CrearPaciente.paciente);
        nombres.add("sugerencias");valores.add(sugerencias.getText().toString());

        new Conexion("encuesta2", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Intent salir=new Intent(este,MenuProfesional.class);
                startActivity(salir);
            }
        }).execute(valores);

    }
}
