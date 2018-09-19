package juan.example.com.diabetest2.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.misioncruds.Recursos_crud;
import juan.example.com.diabetest2.profesional.modmision.Modmisp;

public class misionesadapter extends RecyclerView.Adapter<misionesadapter.MyViewHolder>  {

    ArrayList<Mision> misiones;
    Context este;
    Boolean recursos= false;
    final RecyclerView.Adapter esteadap=this;


    public misionesadapter(ArrayList<Mision> misiones, Context este,Boolean recursos){
        this.misiones=misiones;
        this.este=este;
        this.recursos=recursos;
    }


    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView categoria;
        TextView nivel;
        Button borrar;

        public MyViewHolder(View itemView) {
            super(itemView);
            nombre=(TextView)itemView.findViewById(R.id.nombre45);
            categoria=(TextView)itemView.findViewById(R.id.categoria45);
            nivel=(TextView)itemView.findViewById(R.id.nivell12);
            borrar=(Button)itemView.findViewById(R.id.button46);
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
        final Mision pos = misiones.get(position);
        if (recursos == false) {


            holder.borrar.setVisibility(View.VISIBLE);

           holder.nombre.setText(pos.getNombre());
           holder.categoria.setText(pos.getCategoria());
           holder.nivel.setText(" ");
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent modificar=new Intent(este, Modmisp.class);
                   modificar.putExtra("mision",pos);
                   este.startActivity(modificar);

               }
           });


           holder.borrar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   ArrayList<String> nombres=new ArrayList<>();
                   ArrayList valores=new ArrayList<>();
                   nombres.add("idMision"); valores.add(pos.getIdMision());
                   new Conexion("borrarMisiones", nombres, new Conexion.Comunicado() {
                       @Override
                       public void salidas(String output) {

                       }
                   }).execute(valores);

                   misiones.remove(position);
                   esteadap.notifyDataSetChanged();
               }
           });}

    if(recursos==true){
            holder.nombre.setText(pos.getNombre());
            holder.categoria.setText(pos.getCategoria());
            holder.nivel.setText(" ");
            holder.borrar.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent seleccion=new Intent(este,Recursos_crud.class);
                    seleccion.putExtra("mision",pos);
                    este.startActivity(seleccion);

                }
            });

        }
}

    @Override
    public int getItemCount() {
        return misiones.size();
    }




}
