package juan.example.com.diabetest2.profesional;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import juan.example.com.diabetest2.R;

public class Crearmisg2 extends AppCompatActivity {

    ListView listview ;
    SparseBooleanArray sparseBooleanArray ;
    String[] ListViewItems = new String[] {
            "ListView ITEM-1",
            "ListView ITEM-2",
            "ListView ITEM-3",
            "ListView ITEM-4",
            "ListView ITEM-5",
            "ListView ITEM-6",
            "ListView ITEM-7",
            "ListView ITEM-8",
            "ListView ITEM-9",
            "ListView ITEM-10",
            "ListView ITEM-4",
            "ListView ITEM-5",
            "ListView ITEM-6",
            "ListView ITEM-7",
            "ListView ITEM-8",
            "ListView ITEM-9",

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmisg2);

        listview = (ListView)findViewById(R.id.pasos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (Crearmisg2.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, ListViewItems );
        listview.setAdapter(adapter);

       }

    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void crear(View v) {

        sparseBooleanArray = listview.getCheckedItemPositions();

        String ValueHolder = "" ;

        int i = 0 ;

        while (i < sparseBooleanArray.size()) {
            if (sparseBooleanArray.valueAt(i)) {
                ValueHolder += ListViewItems [ sparseBooleanArray.keyAt(i) ] + ",";
            }
            i++ ;
        }
        ValueHolder = ValueHolder.replaceAll("(,)*$", "");
        Toast.makeText(Crearmisg2.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();
        Intent uno =new Intent(this,Crearmisg3.class);
        startActivity(uno);

    }

    }

