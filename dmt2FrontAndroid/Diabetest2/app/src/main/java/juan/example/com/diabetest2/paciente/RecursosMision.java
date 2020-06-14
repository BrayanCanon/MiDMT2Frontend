package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.comunes.VerRecurso;
import juan.example.com.diabetest2.util.Conexion;
import juan.example.com.diabetest2.util.RecursoVo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecursosMision.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecursosMision#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecursosMision extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerRecursos;
    ArrayList<RecursoVo> listaRecursos;
    AdaptadorRecursos adapter;

    public RecursosMision() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecursosMision.
     */
    // TODO: Rename and change types and number of parameters
    public static RecursosMision newInstance(String param1, String param2) {
        RecursosMision fragment = new RecursosMision();
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
        Bundle envio = getArguments();
        MisionVo mision= (MisionVo) envio.getSerializable("mision");
        View vista =inflater.inflate(R.layout.fragment_recursos_mision, container, false);
        listaRecursos= new ArrayList<>();
        adapter= new AdaptadorRecursos(listaRecursos,vista.getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento;
                intento = new Intent(getContext(),VerRecursosMisionPaciente.class);

                Bundle envio = new Bundle();
                RecursoVo recurso = listaRecursos.get(recyclerRecursos.getChildAdapterPosition(view));
                envio.putSerializable("recurso",recurso);


                intento.putExtras(envio);
                startActivity(intento);


            }
        });

        recyclerRecursos=vista.findViewById(R.id.recRecPac);
        recyclerRecursos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRecursos.setAdapter(adapter);
        recyclerRecursos.setItemAnimator(new DefaultItemAnimator());
        llenarLista(mision);


        // Inflate the layout for this fragment
        return vista ;
    }

    private void llenarLista(MisionVo mision) {
        ArrayList<String> nombres= new ArrayList<>();
        ArrayList valores= new ArrayList();
        nombres.add("idMision");
        valores.add(mision.getIdMision());
        new Conexion("consultarRecursoMision", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
                Gson gson = new Gson();
                JsonArray arreglo = gson.fromJson(output, JsonArray.class);
                JsonObject salida,usuario;
                for(int i = 0; i<arreglo.size();i++){
                    salida=arreglo.get(i).getAsJsonObject();
                    usuario=salida.get("nomUsuario").getAsJsonObject();
                    RecursoVo rec = new RecursoVo(salida.get("tituloRec").getAsString(),usuario.get("nomUsuario").getAsString(),salida.get("contenidoApoyo").getAsString(),salida.get("imagen").getAsString(),salida.get("fecha").getAsString(),salida.get("video").getAsString());
                    listaRecursos.add(rec);

                }
                adapter.notifyDataSetChanged();
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
}
