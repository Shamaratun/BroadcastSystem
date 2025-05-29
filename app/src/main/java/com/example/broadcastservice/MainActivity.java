package com.example.broadcastservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.broadcastservice.service.MyBackgroundService;

public class MainActivity extends AppCompatActivity {
private  static  final String TAG= "MainActivity";
public  static final String CUSTOM_BROADCAST_ACTION ="com.example.bookcrudapi.CUSTOM_ACTION";
private BroadcastReceiver myReciver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button startServiceButton = findViewById(R.id.startServiceButton);
Button stopServiceButton=findViewById(R.id.stopServiceButton);
    Button sendBroadcastButton=findViewById(R.id.sendBroadcastButton);
    myReciver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (CUSTOM_BROADCAST_ACTION.equals(intent.getAction())) {
                String message = intent.getStringExtra("message");

                Toast.makeText(context, " Broadcast Received: " + message, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Custom Broadcast Received: " + message);
            }
        }
    };
        LocalBroadcastManager.getInstance(this).registerReceiver(myReciver,new IntentFilter(CUSTOM_BROADCAST_ACTION));
 startServiceButton.setOnClickListener(v->{
     Intent serviceIntent = new Intent(MainActivity.this, MyBackgroundService.class);
     startService(serviceIntent);
     Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_SHORT).show();
 Log.d(TAG,"Attempting to start MyBackgroundService");

 });
    stopServiceButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent serviceIntent =new Intent(MainActivity.this, MyBackgroundService.class);
            stopService(serviceIntent);
            Toast.makeText(MainActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Attempting to stop MyBackgroundService");

        }
    });
    sendBroadcastButton.setOnClickListener(v -> {
        Intent broadcastIntent =new Intent(CUSTOM_BROADCAST_ACTION);
        broadcastIntent.putExtra("message","Hello From Main Activity!");

        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(broadcastIntent);
        Toast.makeText(MainActivity.this, "Custom Broadcast send", Toast.LENGTH_SHORT).show();
    Log.d(TAG,"Custom Broadcast sent from Main Activity");

    });

}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReciver);
        Log.d(TAG, "Main Activity Destroyed , Broadcast unregistered.");

    }
}