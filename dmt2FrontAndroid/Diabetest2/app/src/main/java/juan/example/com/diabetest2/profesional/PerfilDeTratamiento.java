package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class PerfilDeTratamiento extends AppCompatActivity {

    CheckBox general,cardiaco,renales,cardiovasculares,arterial,movilidad;
    TextView advertencia;
    EditText indicaciones;
    Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_de_tratamiento);
        general=(CheckBox)findViewById(R.id.general);
        cardiaco=(CheckBox)findViewById(R.id.cardiacas);
        renales=(CheckBox)findViewById(R.id.renales);
        cardiovasculares=(CheckBox)findViewById(R.id.cardiovasculares);
        arterial=(CheckBox)findViewById(R.id.hipertensionArterial);
        movilidad=(CheckBox)findViewById(R.id.movilidadReducida);
        advertencia=(TextView)findViewById(R.id.textView157);
        indicaciones=(EditText)findViewById(R.id.editText7);

        advertencia.setVisibility(View.INVISIBLE);
        indicaciones.setVisibility(View.INVISIBLE);
        //------------------------------------------------------
        //------------------------------------------------------

    }

    public void deselect(View view) {
        if(general.isChecked()) {
            cardiaco.setChecked(false);cardiaco.setEnabled(false);
            renales.setChecked(false);renales.setEnabled(false);
            cardiovasculares.setChecked(false);cardiovasculares.setEnabled(false);
            arterial.setChecked(false);arterial.setEnabled(false);
            movilidad.setChecked(false);movilidad.setEnabled(false);
            advertencia.setVisibility(View.INVISIBLE);
            indicaciones.setVisibility(View.INVISIBLE);
        }
        else {
            cardiaco.setEnabled(true);
            renales.setEnabled(true);
            cardiovasculares.setEnabled(true);
            arterial.setEnabled(true);
            movilidad.setEnabled(true);
        }

    }

    public void movred(View view) {
        if(movilidad.isChecked()) {
            advertencia.setVisibility(View.VISIBLE);
            indicaciones.setVisibility(View.VISIBLE);
        }
        else {
            advertencia.setVisibility(View.INVISIBLE);
            indicaciones.setVisibility(View.INVISIBLE);
        }
    }

    public void iniciovolv(View view) {
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> valores=new ArrayList<>();
        nombres.add("cardiaco");valores.add(String.valueOf(cardiaco.isActivated()));
        nombres.add("renales");valores.add(String.valueOf(renales.isActivated()));
        nombres.add("cardiovasculares");valores.add(String.valueOf(cardiovasculares.isActivated()));
        nombres.add("arterial");valores.add(String.valueOf(arterial.isActivated()));
        nombres.add("movilidad");valores.add(String.valueOf(movilidad.isActivated()));
        nombres.add("advertencia");valores.add(String.valueOf(advertencia.isActivated()));
        nombres.add("indicaciones");valores.add(String.valueOf(indicaciones.isActivated()));
        if(!indicaciones.getText().toString().matches("")){
            nombres.add("movilidadindi");valores.add(indicaciones.getText().toString());
        }
        new Conexion("perfiltrat", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Intent volver=new Intent(este,Encuesta1.class);
                startActivity(volver);
            }
        }).execute(valores);
    }
}
