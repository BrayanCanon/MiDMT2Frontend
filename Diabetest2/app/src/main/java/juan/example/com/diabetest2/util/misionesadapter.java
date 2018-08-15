package juan.example.com.diabetest2.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.modmision.Modmisp;

public class misionesadapter extends RecyclerView.Adapter<misionesadapter.MyViewHolder>  {

    ArrayList<Mision> misiones;
    Context este;


    public misionesadapter(ArrayList<Mision> misiones, Context este){
        this.misiones=misiones;
        this.este=este;
    }


    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView categoria;
        TextView nivel;

        public MyViewHolder(View itemView) {
            super(itemView);
            nombre=(TextView)itemView.findViewById(R.id.nombre45);
            categoria=(TextView)itemView.findViewById(R.id.categoria45);
            nivel=(TextView)itemView.findViewById(R.id.nivell12);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantilla_mision, parent, false);
        //itemView.setOnClickListener(mOnClickListener);
        return new misionesadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
           final Mision pos= misiones.get(position);

           holder.nombre.setText(pos.getNombre());
           holder.categoria.setText(pos.getCategoria());
           holder.nivel.setText(String.valueOf(pos.getNivel()));
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent modificar=new Intent(este, Modmisp.class);
                   modificar.putExtra("mision",pos);
                   este.startActivity(modificar);

               }
           });
    }

    @Override
    public int getItemCount() {
        return misiones.size();
    }




}
