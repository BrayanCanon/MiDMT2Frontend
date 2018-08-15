package juan.example.com.diabetest2.paciente;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import juan.example.com.diabetest2.PasosMision;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class misiones extends AppCompatActivity {
    ArrayList<MisionVo> listaMisiones;
    RecyclerView recylerMisiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misiones);

       listaMisiones= new ArrayList<>();
       recylerMisiones=findViewById(R.id.recyclerMisiones);
      llenarMisiones();




    }

    private void llenarMisiones() {
        ArrayList vacio = new ArrayList<>();
        new Conexion("")


    }
}
