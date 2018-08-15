package juan.example.com.diabetest2.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;

import juan.example.com.diabetest2.R;

public class RecursoAdapter extends RecyclerView.Adapter<RecursoAdapter.MyViewHolder> {


    private ArrayList<Recurso> recursos;
    private Context este;
    private String codigo;

   public class MyViewHolder extends RecyclerView.ViewHolder {
       public TextView nombre;
       public CheckBox selec;
       public MyViewHolder(View view){
           super(view);
           nombre=(TextView)view.findViewById(R.id.nombre);
          // selec=(CheckBox)view.findViewById(R.id.selec);
       }

   }

   public RecursoAdapter(ArrayList<Recurso> recursos,Context este,String codigo)
   {
       this.recursos=recursos;
       this.este=este;
       this.codigo=codigo;
   }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemv= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.recursos_recy,parent,false);

        return new MyViewHolder(itemv);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       final Recurso rec=recursos.get(position);
       holder.nombre.setText(rec.getNombre());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   Class casl = Class.forName(rec.getRecursonom());
                   Intent iniciar = new Intent(este,casl);
                   iniciar.putExtra("codigo",codigo);
                   este.startActivity(iniciar);

               }
               catch (Exception e){
                   Log.d("clase",e.toString());
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return recursos.size();
    }

    public ArrayList<Recurso> getres() {
        return recursos;
    }
}
