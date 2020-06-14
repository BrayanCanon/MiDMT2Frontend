package juan.example.com.diabetest2.profesional;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class activity_encuestas_crud extends AppCompatActivity {
    ImageButton buscarBoton;
    TextView buscarTexto;
    ListView listaEncuestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuestas_crud);
        buscarBoton = (ImageButton) findViewById(R.id.botonbuscar);
        buscarTexto = (TextView) findViewById(R.id.textobusqueda);
        //buscarBoton.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //  public void onClick(View view) {

        //}
        // });
    }
        public void llenar(){
            ArrayList<String> valores=new ArrayList<String>();
            ArrayList<String> nombres=new ArrayList<String>();
            listaEncuestas = (ListView)findViewById(R.id._listaEncuesta);



        }


    }

