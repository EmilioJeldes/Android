package cl.ejeldes.custombroadcastsend;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Send Broadcast only inside the application
    LocalBroadcastManager broadcastManager;

    private static final String TAG = "MainActivity";
    public static final String CUSTOMBROADCASTRECEIVER_TAG = "cl.ejeldes.custombroadcastreceiver.tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendBroadcast = findViewById(R.id.btn_send_broadcast);

        broadcastManager = LocalBroadcastManager.getInstance(this);

        btnSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sending Broadcast");
                Intent intentBroadcast = new Intent(CUSTOMBROADCASTRECEIVER_TAG);

                // Send broadcast Globally
                sendBroadcast(intentBroadcast);

                // Send broadcast locally
                // broadcastManager.sendBroadcast(intentBroadcast);
                Log.d(TAG, "onClick: Broadcast Sent");
            }
        });
    }
}
