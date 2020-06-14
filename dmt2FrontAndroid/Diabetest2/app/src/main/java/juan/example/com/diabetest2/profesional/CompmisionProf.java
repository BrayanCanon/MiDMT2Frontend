package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.misioncruds.Recursos_crud;
import juan.example.com.diabetest2.util.InternetPrueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CompmisionProf extends AppCompatActivity {

    private InternetPrueba con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compmision_prof);
        con=new InternetPrueba(CompmisionProf.this);
        Button btn =(Button) findViewById(R.id.recursos_abrir);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(view.getContext(), Recursos_crud.class);
                if(con.probarInternet() == false){ Toast.makeText(view.getContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento);


                }
            }

        });}

    public void crear_logro(View v) { Intent intento = new Intent(this, ag_logro.class);
        if(con.probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    public void bus_logro(View v) { Intent intento = new Intent(this, BuscLogro.class);
        if(con.probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }




}
