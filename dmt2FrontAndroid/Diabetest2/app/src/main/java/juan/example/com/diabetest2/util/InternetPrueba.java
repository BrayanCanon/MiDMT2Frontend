package juan.example.com.diabetest2.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetPrueba {
    Context local;
    public InternetPrueba(Context i){
        local=i;
    }


    public boolean probarInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) local.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
