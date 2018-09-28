package juan.example.com.diabetest2.profesional.misioncruds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.util.Mision;
import juan.example.com.diabetest2.util.RecursoVo;

public class AdaptadorRecursos extends RecyclerView.Adapter<AdaptadorRecursos.ViewHolderRecursos> {
    ArrayList<RecursoVo> listaRecurso;
    Context contexto;
    Boolean asignar;
    Mision mis;

    public AdaptadorRecursos(ArrayList<RecursoVo> listaRecurso, Context con,Boolean asignar,Mision mis) {
        this.listaRecurso = listaRecurso;
        this.contexto=con;
        this.asignar=asignar;
        this.mis=mis;
    }

    @Override
    public ViewHolderRecursos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_recursos,parent,false);
        return new ViewHolderRecursos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderRecursos holder, final int position) {
         String urlImagenes = Inicio.urlImagenes+listaRecurso.get(position).getFoto();;
        Log.d("urlimagenes",urlImagenes);
        holder.titulo.setText(listaRecurso.get(position).getTitulo());
        holder.descripcion.setText(listaRecurso.get(position).getDecripcion());
       Picasso.with(contexto).load(urlImagenes).fit().noFade().into(holder.imagen);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento=new Intent(contexto, AsignacionRecursos.class);
                Bundle envio = new Bundle();
                RecursoVo recurso = listaRecurso.get(position);
                envio.putSerializable("recurso",recurso);
                envio.putBoolean("asignar",asignar);
                envio.putParcelable("mision",mis);
                intento.putExtras(envio);
                contexto.startActivity(intento); }
        });





    }

    @Override
    public int getItemCount() {
        return listaRecurso.size();
    }

    public class ViewHolderRecursos extends RecyclerView.ViewHolder {
        TextView titulo,descripcion;
        ImageView imagen;
        public ViewHolderRecursos(View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.t1);
            descripcion= itemView.findViewById(R.id.t2);
            imagen=itemView.findViewById(R.id.id_im2);

        }
    }
}
