package juan.example.com.diabetest2.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import juan.example.com.diabetest2.R;

public class AdapterPasoedit extends RecyclerView.Adapter<AdapterPasoedit.MyViewHolder> {

    private ArrayList<Movie> pasoList;

    public AdapterPasoedit(ArrayList<Movie> pasoList){
        this.pasoList=pasoList;
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
            butnot = (Button) view.findViewById(R.id.butnot);
            listeado1=(CheckBox) view.findViewById(R.id.listeado1);
            down=(Button) view.findViewById(R.id.button40);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie m=pasoList.get(position);
        holder.title.setText(m.getTitle());


    }

    @Override
    public int getItemCount() {
        return pasoList.size();
    }

}
