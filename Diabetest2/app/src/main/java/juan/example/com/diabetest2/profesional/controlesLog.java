package juan.example.com.diabetest2.profesional;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.logros.EditLog;
import juan.example.com.diabetest2.util.Conexion;

public class controlesLog extends AppCompatActivity {

    TextView info;
    Bundle bundleInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controles_log);
        bundleInt = this.getIntent().getExtras();

    }

    public void abrir_editar(View v){
        Intent entrar=new Intent(controlesLog.this, EditLog.class);
        Bundle b= new Bundle();
        b.putInt("id",bundleInt.getInt("codigo"));
        Log.d("codigodel",""+bundleInt.getInt("codigo"));
        entrar.putExtras(b);
        startActivity(entrar);
    }

    public void eliminar_a(View v){


        final AlertDialog.Builder builder = new AlertDialog.Builder(controlesLog.this);//Context parameter
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<String> nom=new ArrayList<String>();
                ArrayList<String> val=new ArrayList<String>();
                nom.add("idLogro"); val.add(Integer.toString(bundleInt.getInt("codigo")));
                new Conexion("borrarLogro", nom, new Conexion.Comunicado() {
                    @Override
                    public void salidas(String output) {

                        String salida=output;
                        Toast toast1 = Toast.makeText(getApplicationContext(), salida, Toast.LENGTH_SHORT);
                        toast1.show();
                    }

                }).execute(val);
            }
        });


        builder.setMessage("¿Esta seguro que desea eliminarlo?");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
