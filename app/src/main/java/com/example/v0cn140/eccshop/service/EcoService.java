package com.example.v0cn140.eccshop.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.v0cn140.eccshop.utility.log.LogManager;

public class EcoService extends Service {

    private static String TAG = "Eco Service holder";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogManager.getLogger().d(TAG, "FirstService started");
        Toast.makeText(this, "Number of elements", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Number of elements", Toast.LENGTH_SHORT).show();
        LogManager.getLogger().d(TAG, "FirstService destroyed");
    }

}
