package juan.example.com.diabetest2.paciente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.util.Conexion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BasicInfoMision.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BasicInfoMision#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicInfoMision extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View vista;
    TextView descripcion, categoria, dificultad,titulo;
    Button empezarMision;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BasicInfoMision() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicInfoMision.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicInfoMision newInstance(String param1, String param2) {
        BasicInfoMision fragment = new BasicInfoMision();
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
        final MisionVo mision  = (MisionVo) envio.getSerializable("mision");
        final String idPaciente = envio.getString("codPaciente");
        boolean habEmpezarMision=envio.getBoolean("habEmpezarMision");
        vista=inflater.inflate(R.layout.fragment_basic_info_mision, container, false);
        titulo=vista.findViewById(R.id.titulo);
        descripcion=vista.findViewById(R.id.descripcionMision);
        categoria = vista.findViewById(R.id.catMision);
        dificultad=vista.findViewById(R.id.difMision);
        empezarMision=vista.findViewById(R.id.empMision);
        descripcion.setText(mision.getDescripcion());
        titulo.setText(mision.getTitulo());
        categoria.setText(mision.getCategoria());
        dificultad.setText(mision.getDificultad());
        if(habEmpezarMision==false){
            empezarMision.setVisibility(View.INVISIBLE);
        }
        empezarMision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asignarMision(mision.getIdMision(),idPaciente);
            }
        });

        return  vista;
    }

    private void asignarMision(String idmision,String codPaciente) {
        ArrayList<String> nombres= new ArrayList<>();
        ArrayList<String> valores= new ArrayList<>();
        nombres.add("idMision");
        nombres.add("codPaciente");
        valores.add(idmision);
        valores.add(codPaciente);
        new Conexion("asignarMisionPaciente", nombres, new Conexion.Comunicado() {
            @Override
            public void salidas(String output) {
          int duration = Toast.LENGTH_SHORT;
                String text;
                Context context = getContext();
                if (output==null){
                    text  = "No se ha iniciado la mision intenta mas tarde";
                }else{
                   text=output;
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
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
