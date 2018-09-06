package juan.example.com.diabetest2.paciente;

import android.content.Context;
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
import juan.example.com.diabetest2.util.RecursoVo;

public class AdaptadorRecursos extends  RecyclerView.Adapter<AdaptadorRecursos.ViewHolderRecursos> {
    ArrayList<RecursoVo> recursos;
    Context contexto;

    public AdaptadorRecursos(ArrayList<RecursoVo> recursos, Context con) {
        this.recursos = recursos;
        this.contexto = con;
    }

    @Override
    public AdaptadorRecursos.ViewHolderRecursos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_recursos, null, false);
        return new ViewHolderRecursos(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorRecursos.ViewHolderRecursos holder, int position) {
        String urlImagenes = Inicio.urlImagenes + recursos.get(position).getFoto();
        ;
        Log.d("urlimagenes", urlImagenes);
        holder.titulo.setText(recursos.get(position).getTitulo());
        holder.descripcion.setText(recursos.get(position).getDecripcion());
        Picasso.with(contexto).load(urlImagenes).fit().noFade().into(holder.imagen);


    }

    @Override
    public int getItemCount() {
        return recursos.size();
    }

    public class ViewHolderRecursos extends RecyclerView.ViewHolder {
        TextView titulo, descripcion;
        ImageView imagen;

        public ViewHolderRecursos(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.t1);
            descripcion = itemView.findViewById(R.id.t2);
            imagen = itemView.findViewById(R.id.id_im2);
        }
    }
}