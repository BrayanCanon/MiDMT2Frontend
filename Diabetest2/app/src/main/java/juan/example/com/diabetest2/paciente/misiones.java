package juan.example.com.diabetest2.paciente;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import juan.example.com.diabetest2.PasosMision;
import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

public class misiones extends AppCompatActivity {
    ArrayList<MisionVo> listaMisiones,listaNuevasMisiones;
    RecyclerView recylerMisiones,recyclerNuevasMisiones;
    AdapterMision adapter,adapterNuevasMis;
    Context vista = this;
    ProgressBar ejercicio,alimentacion;
    TextView nivEjercicio,nivAlim;
    String idGuardado;
    String idFamguard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misiones);

        FileInputStream in2 = null;

        //-----------------------------
        try {
            in2 = openFileInput("id.dt2");
            ObjectInputStream ois2 = new ObjectInputStream(in2);
            idGuardado =  ois2.readObject().toString();

            //-------------------

            //-------------------
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //-----------------------------
        if (getIntent().hasExtra("fampuestos"))
        {
            idFamguard=idGuardado;
            idGuardado=getIntent().getExtras().getString("fampuestos");

        }
        //-----------------------------
        listaMisiones= new ArrayList<>();
        listaNuevasMisiones = new ArrayList<>();
        recylerMisiones=findViewById(R.id.recyclerMisiones);
        recyclerNuevasMisiones=findViewById(R.id.recNuevasMis);
        adapterNuevasMis= new AdapterMision(listaNuevasMisiones);
        adapter = new AdapterMision(listaMisiones);
        ejercicio = findViewById(R.id.barra_ej);
        alimentacion = findViewById(R.id.barra_alim);
        nivEjercicio=findViewById(R.id.NivelActEjer);
        nivAlim=findViewById(R.id.NivelAcrAlim);
        llenarBarraEjercicio();
        llenarBarraAlimentacion();

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(misiones.this,VerMisionPaciente.class);
                startActivity(intento);
                Bundle envio = new Bundle();
                MisionVo mision = listaMisiones.get(recylerMisiones.getChildAdapterPosition(view));
                envio.putSerializable("mision",mision);
                envio.putString("codPaciente",idGuardado);
                envio.putBoolean("habEmpezarMision",false);
                if(idFamguard != null && !idFamguard.isEmpty()) envio.putString("codApoyo",idFamguard);
                intento.putExtras(envio);
                startActivity(intento);


            }
        });
        adapterNuevasMis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(misiones.this,VerMisionPaciente.class);
                startActivity(intento);
                Bundle envio = new Bundle();
                MisionVo mision = listaNuevasMisiones.get(recyclerNuevasMisiones.getChildAdapterPosition(view));
                envio.putSerializable("mision",mision);
                envio.putString("codPaciente",idGuardado);
                envio.putBoolean("habEmpezarMision",true);
                intento.putExtras(envio);
                startActivity(intento);


            }
        });
        recyclerNuevasMisiones.setAdapter(adapterNuevasMis);
        recylerMisiones.setAdapter(adapter);
        recyclerNuevasMisiones.setItemAnimator(new DefaultItemAnimator());

        recylerMisiones.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerNuevasMisiones.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recylerMisiones.setLayoutManager(horizontalLayoutManager);

        try {
            llenarMisiones();
            llenarNuevasMis();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void llenarMisiones() throws IOException {

        FileInputStream in2 = null;

            ArrayList<String> nombres= new ArrayList<>();
            ArrayList valores= new ArrayList();
            nombres.add("codPaciente");
            valores.add(idGuardado);
            new Conexion("consultarMisPaciente", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    if (!output.equals("null")) {
                        Gson gson = new Gson();
                        JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                        JsonObject salida;
                        JsonObject mision;
                        JsonObject categoria;
                        JsonObject nivel;
                        for (int a = 0; a < arreglo.size(); a++) {
                            salida = arreglo.get(a).getAsJsonObject();
                            mision = salida.get("idMision").getAsJsonObject();
                            categoria = mision.get("idCategoria").getAsJsonObject();
                            nivel = mision.get("idNivel").getAsJsonObject();
                            MisionVo mis = new MisionVo(mision.get("nombre").getAsString(), categoria.get("nombreCategoria").getAsString(), nivel.get("nombre").getAsString(), mision.get("idMision").getAsString(), mision.get("descripcion").getAsString(),salida.get("idMisionPaciente").getAsString());
                            listaMisiones.add(mis);


                        }
                        adapter.notifyDataSetChanged();


                    }
                }}).execute(valores);




    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK ) {
            Intent a = new Intent(this,MenuPaciente.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    private void llenarNuevasMis() throws IOException, ClassNotFoundException {
        FileInputStream in2 = null;
        ArrayList<String> nombres= new ArrayList<>();
        ArrayList valores= new ArrayList();
        nombres.add("codPaciente");
        valores.add(idGuardado);
        new Conexion("consNuevasMisionesPaciente", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                if (!output.equals("null")) {
                    Gson gson = new Gson();
                    JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                    JsonObject mision;
                    JsonObject categoria;
                    JsonObject nivel;
                    int img =0;
                    for(int i =0; i<arreglo.size();i++ ){
                      mision=arreglo.get(i).getAsJsonObject();
                      categoria = mision.get("idCategoria").getAsJsonObject();
                      nivel = mision.get("idNivel").getAsJsonObject();
                      if(categoria.get("idCategoria").getAsInt()==2){
                          img=1;
                      }else{img =2;}
                      MisionVo mis = new MisionVo(mision.get("nombre").getAsString(), categoria.get("nombreCategoria").getAsString(), nivel.get("nombre").getAsString(), mision.get("idMision").getAsString(), mision.get("descripcion").getAsString(),img);
                      listaNuevasMisiones.add(mis);

                    }
                    adapterNuevasMis.notifyDataSetChanged();
                }
            }
        }).execute(valores);

    }

private void llenarBarraEjercicio(){
        ArrayList<String> nombres= new ArrayList<>();
        ArrayList valores= new ArrayList();
        nombres.add("codPaciente");
        valores.add(idGuardado);
        new Conexion("consultarNivelEjercicio", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson = new Gson();
                JsonObject nivel = gson.fromJson(output, JsonObject.class);


                ejercicio.setProgress(nivel.get("Porcentaje").getAsInt());
                nivEjercicio.setText( nivel.get("nombreNivel").getAsString());
                }
        }).execute(valores);


}

    private void llenarBarraAlimentacion(){
            ArrayList<String> nombres= new ArrayList<>();
            ArrayList valores= new ArrayList();
            nombres.add("codPaciente");
            valores.add(idGuardado);
            new Conexion("consultarNivelAlimentacion", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Gson gson = new Gson();
                    JsonObject nivel = gson.fromJson(output, JsonObject.class);


                    alimentacion.setProgress(nivel.get("Porcentaje").getAsInt());
                    nivAlim.setText( nivel.get("nombreNivel").getAsString());
                }
            }).execute(valores);

    }}
