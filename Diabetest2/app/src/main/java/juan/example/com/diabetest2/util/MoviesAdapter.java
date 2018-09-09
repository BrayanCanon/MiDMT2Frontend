package juan.example.com.diabetest2.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import juan.example.com.diabetest2.R;
import juan.example.com.diabetest2.profesional.BuscLogro;
import juan.example.com.diabetest2.profesional.Modpasmisg1;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Movie> moviesList;
    public Context este;
    public ArrayList nombres;
    public ArrayList<Integer> numeritos;
    public Context con;



    public class MyViewHolder extends RecyclerView.ViewHolder {
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
            edit=(Button)view.findViewById(R.id.button38);
            este=view.getContext();
            estep=view;


        }
    }


    public MoviesAdapter(ArrayList<Movie> moviesList,ArrayList<String> nombres,ArrayList<Integer> numeritos,Context con) {
        this.moviesList = moviesList;
        this.nombres=nombres;
        this.numeritos=numeritos;
        this.con=con;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);


       con=itemView.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText("");
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent edicion=new Intent(este,Modpasmisg1.class);
                edicion.putExtra("nombre",movie.getTitle());
                edicion.putExtra("dias",String.valueOf(movie.getDias()));
                Log.d("descripcion",movie.getGenre());
                edicion.putExtra("descripcion",movie.getGenre());
                edicion.putExtra("categoria",String.valueOf(movie.getCategoria()));
                este.startActivity(edicion);
                */
                final int[] n = {1};
                AlertDialog.Builder b = new AlertDialog.Builder(este);
                Object[] objectList = nombres.toArray();
                String[] types = Arrays.copyOf(objectList,nombres.size(),String[].class);
                b.setTitle("Seleccione un logro");
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        movie.setLogro(numeritos.get(which));
                        moviesList.set(position,movie);
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

    public void stoppid(int[] n){
        Log.d("fin","sip"+n[0]);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public ArrayList<Movie> res(){ return  moviesList; }
}