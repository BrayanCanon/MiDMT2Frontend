package com.example.daryo.miservicio;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

public class MyService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful


        return Service.START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
