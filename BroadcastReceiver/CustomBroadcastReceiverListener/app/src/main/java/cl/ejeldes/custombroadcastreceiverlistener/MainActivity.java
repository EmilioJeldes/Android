package cl.ejeldes.custombroadcastreceiverlistener;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String CUSTOMBROADCASTRECEIVER_TAG = "cl.ejeldes.custombroadcastreceiver.tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonSendBroadcastIntent = findViewById(R.id.btn_send_broadcast_intent);

        // Instantiate Broadcast Receiver
        BroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();

        // Create intent filter
        IntentFilter intentFilter = new IntentFilter(CUSTOMBROADCASTRECEIVER_TAG);

        // Register Broadcast Receiver passing the filter
        registerReceiver(broadcastReceiver, intentFilter);

        // Set Button listener to send broadcast
        buttonSendBroadcastIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sendBroadcast: Clicking sendBroadcast()");

                // Create the intent with the same action and category from AndroidManifest file
                Intent broadcastIntent = new Intent();

                // Sets the action for the intent
                broadcastIntent.setAction(CUSTOMBROADCASTRECEIVER_TAG);

                // Send Broadcast with intent and it will be triggered with MyBroadcastReceiver.class listener
                sendBroadcast(broadcastIntent);
            }
        });
    }
}