package com.example.daryo.sdjpoajd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class autostart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent2) {
        Intent intent = new Intent(context,service.class);
        context.startService(intent);
        Log.i("Autostart", "started");
    }
}
