package com.example.broadcastservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyBackgroundService extends Service {
    private static final String TAG = "MyBackgroundService";
    private Handler handler;
    private Runnable runnable;
    private int counter = 0;

    public MyBackgroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service Created");
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                counter++;
                Log.d(TAG, "Service Running...Counter: " + counter);
                Toast.makeText(MyBackgroundService.this, "Service Running: " + counter, Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 5000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servive Started command ");
        Toast.makeText(this, "Servive Started command", Toast.LENGTH_SHORT).show();
        handler.post(runnable);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service Destroyed");
        Toast.makeText(this, "Servive Destroyed", Toast.LENGTH_SHORT).show();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }
}