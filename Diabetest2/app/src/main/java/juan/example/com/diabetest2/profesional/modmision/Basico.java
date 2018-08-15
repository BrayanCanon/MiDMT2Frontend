package juan.example.com.diabetest2.profesional.modmision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Mision;

public class Basico extends AppCompatActivity {

    Mision seleccion;
    EditText modTextnom;
    Spinner categoria,tipo,nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basico);
        seleccion=(Mision) getIntent().getParcelableExtra("mision");

        //------------
        modTextnom=(EditText)findViewById(R.id.modTextnom);
        categoria=(Spinner)findViewById(R.id.modspinner1);
        tipo=(Spinner)findViewById(R.id.modspinner3);
        nivel=(Spinner)findViewById(R.id.modspinner4);
        //-------------
        ArrayAdapter cat1 = (ArrayAdapter) categoria.getAdapter();
        ArrayAdapter tip1 = (ArrayAdapter) tipo.getAdapter();

        int pos=cat1.getPosition(seleccion.getCategoria());
        int pos2=tip1.getPosition(seleccion.getTipo());
        modTextnom.setText(seleccion.getNombre());
        categoria.setSelection(pos);
        tipo.setSelection(pos2);


    }

    public void continuar(View v){
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> valores=new ArrayList<>();

    }
}
