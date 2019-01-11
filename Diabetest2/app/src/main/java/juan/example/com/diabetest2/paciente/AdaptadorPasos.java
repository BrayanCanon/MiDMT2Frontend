package juan.example.com.diabetest2.paciente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.misioncruds.RecursosAsignadosMis;
import juan.example.com.diabetest2.util.Conexion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdaptadorPasos extends RecyclerView.Adapter<AdaptadorPasos.ViewHolderPasos> {
    ArrayList<PasoVo> listaPasos;
    ArrayList<VerificacionVo> listaverif;
    Context con;
    private Integer diascomp;
    public static boolean diaverificado=false;



    public Integer getDiascomp() {
        return diascomp;
    }

    public void setDiascomp(Integer diascomp) {
        this.diascomp = diascomp;
    }

    public AdaptadorPasos(ArrayList<PasoVo> listaPasos, ArrayList<VerificacionVo> listaverif, Context con ) {
        this.listaPasos = listaPasos;
        this.listaverif=listaverif;
        this.con=con;

    }
    public AdaptadorPasos(ArrayList<PasoVo> listaPasos,ArrayList<VerificacionVo> listaverif,Context con,Integer diascomp ) {
        this.listaPasos = listaPasos;
        this.listaverif=listaverif;
        this.con=con;
        this.diascomp=diascomp;

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
                String fecha = verificacion.getFecha();
                Date current = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoserv = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a",Locale.ENGLISH);
                Date c = null;
                try {
                     c=formatoserv.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                String fechaverif=format.format(c);
                String fechaactual =format.format(current);
                if(position>((diascomp)+1 )){
                    holder.verif.setVisibility(View.INVISIBLE);
                }
                if(fechaverif.equals(fechaactual)){
                    diaverificado=true;
                }
                if(diaverificado==true){
                    holder.verif.setVisibility(View.INVISIBLE);

                }





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
                           new Conexion("consultarLogroGanado", nombres, new Conexion.Comunicado() {
                                @Override
                                public void salidas(String output) {
                                    if(output!= null ) {
                                        Gson gson = new Gson();
                                        JsonObject logro = gson.fromJson(output,JsonObject.class);
                                        AlertDialog.Builder logrodial = new AlertDialog.Builder(con);
                                        if(logro.get("idLogro").getAsInt()!=38){
                                       logrodial.setTitle(" Felicidades Ganaste "+ logro.get("nomLogro").getAsString());
                                        logrodial.setMessage(logro.get("descripcion").getAsString());
                                        logrodial.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intento = new Intent(con, misiones.class);

                                                intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                con.startActivity(intento);
                                            }
                                        });
                                       logrodial.create();
                                        logrodial.show();



                                    }else{Intent intento = new Intent(con, misiones.class);

                                        intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        con.startActivity(intento);}}

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
