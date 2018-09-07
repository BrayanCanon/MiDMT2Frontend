package juan.example.com.diabetest2.paciente;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.util.RecursoVo;

public class VerRecursosMisionPaciente extends AppCompatActivity {
    ImageView imagen ;
    TextView titulo,video,autor,fecha,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_recursos_mision_paciente);
        imagen=findViewById(R.id.id_imagen_recurso);
        titulo=findViewById(R.id.id_titulo_recurso_detalle);
        video= findViewById(R.id.id_link_YouTube);
        autor=findViewById(R.id.id_autor_recurso);
        fecha=findViewById(R.id.id_fecha_recurso);
        descripcion=findViewById(R.id.id_contenido_recurso);
        Bundle envio =   getIntent().getExtras();
        final RecursoVo recurso = (RecursoVo) envio.getSerializable("recurso");
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



    }
}
