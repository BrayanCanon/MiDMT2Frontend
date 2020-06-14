package juan.example.com.diabetest2.paciente;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;

public class AdapterMision extends RecyclerView.Adapter<AdapterMision.ViewHolderMision> implements View.OnClickListener{
    ArrayList<MisionVo> listaMisiones;
    private View.OnClickListener listener;

    public AdapterMision(ArrayList<MisionVo> listaMisiones ){
        this.listaMisiones=listaMisiones;


    }

    @Override
    public ViewHolderMision onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mision_list,null,false);
        view.setOnClickListener(this);
        return new ViewHolderMision(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMision holder, int position) {
        holder.titulo.setText(listaMisiones.get(position).getTitulo());
        holder.dificultad.setText(listaMisiones.get(position).getDificultad());
        holder.categoria.setText(listaMisiones.get(position).getCategoria());
        /* 1 ejercicio
        2 alimentacion
        3 en progreso
        4 completada
         */
        if(listaMisiones.get(position).getCondicionImagen()==1){
            holder.imagen.setImageResource(R.drawable.ejmis);

        }else if (listaMisiones.get(position).getCondicionImagen()==2){
            holder.imagen.setImageResource(R.drawable.alimmis);

        }



    }

    @Override
    public int getItemCount() {
        return listaMisiones.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null)
        {
            listener.onClick(view);
        }
    }

    public class ViewHolderMision extends RecyclerView.ViewHolder {
        TextView titulo,dificultad,categoria;
        ImageView imagen;


        public ViewHolderMision(View itemView) {
            super(itemView);
            titulo=  itemView.findViewById(R.id.titulo);
            dificultad= itemView.findViewById(R.id.dificultad);
            categoria=itemView.findViewById(R.id.categoria);
            imagen =itemView.findViewById(R.id.imagen_mision);


        }
    }
}
