package juan.example.com.diabetest2.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import juan.example.com.diabetest2.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private ArrayList<Movie> moviesList;
    public Context este;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,pos;
        public Button butnot,down,edit;
        public CheckBox listeado1;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            pos=(TextView) view.findViewById(R.id.year);
            butnot = (Button) view.findViewById(R.id.butnot);
            listeado1=(CheckBox) view.findViewById(R.id.listeado1);
            down=(Button) view.findViewById(R.id.button40);
            edit=(Button)view.findViewById(R.id.button38);
            este=view.getContext();
        }
    }


    public MoviesAdapter(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Movie movie = moviesList.get(position);

        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText("");
        holder.listeado1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkeado =holder.listeado1.isChecked();
                movie.setSel(checkeado);
                if (!checkeado){
                    movie.setYear("0");
                    holder.year.setText("");
                    moviesList.set(position,movie);

                }
                else {
                    movie.setYear("1");
                    holder.year.setText("1");
                    moviesList.set(position,movie);

                }



            }
        });

        holder.butnot.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(holder.listeado1.isChecked() && Integer.parseInt(movie.getYear())!=-1) {
                    /*Toast toast1 =
                            Toast.makeText(este,
                                    "Toast por defecto " + position, Toast.LENGTH_SHORT);
                                     toast1.show();*/
                    movie.setYear(String.valueOf(Integer.parseInt(movie.getYear()) + 1));
                    holder.year.setText(String.valueOf(movie.getYear()));
                    moviesList.set(position, movie);

                }
                else if(holder.listeado1.isChecked() && Integer.parseInt(movie.getYear())==-1){
                    movie.setYear("1");
                    holder.year.setText("1");
                    moviesList.set(position, movie);
                }
            }
        });

        holder.down.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(holder.listeado1.isChecked() && Integer.parseInt(movie.getYear())>1 ) {

                    movie.setYear(String.valueOf(Integer.parseInt(movie.getYear()) - 1));
                    holder.year.setText(String.valueOf(movie.getYear()));
                    moviesList.set(position, movie);

                }
                else if(holder.listeado1.isChecked()) {
                    movie.setYear(String.valueOf(-1));
                    holder.year.setText("sin orden");
                    moviesList.set(position, movie);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public ArrayList<Movie> res(){ return  moviesList; }
}