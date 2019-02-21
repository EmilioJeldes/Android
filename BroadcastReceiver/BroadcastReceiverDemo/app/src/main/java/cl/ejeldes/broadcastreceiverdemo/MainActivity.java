package cl.ejeldes.broadcastreceiverdemo;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INITIALIZE BROADCAST RECEIVER
        myBroadcastReceiver = new MyBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // CREATE AN INTENT FILTER TO LISTEN SMS_RECEIVED ACTIONS
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        // REGISTER RECEIVER
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // UNREGISTER ON ONSTOP
        unregisterReceiver(myBroadcastReceiver);
    }
}
