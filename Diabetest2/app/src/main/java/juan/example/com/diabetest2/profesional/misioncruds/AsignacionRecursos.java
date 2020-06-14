package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.RecursoVo;

public class AsignacionRecursos extends AppCompatActivity {
    ImageView imagen ;
    TextView titulo,video,autor,fecha,descripcion;
    RecursoVo recurso;
    Mision mis;
    Boolean asig;
    Button asignar,desasignar;
    Context con=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_recursos);
        asignar=findViewById(R.id.asignarRec);
        desasignar=findViewById(R.id.desasignarRec);
        imagen=findViewById(R.id.id_imagen_recurso_prof);
        titulo=findViewById(R.id.id_titulo_recurso_detalle_prof);
        video= findViewById(R.id.id_link_YouTube_prof);
        autor=findViewById(R.id.id_autor_recurso_prof);
        fecha=findViewById(R.id.id_fecha_recurso_prof);
        descripcion=findViewById(R.id.id_contenido_recurso_prof);
        Bundle envio =   getIntent().getExtras();
        recurso = (RecursoVo) envio.getSerializable("recurso");
        mis = (Mision) envio.getParcelable("mision");
        asig =envio.getBoolean("asignar");
        titulo.setText(recurso.getTitulo());
        autor.setText(recurso.getAutor());
        fecha.setText(recurso.getFecha());
        descripcion.setText(recurso.getDecripcion());
        video.setText(recurso.getVideo());
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(recurso.getVideo())));
                Log.i("Video", "Video Playing....");
            }
        });
        String urlImagen = Inicio.urlImagenes+recurso.getFoto();
        Picasso.with(this).load(urlImagen).fit().noFade().into(imagen);
        asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asignarrecurso();

                Intent intento = new Intent(con, RecursosAsignadosMis.class);
                Bundle envio = new Bundle();
                envio.putParcelable("mision",mis);
                intento.putExtras(envio);
                intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intento);
            }
        });
        desasignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desasignarrecurso();
                Intent intento = new Intent(con, RecursosAsignadosMis.class);
                Bundle envio = new Bundle();
                intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                envio.putParcelable("mision",mis);
                intento.putExtras(envio);
                startActivity(intento);
            }
        });


        if(asig){
            asignar.setVisibility(View.VISIBLE);
            desasignar.setVisibility(View.INVISIBLE);
        }else if(!asig){
            asignar.setVisibility(View.INVISIBLE);
            desasignar.setVisibility(View.VISIBLE);
        }


    }

    private void asignarrecurso() {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("idRecapoyo");
        nombres.add("idMision");
        ArrayList<String> valores = new ArrayList<>();
        valores.add(recurso.getIdRecurso());
        valores.add(Integer.toString(mis.getIdMision()));
        new Conexion("asignarRecurso", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {


            }
        }).execute(valores);
    }
    private void desasignarrecurso() {
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("idRecapoyo");
        nombres.add("idMision");
        ArrayList<String> valores = new ArrayList<>();
        valores.add(recurso.getIdRecurso());
        valores.add(Integer.toString(mis.getIdMision()));
        new Conexion("desasignarRecurso", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {


            }
        }).execute(valores);
    }

}
