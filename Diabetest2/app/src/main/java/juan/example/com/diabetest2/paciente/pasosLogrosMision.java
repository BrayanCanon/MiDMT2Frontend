package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pasosLogrosMision.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pasosLogrosMision#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pasosLogrosMision extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerPasos ;
    MisionVo mision;
    ArrayList<PasoVo> listaPasos;
    ArrayList<VerificacionVo> listaverif ;
    AdaptadorPasos adapter;
    TextView nombre,descripcion,dias;


    public pasosLogrosMision() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pasosLogrosMision.
     */
    // TODO: Rename and change types and number of parameters
    public static pasosLogrosMision newInstance(String param1, String param2) {
        pasosLogrosMision fragment = new pasosLogrosMision();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle envio = getArguments();
         mision  = (MisionVo) envio.getSerializable("mision");
         Boolean familiar=envio.containsKey("codApoyo");
        boolean habCheckBox=envio.getBoolean("habEmpezarMision");
        View vista =inflater.inflate(R.layout.fragment_pasos_logros_mision, container, false);
        listaPasos=new ArrayList<>();
        listaverif = new ArrayList<>();
        llenarListaVerificación(habCheckBox);

        adapter = new AdaptadorPasos(listaPasos,listaverif,this.getContext(),mision.getIdMision());
        recyclerPasos=vista.findViewById(R.id.recyclerPasos);
        recyclerPasos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPasos.setAdapter(adapter);
        recyclerPasos.setItemAnimator(new DefaultItemAnimator());
        nombre=vista.findViewById(R.id.nombre);
        descripcion=vista.findViewById(R.id.descripcion);
        dias=vista.findViewById(R.id.orden);

            llenarLista(habCheckBox);

        return vista;
    }

    private void mostrarDias() {
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList valores = new ArrayList();
        nombres.add("idMision");
        valores.add(mision.getIdMision());
        new Conexion("consultarTodosPasos", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson = new Gson();
                JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                JsonObject salida;
                JsonObject paso;

            }
        }).execute(valores);
    }
    private void llenarLista( final boolean habCheckBox) {
        ArrayList<String> nombres= new ArrayList<>();
        final ArrayList valores= new ArrayList();
        nombres.add("idMision");
        valores.add(mision.getIdMision());
        new Conexion("consultarTodosPasos", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                if (!output.equals("null")) {
                    Gson gson = new Gson();
                    JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                    JsonObject salida;
                    JsonObject paso;
                    JsonObject verif;


                    salida=arreglo.get(0).getAsJsonObject();
                    paso=salida.get("idPaso").getAsJsonObject();
                    String nom=paso.get("nombre").getAsString();
                    String desc =paso.get("descripcion").getAsString();
                    String dia =paso.get("diasDuracion").getAsString();
                    nombre.setText(nom);
                    descripcion.setText(desc);
                    dias.setText(dia);
                    if( habCheckBox==false) {
                        ArrayList<String> no = new ArrayList<>();
                        ArrayList va = new ArrayList();
                        no.add("idMisionPaciente");
                        va.add(mision.getIdMisionPaciente());


                        new Conexion("consultarVerificacionPorMP", no, new Conexion.Comunicado() {
                            @Override
                            public void salidas(String output) {
                                Gson gson = new Gson();
                                JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                                JsonObject verif;
                                for (int i = 0; i < arreglo.size(); i++) {
                                    verif = arreglo.get(i).getAsJsonObject();
                                    VerificacionVo obj = new VerificacionVo(verif.get("numeroDia").getAsInt(), verif.get("verifPaciente").getAsBoolean());
                                    listaverif.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }).execute(va);

                    }
                    for(int i=1;i<=arreglo.size();i++) {
                        PasoVo pasoVo = new PasoVo(nom, habCheckBox,i,mision.getIdMisionPaciente());

                        listaPasos.add(pasoVo);

                    }
                    //----------------------------
                    adapter.contarverifs();

                    adapter.notifyDataSetChanged();
                }
            }
        }).execute(valores);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void llenarListaVerificación(final boolean habCheckBox){
        if( habCheckBox==false) {
            ArrayList<String> no = new ArrayList<>();
            ArrayList va = new ArrayList();
            no.add("idMisionPaciente");
            va.add(mision.getIdMisionPaciente());


            new Conexion("consultarVerificacionPorMP", no, new Conexion.Comunicado() {
                @Override
                public void salidas(String output) {
                    Gson gson = new Gson();
                    JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                    JsonObject verif;
                    for (int i = 0; i < arreglo.size(); i++) {
                        verif = arreglo.get(i).getAsJsonObject();
                        VerificacionVo obj = new VerificacionVo(verif.get("numeroDia").getAsInt(), verif.get("verifPaciente").getAsBoolean());
                        listaverif.add(obj);
                    }
                    adapter.notifyDataSetChanged();
                }
            }).execute(va);

        }}
}
