package juan.example.com.diabetest2.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Holder extends RecyclerView.ViewHolder{
    public Movie binding;

    public Holder(View itemView) {
        super(itemView);
      //  binding = Movie.bind(itemView);
    }
}