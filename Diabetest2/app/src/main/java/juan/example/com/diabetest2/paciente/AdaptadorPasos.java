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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.misioncruds.RecursosAsignadosMis;
import juan.example.com.diabetest2.util.Conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdaptadorPasos extends RecyclerView.Adapter<AdaptadorPasos.ViewHolderPasos> {
    ArrayList<PasoVo> listaPasos;
    ArrayList<VerificacionVo> listaverif;
    Context con;
    int diferencia;
    String un;
    int diascomp;

    public Integer getDiascomp() {
        return diascomp;
    }

    public void setDiascomp(Integer diascomp) {
        this.diascomp = diascomp;
    }

    public AdaptadorPasos(ArrayList<PasoVo> listaPasos,ArrayList<VerificacionVo> listaverif,Context con,String un) {
        this.listaPasos = listaPasos;
        this.listaverif=listaverif;
        this.con=con;
        this.un=un;

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
            if(diaverificado == true ){
                holder.verif.setEnabled(false);
            }
            else if(listaverif.size()> position){
                verificacion=listaverif.get(position);
                String fecha = verificacion.getFecha();
                Date current = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoserv = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.ENGLISH);
                Date c = null;
                try {
                     c=formatoserv.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                String fechaverif=format.format(c);
                String fechaactual =format.format(current);

                if(fechaverif.equals(fechaactual)){
                    diaverificado=true;

                }
            }



        holder.verif.setChecked(verificacion.getVerif());
        holder.verif.setEnabled(!verificacion.getVerif());
        final VerificacionVo finalVerificacion = verificacion;
        holder.verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File archivo_fecha = new File(con.getFilesDir(), un+".dt2");
                Boolean primer_ingreso=false;
                if(!archivo_fecha.exists()){
                    Date hoy = new Date();

                    try {
                        FileOutputStream salida = new FileOutputStream(archivo_fecha);
                        ObjectOutputStream oos2 = new ObjectOutputStream(salida);
                        oos2.writeObject(hoy);
                        oos2.close();
                        salida.close();
                        primer_ingreso=true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Date hoy = new Date();
                    Date ultima_pulsacion;
                    try {
                        FileInputStream in = con.openFileInput(un+".dt2");
                        ObjectInputStream ois = new ObjectInputStream(in);
                        ultima_pulsacion=(Date) ois.readObject();
                        diferencia = (int) ( (hoy.getTime() - ultima_pulsacion.getTime()) / (1000 * 60 * 60 * 24) );
                        int i=1;
                        if(diferencia<1)
                        {
                            AlertDialog.Builder logrodial = new AlertDialog.Builder(con);
                            logrodial.setTitle("Por favor espere");
                            logrodial.setMessage("solo marque un paso al dia");
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

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                if(primer_ingreso || diferencia>1){
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
