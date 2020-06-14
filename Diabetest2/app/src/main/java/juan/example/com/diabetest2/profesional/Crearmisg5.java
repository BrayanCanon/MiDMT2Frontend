package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Recurso;
import juan.example.com.diabetest2.util.RecursoAdapter;

public class Crearmisg5 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecursoAdapter adaptador;
    private ArrayList<Recurso> res=new ArrayList<Recurso>();
    private Context este=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisg5);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayourManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayourManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adaptador=new RecursoAdapter(res,este,getIntent().getStringExtra("codigo"));
        recyclerView.setAdapter(adaptador);


        res.add(new Recurso("medicaentos","juan.example.com.diabetest2.profesional.misioncruds.Medicamentosm",false));
        res.add(new Recurso("foro","juan.example.com.diabetest2.profesional.misioncruds.Forosm",false));
        res.add(new Recurso("habitos saludables","juan.example.com.diabetest2.profesional.misioncruds.habitosm",false));


        adaptador.notifyDataSetChanged();


    }
}
