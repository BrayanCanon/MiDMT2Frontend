package juan.example.com.diabetest2.paciente;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;

public class AdaptadorRecursos extends  RecyclerView.Adapter<AdaptadorRecursos.ViewHolderRecursos>  {
    ArrayList<RecursosVo> recursos;

    public AdaptadorRecursos(ArrayList<RecursosVo> recursos) {
        this.recursos = recursos;
    }

    @Override
    public AdaptadorRecursos.ViewHolderRecursos onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pasos,null,false);
        return new ViewHolderRecursos(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorRecursos.ViewHolderRecursos holder, int position) {
        holder.descripcion.setText(recursos.get(position).getDescripcion());
        holder.nombre.setText(recursos.get(position).getNombre());
        holder.orden.setText(String.valueOf(position));


    }

    @Override
    public int getItemCount() {
        return recursos.size();
    }

    public class ViewHolderRecursos extends RecyclerView.ViewHolder {
        TextView orden,nombre,descripcion;
        public ViewHolderRecursos(View itemView) {

            super(itemView);
            orden=itemView.findViewById(R.id.orden);
            nombre=itemView.findViewById(R.id.nombre);
            descripcion=itemView.findViewById(R.id.descripcion);
        }
    }
}
