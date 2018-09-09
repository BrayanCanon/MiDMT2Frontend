package juan.example.com.diabetest2.paciente;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import juan.example.com.diabetest2.R;

import java.util.ArrayList;

public class AdaptadorPasos extends RecyclerView.Adapter<AdaptadorPasos.ViewHolderPasos> {
    ArrayList<PasoVo> listaPasos;

    public AdaptadorPasos(ArrayList<PasoVo> listaPasos) {
        this.listaPasos = listaPasos;
    }

    @Override
    public ViewHolderPasos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pasos,null,false);
        return new ViewHolderPasos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPasos holder, int position) {
        holder.dia.setText(Integer.toString(listaPasos.get(position).getOrden()));


    }


    @Override
    public int getItemCount() {
        return listaPasos.size();
    }

    public class ViewHolderPasos extends RecyclerView.ViewHolder {
        TextView dia ;
        public ViewHolderPasos(View itemView) {
            super(itemView);
            dia=itemView.findViewById(R.id.diaMision);

        }
    }
}
