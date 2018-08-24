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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    ArrayList<MisionVo> listaMisiones;
    RecyclerView recylerMisiones;
    AdapterMision adapter;
    Context vista = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misiones);

        listaMisiones= new ArrayList<>();
        recylerMisiones=findViewById(R.id.recyclerMisiones);
        adapter = new AdapterMision(listaMisiones);
        recylerMisiones.setAdapter(adapter);
        recylerMisiones.setItemAnimator(new DefaultItemAnimator());
        recylerMisiones.setLayoutManager(new LinearLayoutManager(this));

        try {
            llenarMisiones();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void llenarMisiones() throws IOException {
        ArrayList vacio = new ArrayList<>();
        FileInputStream in2 = null;
        try {
            in2 = openFileInput("id.dt2");


        ObjectInputStream ois2 = new ObjectInputStream(in2);

            String idGuardado =  ois2.readObject().toString();
            ArrayList<String> nombres= new ArrayList<>();
            ArrayList valores= new ArrayList();
            nombres.add("codPaciente");

            valores.add(idGuardado);
            new Conexion("consultarMisPaciente", nombres, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Gson gson= new Gson();

                    JsonArray arreglo = gson.fromJson(output,JsonArray.class);
                    JsonObject salida;
                    JsonObject mision;
                    JsonObject categoria;
                    JsonObject nivel;
                    for(int a=0;a<arreglo.size();a++){
                        salida = arreglo.get(a).getAsJsonObject();
                        mision = salida.get("idMision").getAsJsonObject();
                        categoria = mision.get("idCategoria").getAsJsonObject();
                        nivel= mision.get("idNivel").getAsJsonObject();
                        MisionVo mis = new MisionVo(mision.get("nombre").getAsString(),categoria.get("nombreCategoria").getAsString(),nivel.get("idNivel").getAsString());
                        listaMisiones.add(mis);


                    }
                    adapter.notifyDataSetChanged();


                }
            }).execute(valores);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
