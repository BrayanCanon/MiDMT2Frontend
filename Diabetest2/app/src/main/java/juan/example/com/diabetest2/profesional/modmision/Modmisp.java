package juan.example.com.diabetest2.profesional.modmision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Mision;

public class Modmisp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modmisp);
    }
    public void basico(View v){
        Intent n=new Intent(this,Basico.class);
        n.putExtra("mision",(Mision) getIntent().getParcelableExtra("mision"));
        startActivity(n);
    }


}
