package juan.example.com.diabetest2.paciente;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;

public class AdapterMision extends RecyclerView.Adapter<AdapterMision.ViewHolderMision> {
    ArrayList<MisionVo> listaMisiones;

  public AdapterMision(ArrayList<MisionVo> listaMisiones){
      this.listaMisiones=listaMisiones;

  }

    @Override
    public ViewHolderMision onCreateViewHolder(ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mision_list,null,false);
        return new ViewHolderMision(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderMision holder, int position) {
      holder.titulo.setText(listaMisiones.get(position).getCategoria());
      holder.dificultad.setText(listaMisiones.get(position).getDificultad());
      holder.categoria.setText(listaMisiones.get(position).getCategoria());



    }

    @Override
    public int getItemCount() {
        return listaMisiones.size();
    }

    public class ViewHolderMision extends RecyclerView.ViewHolder {
      TextView titulo,dificultad,categoria;

        public ViewHolderMision(View itemView) {
            super(itemView);
            titulo=  itemView.findViewById(R.id.titulo);
            dificultad= itemView.findViewById(R.id.dificultad);
            categoria=itemView.findViewById(R.id.categoria);
        }
    }
}
