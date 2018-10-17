package juan.example.com.diabetest2.paciente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

import java.util.ArrayList;

public class AdaptadorPasos extends RecyclerView.Adapter<AdaptadorPasos.ViewHolderPasos> {
    ArrayList<PasoVo> listaPasos;
    ArrayList<VerificacionVo> listaverif;
    Context con;


    public AdaptadorPasos(ArrayList<PasoVo> listaPasos,ArrayList<VerificacionVo> listaverif,Context con) {
        this.listaPasos = listaPasos;
        this.listaverif=listaverif;
        this.con=con;

    }

    @Override
    public ViewHolderPasos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pasos,null,false);
        return new ViewHolderPasos(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderPasos holder, final int position) {

        VerificacionVo verificacion= new VerificacionVo(position,false);
        holder.dia.setText(Integer.toString(listaPasos.get(position).getOrden()));
        if(listaPasos.get(position).getHabCheckbox()==true){
            holder.verif.setVisibility(View.INVISIBLE);
        }else{
        for(int i=0;i<listaverif.size();i++){
            if(listaPasos.get(position).getOrden()==listaverif.get(i).getNumDia()){
                verificacion=listaverif.get(i);
            }

        }

        holder.verif.setChecked(verificacion.getVerif());
        holder.verif.setEnabled(!verificacion.getVerif());
        final VerificacionVo finalVerificacion = verificacion;
        holder.verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(con);
                alerta.setTitle(" Verificación");
                alerta.setMessage("Esta seguro que quiere verificar que ha completado el paso?");

                alerta.setPositiveButton("verificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(finalVerificacion.getVerif()==false && holder.verif.isChecked()){
                            ArrayList<String> nombres= new ArrayList<>();
                            ArrayList<String> valores= new ArrayList<>();
                            nombres.add("idMisionPaciente");
                            nombres.add("numeroDia");
                            nombres.add("verifPaciente");
                            valores.add(listaPasos.get(position).getIdMisionPaciente());
                            valores.add(Integer.toString(listaPasos.get(position).getOrden()));
                            valores.add(Boolean.toString(true));



                            new Conexion("crearVerificacion", nombres, new Conexion.Comunicado() {
                                @Override

                                public void salidas(String output) {


                                }
                            }).execute(valores);



                        }

                    }
                });alerta.create();
                alerta.show();


            }
        });}

                                                }







    @Override
    public int getItemCount() {
        return listaPasos.size();
    }

    public class ViewHolderPasos extends RecyclerView.ViewHolder {
        TextView dia ;
        CheckBox verif;
        public ViewHolderPasos(View itemView) {
            super(itemView);
            dia=itemView.findViewById(R.id.diaMision);
            verif=itemView.findViewById(R.id.checkBox);

        }
    }
}
