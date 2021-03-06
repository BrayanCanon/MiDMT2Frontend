package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.misioncruds.MisionRecursosLista;
import juan.example.com.diabetest2.profesional.misioncruds.Recursos_crud;

public class Mision_Gen_Prof extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misiongen);
    }

    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void modificarmision(View v) {  Intent intento = new Intent(this, Pruebcon.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{
            intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intento);
        }
    }


    public void formugen(View v) {  Intent intento = new Intent(this, Creamisg.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    public void recursosmision(View view) {
        Intent intento = new Intent(this, MisionRecursosLista.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento);
        }
}
    public void logrosver(View v){
        Intent intento=new Intent(this,BuscLogro.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento);}
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent n=new Intent(this,MenuProfesional.class);
            startActivity(n);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
