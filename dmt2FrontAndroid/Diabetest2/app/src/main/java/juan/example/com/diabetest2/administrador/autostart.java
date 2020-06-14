package juan.example.com.diabetest2.administrador;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class autostart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context,ServicioDT2.class);
        context.startService(intent2);
        Log.i("Autostart", "started");
    }
}
