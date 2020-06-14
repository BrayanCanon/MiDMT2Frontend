package juan.example.com.diabetest2.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import juan.example.com.diabetest2.R;

public class AdapterPasoedit extends RecyclerView.Adapter<AdapterPasoedit.MyViewHolder> {

    private ArrayList<Movie> pasoList;
    public ArrayList<String> nombres;
    public ArrayList<Integer> numeritos;
    private Context este;

    public AdapterPasoedit(ArrayList<Movie> pasoList, ArrayList nombres, ArrayList<Integer> numeritos, Context este){
        this.pasoList=pasoList;
        this.nombres=nombres;
        this.numeritos=numeritos;
        this.este=este;
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{

        public TextView title, year, genre,pos;
        public Button butnot,down,edit;
        public CheckBox listeado1;
        public Spinner logros;
        public View estep;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            pos=(TextView) view.findViewById(R.id.year);
            listeado1=(CheckBox) view.findViewById(R.id.listeado1);
            edit=(Button)view.findViewById(R.id.button38);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new AdapterPasoedit.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie m=pasoList.get(position);
        holder.title.setText(m.getTitle());
        if(m.getLogro()!=-1 && m.getLogro()!=0){
            holder.genre.setText(m.getGenre());
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d("numeritosel",""+nombres.get(which));
                final int[] n = {1};
                AlertDialog.Builder b = new AlertDialog.Builder(este);
                Object[] objectList = nombres.toArray();
                final String[] types = Arrays.copyOf(objectList,nombres.size(),String[].class);
                b.setTitle("Seleccione un logro");
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        m.setLogro(numeritos.get(which));
                        holder.genre.setText(types[which]);
                        pasoList.set(position,m);
                        //-------------------------------------
                        Toast.makeText(este, "Logro asignado!", Toast.LENGTH_LONG).show();
                        //-------------------------------------
                        //Log.d("numeritosel",""+nombres.get(which));
                    }

                });
                b.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return pasoList.size();
    }

    public ArrayList<Movie> retsal(){return pasoList;}

}
