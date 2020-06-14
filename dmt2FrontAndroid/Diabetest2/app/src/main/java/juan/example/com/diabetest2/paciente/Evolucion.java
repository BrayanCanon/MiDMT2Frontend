package juan.example.com.diabetest2.paciente;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.administrador.Inicio;
import juan.example.com.diabetest2.comunes.CrearMensaje;
import juan.example.com.diabetest2.administrador.ServicioDT2;
import juan.example.com.diabetest2.comunes.Mensajes;
import juan.example.com.diabetest2.profesional.MenuProfesional;
import juan.example.com.diabetest2.profesional.Observaciones;
import juan.example.com.diabetest2.util.Conexion;

// Autor: Juan David Velásquez Bedoya

public class Evolucion extends AppCompatActivity {

    static XYPlot pesoImc, animo, hba1c, glucosa;

    TextView nombre, correo, estado, telefono, edad;
    ImageView emoticon;
    Button borrar, chatear, detalle, observaciones, medicamentos, metas, glucosaBoton, pesoBoton;

    //-----
    Button chatboton,id_borrar_paciente,id_bt_medicinas,id_bt_observaciones_pro;
    Button deldetalle;
    //-----
    public static long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolucion);
        //---------------
        chatboton=(Button)findViewById(R.id.id_msj_privado);
        deldetalle=(Button)findViewById(R.id.id_detalle_paciente);
        id_borrar_paciente=(Button)findViewById(R.id.id_borrar_paciente);
        id_bt_medicinas=(Button)findViewById(R.id.id_bt_medicinas);
        id_bt_observaciones_pro=(Button)findViewById(R.id.id_bt_observaciones_pro);
        chatboton.setEnabled(false);
        deldetalle.setEnabled(false);
        id_borrar_paciente.setEnabled(false);
        id_bt_medicinas.setEnabled(false);
        id_bt_observaciones_pro.setEnabled(false);
        //---------------
        pesoImc = (XYPlot) findViewById(R.id.id_PlotPesoIMC);
        //animo = (XYPlot) findViewById(R.id.id_Ev_animo);
        hba1c = (XYPlot) findViewById(R.id.id_hba1c);
        glucosa = (XYPlot) findViewById(R.id.id_glucosa);

        nombre = (TextView) findViewById(R.id.id_nombres_apellidos);
        correo = (TextView) findViewById(R.id.id_correo);
        estado = (TextView) findViewById(R.id.id_estado);
        telefono = (TextView) findViewById(R.id.id_telefono);
        edad = (TextView) findViewById(R.id.id_edad);
        emoticon = (ImageView) findViewById(R.id.id_emoticon);
        borrar = (Button) findViewById(R.id.id_borrar_paciente);
        chatear = (Button) findViewById(R.id.id_msj_privado);
        detalle = (Button) findViewById(R.id.id_detalle_paciente);
        observaciones = (Button) findViewById(R.id.id_bt_observaciones_pro);
        medicamentos = (Button) findViewById(R.id.id_bt_medicinas);
        glucosaBoton = (Button) findViewById(R.id.btn_glucosa);
        pesoBoton = (Button) findViewById(R.id.btn_peso);
        //identificacion del id_destinatario a presentar
        if(Inicio.rol.contains("paciente")){
            id = ServicioDT2.idLocal;
            borrar.setVisibility(View.INVISIBLE);
            observaciones.setVisibility(View.INVISIBLE);
            chatear.setVisibility(View.INVISIBLE);
            //Cambios del botón iniciarChat
            //chatear.setText("GLUCOSA");
            /*chatear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingresarIdFamiliar(null);
                }
            });*/
        }
        if(Inicio.rol.contains("familiar")){
            chatear.setVisibility(View.INVISIBLE);
            borrar.setVisibility(View.INVISIBLE);
            observaciones.setVisibility(View.INVISIBLE);
            medicamentos.setVisibility(View.INVISIBLE);
            detalle.setVisibility(View.INVISIBLE);
            pesoBoton.setVisibility(View.INVISIBLE);
            glucosaBoton.setVisibility(View.INVISIBLE);
        }
        Number[] valorGlucosa= {1,10,5,9,7,4};
        Number[] fechasGlucosa= {1,2,3,4,5,6};
        XYSeries seriesGlucosa = new SimpleXYSeries(
                Arrays.asList(fechasGlucosa),
                Arrays.asList(valorGlucosa),
                "seriesGlucosa");

        LineAndPointFormatter seriesFormatGlucosa = new LineAndPointFormatter(
                Color.rgb(255,51,69),
                Color.rgb(255,51,69),
                Color.rgb(255,168,168), null
        );
        glucosa.addSeries(seriesGlucosa, seriesFormatGlucosa);

        Number[] valorPeso= {1,10,5,9,7,4};
        Number[] fechasPeso= {1,2,3,4,5,6};
        XYSeries seriesPeso = new SimpleXYSeries(
                Arrays.asList(fechasPeso),
                Arrays.asList(valorPeso),
                "seriesPeso");

        LineAndPointFormatter seriesFormatPeso = new LineAndPointFormatter(
                Color.rgb(54,94,249),
                Color.rgb(54,94,249),
                Color.rgb(168,186,255), null
        );
        pesoImc.addSeries(seriesPeso, seriesFormatPeso);
        /*Evolucion.Consultar co = new Evolucion.Consultar();
        co.execute();
*/
        Evolucion.ConsultarPaciente coP = new Evolucion.ConsultarPaciente();
        coP.execute();
    }

    // Ingreso de la glucosa
    int medidaGlucosa = 0;
    public void ingresarIdFamiliar(View v){
        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("Ingrese la medición de glucosa, mg/dL");
        final EditText glucosa = new EditText(this);
        alerta.setView(glucosa);
        alerta.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(glucosa.getText().length()>0) {
                    medidaGlucosa = Integer.parseInt(glucosa.getText().toString().trim());
                    Evolucion.Glucosa gl = new Evolucion.Glucosa();
                    if (medidaGlucosa > 0) {
                        gl.execute();
                    }
                }
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alerta.show();
    }
    //Ingreso glucosa en bd ---------------
    private class Glucosa extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "crearGlucosa");
                solicitud.addProperty("id", id);
                solicitud.addProperty("glucosa", medidaGlucosa);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/crearGlucosa", sobre);
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(Evolucion.this, "Medición guardada!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //Graficos ---------------
    Vector <String> tablaPesoImc = new Vector<>();
    Vector <String> tablaAnimo = new Vector<>();//No se esta trabajando
    Vector <String> tablaHba1c = new Vector<>();
    Vector <String> tablaGlucosa = new Vector<>();

    private class Consultar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                //Consulta peso e imc
                SoapObject solicitud = new SoapObject(Inicio.namespace, "consultarVerificacionRutinaPacienteValores");
                solicitud.addProperty("codPaciente", ""+id);
                solicitud.addProperty("idRutina", 2);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/consultarVerificacionRutinaPacienteValores", sobre);
                SoapObject temp=(SoapObject) sobre.bodyIn;

                //Consulta del animo
                solicitud = new SoapObject(Inicio.namespace, "consultarAnimo");
                solicitud.addProperty("id", id);
                sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                transporte.call("http://Servicios/consultarAnimo", sobre);
                temp=(SoapObject)sobre.bodyIn;
                for(int a=0;a<temp.getPropertyCount();a++) tablaAnimo.add(temp.getProperty(a).toString());
                //Consulta del Glucos

                //Consulta del HbA1c
                solicitud = new SoapObject(Inicio.namespace, "consultarHba1c");
                solicitud.addProperty("id", id);
                sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                transporte.call("http://Servicios/consultarHba1c", sobre);
                temp=(SoapObject)sobre.bodyIn;
                for(int a=0;a<temp.getPropertyCount();a++) tablaHba1c.add(temp.getProperty(a).toString());

                //Consulta del Glucosa
                solicitud = new SoapObject(Inicio.namespace, "consultarVerificacionRutinaPacienteValores");
                solicitud.addProperty("codPaciente", ""+id);
                solicitud.addProperty("idRutina", 2);
                sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporteVerificacion = new HttpTransportSE(Inicio.urlVerificacion);
                transporteVerificacion.call("http://Servicios/consultarVerificacionRutinaPacienteValores", sobre);
                temp=(SoapObject)sobre.bodyIn;
                for(int a=0;a<temp.getPropertyCount();a++) tablaGlucosa.add(temp.getProperty(a).toString());
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                //Acomodación de la consulta obtenida
                // Nuevos vectores con tipos para los gráficos
                //Vectores Peso IMC
               /* final Vector <String> fechasX = new Vector();
                Vector <Number> pesoX = new Vector();
                Vector <Number> imcX = new Vector();
                int i = 0;
                if(tablaPesoImc.size()>24){ i = tablaPesoImc.size()-24;}
                while(i< tablaPesoImc.size()){
                    fechasX.add(tablaPesoImc.get(i));
                    pesoX.add((int)Double.parseDouble(tablaPesoImc.get(i+1)) );
                    imcX.add((int)Double.parseDouble(tablaPesoImc.get(i+2)));
                    i = i+3;
                }
                //Vectores Animo
                final Vector <String> fechasX2 = new Vector();
                Vector <Number> animoX = new Vector();
                i = 0;
                if(tablaAnimo.size()>30){ i = tablaAnimo.size()-30;}
                while(i< tablaAnimo.size()){
                    fechasX2.add(tablaAnimo.get(i));
                    animoX.add((int)Double.parseDouble(tablaAnimo.get(i+1)));
                    i = i+2;
                }
                //Vectores Hba1c
                final Vector <String> fechasX3 = new Vector();
                Vector <Double> hba1cX = new Vector();
                i = 0;
                if(tablaHba1c.size()>12){ i = tablaHba1c.size()-12;}
                while(i< tablaHba1c.size()){
                    fechasX3.add(tablaHba1c.get(i));
                    hba1cX.add(Double.parseDouble(tablaHba1c.get(i+1)));
                    i = i+2;
                }
                //Vector glucosa
                /*final Vector <String> fechasX4 = new Vector();
                Vector <Integer> glucosaX = new Vector();
                i = 0;
                if(tablaGlucosa.size()>0 ){ i = tablaGlucosa.size()-0;}
                while(i< tablaGlucosa.size() && tablaGlucosa.size()>0){
                    fechasX4.add(tablaGlucosa.get(i).substring(5,16));
                    glucosaX.add((int)Double.parseDouble(tablaGlucosa.get(i+1)));
                    i = i+2;
                }
                if(tablaGlucosa.size()>0) {
                    //Grafico glucosa ---------------------------------------------------------------------
                    Number[] glucosaValores = glucosaX.toArray(new Number[glucosaX.size()]);
                    XYSeries s0 = new SimpleXYSeries(Arrays.asList(glucosaValores), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Glucosa");

                    LineAndPointFormatter f1x = new LineAndPointFormatter(Color.argb(50, 163, 0, 0), Color.rgb(85, 0, 0), Color.argb(254, 255, 181, 181), new PointLabelFormatter(Color.DKGRAY));
                    glucosa.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE); // fondo de los ejes
                    glucosa.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE); //fondo del grafico
                    glucosa.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);//Intervalo del dominio
                    glucosa.setRangeValueFormat(new DecimalFormat("0")); //Quitar el decimal de las etiquetas del rango
                    glucosa.addSeries(s0, f1x);
                    glucosa.redraw();
                    //Cambio valores del eje x
                    glucosa.setDomainValueFormat(new NumberFormat() {
                        @Override
                        public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) {
                            return new StringBuffer(fechasX4.get((int) value));
                        }

                        @Override
                        public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) {
                            return null;
                        }

                        @Override
                        public Number parse(String string, ParsePosition position) {
                            return null;
                        }
                    });
                }


                //Graficos Peso e IMC (2 en 1)-----------------------------------------------------------------
                Number[] peso = pesoX.toArray(new Number[pesoX.size()]);
                Number[] imc =  imcX.toArray(new Number[imcX.size()]);

                XYSeries s1 = new SimpleXYSeries(Arrays.asList(peso), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Peso" );
                XYSeries s2 = new SimpleXYSeries(Arrays.asList(imc), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "IMC" );

                LineAndPointFormatter f1 = new LineAndPointFormatter(Color.argb(50, 0, 200,0), Color.rgb(0, 100, 0),  Color.argb(254, 255, 204, 102), new PointLabelFormatter(Color.DKGRAY));
                LineAndPointFormatter f2 = new LineAndPointFormatter(Color.argb(50, 0, 0,200), Color.rgb(0, 0, 100),  Color.argb(125 , 255, 92, 51), new PointLabelFormatter(Color.WHITE));
                pesoImc.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE); // fondo de los ejes
                pesoImc.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE); //fondo del grafico
                pesoImc.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);//Intervalo del dominio
                pesoImc.setRangeValueFormat(new DecimalFormat("0")); //Quitar el decimal de las etiquetas del rango

                pesoImc.addSeries(s1, f1);
                pesoImc.addSeries(s2, f2);
                pesoImc.redraw();
                //Cambio valores del eje x
                pesoImc.setDomainValueFormat(new NumberFormat() {
                    @Override
                    public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) { return new StringBuffer(fechasX.get((int) value)); }
                    @Override
                    public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) { return null; }
                    @Override
                    public Number parse(String string, ParsePosition position) { return null; }
                });

                //Grafico animo -----------------------------------------------------------------
                Number[] nivelAnimo =  animoX.toArray(new Number[animoX.size()]);
                XYSeries s3 = new SimpleXYSeries(Arrays.asList(nivelAnimo), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Nivel de ánimo registrado" );
                BarFormatter bf1 = new BarFormatter(Color.rgb(221, 234, 151), Color.rgb(0,125,215));
                //Colores del entorno de gráfico
                animo.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE); //fondo del grafico
                animo.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE); // fondo de los ejes
                animo.getGraphWidget().getDomainGridLinePaint().setColor(Color.TRANSPARENT); // lineas verticales de referencia
                animo.getGraphWidget().getRangeSubGridLinePaint().setColor(Color.rgb(227,227,227)); // lineas horizontales de referencia
                animo.getGraphWidget().getDomainLabelPaint().setColor(Color.GRAY); //Color etiquetas dominio
                animo.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1); //Intervalo del dominio
                animo.addSeries(s3, bf1);
                BarRenderer renderer = (BarRenderer) animo.getRenderer(BarRenderer.class); //Necesario para ampliar el ancho de las barras
                renderer.setBarWidthStyle(BarRenderer.BarWidthStyle.VARIABLE_WIDTH); //Define estilo para el ancho, puede ser FIXED_WIDTH
                renderer.setBarWidth(30); //Ancho barras
                renderer.setBarGap(4f); // Separación de barras

                //Configuración del rango
                animo.getGraphWidget().setRangeOriginLinePaint(null);
                animo.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
                animo.setRangeValueFormat(new DecimalFormat("0"));

                //Cambio valores del eje x
                animo.setDomainValueFormat(new NumberFormat() {
                    @Override
                    public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) { return new StringBuffer(fechasX2.get((int) value)); }
                    @Override
                    public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) { return null; }
                    @Override
                    public Number parse(String string, ParsePosition position) { return null; }
                });

                animo.redraw();

                //Grafico HbA1c -----------------------------------------------------------------
                Number[] nivelHba1c =  hba1cX.toArray(new Number[hba1cX.size()]);
                XYSeries s4 = new SimpleXYSeries(Arrays.asList(nivelHba1c), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Porcentajes de HbA1C registrado" );
                BarFormatter bf = new BarFormatter(Color.rgb(255, 77, 77), Color.WHITE);
                hba1c.addSeries(s4, bf);
                BarRenderer renderer2 = (BarRenderer) hba1c.getRenderer(BarRenderer.class); //Necesario para ampliar el ancho de las barras
                renderer2.setBarWidthStyle(BarRenderer.BarWidthStyle.VARIABLE_WIDTH); //Define estilo para el ancho, puede ser FIXED_WIDTH
                renderer2.setBarWidth(100); //Ancho barras
                renderer2.setBarGap(4f); // Separación de barras
                hba1c.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);//Intervalo del dominio
                hba1c.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE); // fondo de los ejes
                hba1c.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE); //fondo del grafico
                hba1c.redraw();
                //Cambio valores del eje x
                hba1c.setDomainValueFormat(new NumberFormat() {
                    @Override
                    public StringBuffer format(double value, StringBuffer buffer, FieldPosition field) { return new StringBuffer(fechasX3.get((int) value)); }
                    @Override
                    public StringBuffer format(long value, StringBuffer buffer, FieldPosition field) { return null; }
                    @Override
                    public Number parse(String string, ParsePosition position) { return null; }
                });*/

            }
        }

    }
    // Datos generales del paciente  ---------------------------
    Vector datosPaciente = new Vector();

    private class ConsultarPaciente extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "paciente");
                solicitud.addProperty("id", id);
                solicitud.addProperty("op", 0); // 0 es Consultar
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/paciente", sobre);
                SoapObject temp=(SoapObject)sobre.bodyIn;
                for(int a=0;a<temp.getPropertyCount();a++) datosPaciente.add(temp.getProperty(a).toString());
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                int i = 0;
                Calendar fa = new GregorianCalendar();


                while(i< datosPaciente.size()){
                    correo.setText((CharSequence) datosPaciente.get(i));
                    nombre.setText((CharSequence)datosPaciente.get(i+1) +" "+ (CharSequence)datosPaciente.get(i+2));
                    if((datosPaciente.get(i+3)).equals("1")){estado.setText("Activo");}else {estado.setText("Inactivo");} //Cambio de valor numero a texto activo o inactivo en el estado
                    telefono.setText((CharSequence)datosPaciente.get(i+4));
                    try {
                        /*if (((String) datosPaciente.get(i + 10)).contains("Femenino")) {
                            emoticon.setImageResource(R.drawable.woman);
                        }*/
                        String f = ((String) datosPaciente.get(i + 8)).substring(0, 4);// Fecha de nacimiento
                        Date dt = new Date();
                        f = String.valueOf((1900 + dt.getYear()) - Integer.parseInt(f));
                        edad.setText(f);
                    }
                    catch (Exception e){}
                    i = 20;

                }
                //------------------------desbloquear en caso de cargar datos
                chatboton.setEnabled(true);
                deldetalle.setEnabled(true);
                id_borrar_paciente.setEnabled(true);
                id_bt_medicinas.setEnabled(true);
                id_bt_observaciones_pro.setEnabled(true);
                //------------------------
            }
        }
    }

    //Borrar al paciente ---------------
    Vector respuesta;
    private class borrar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SoapObject solicitud = new SoapObject(Inicio.namespace, "paciente");
                solicitud.addProperty("id", id);
                solicitud.addProperty("op", 1); // 1 es borrar
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.setOutputSoapObject(solicitud);
                HttpTransportSE transporte = new HttpTransportSE(Inicio.url);
                transporte.call("http://Servicios/paciente", sobre);
                respuesta = (Vector) sobre.bodyIn;
            } catch (Exception e) {}
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            if (success == true) {
                Toast.makeText(Evolucion.this, ""+respuesta.get(0).toString(), Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(getApplicationContext(), MenuProfesional.class);
                startActivity(intento);
            }
        }
    }

    public void cuadroAlerta(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Atención!, se desactivará el paciente!");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Evolucion.borrar bo = new Evolucion.borrar();
                        bo.execute();
                    }
                });
        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Enviar mensaje
    public void enviarMensaje(View v) {
        //CrearMensaje.idDestino = id;
        //CrearMensaje.destinatario = datosPaciente.get(1) +" "+ datosPaciente.get(2);
        Mensajes.idDestino=id;
        Mensajes.destinatario=datosPaciente.get(1) +" "+ datosPaciente.get(2);
        //Intent intento = new Intent(this, CrearMensaje.class);
        Intent intento = new Intent(this, Mensajes.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    // Ver plantilla_detalle del paciente
    public void abrirDetalle(View v) {
        //Intent intento = new Intent(this, DetallePaciente.class);
        //if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }

        ArrayList nombres=new ArrayList();
        ArrayList valores=new ArrayList();
        nombres.add("codPaciente");valores.add(Evolucion.id);

        new Conexion("generarExcel", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Inicio.domain+"/reportes/"+Evolucion.id+".xlsx"));
                startActivity(browserIntent);
            }
        }).execute(valores);

    }
    // Ver metas del paciente
    public void abrirMetas(View v) {
        Intent intento = new Intent(this, Metas.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    // Ver plantilla_detalle del paciente
    public void abrirObservaciones(View v) {
        Intent intento = new Intent(this, Observaciones.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    // Ver plantilla_detalle del paciente
    public void abrirMedicamentos(View v) {
        Intent intento = new Intent(this, Medicamentos.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    // Ver plantilla de la inserción de glucosa
    public void abrirGlucosa(View v) {
        Intent intento = new Intent(this, VerificacionGlucosa.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }
    // Ver plantilla de la inserción de glucosa
    public void abrirPeso(View v) {
        Intent intento = new Intent(this, VerificacionPeso.class);
        if(probarInternet() == false){ Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show(); } else{ startActivity(intento); }
    }

    //Chequear conexion a internet  ----------------------------------
    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}