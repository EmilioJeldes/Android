package cl.ejeldes.custombroadcastreceiverlistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Executing onReceive");
        Toast.makeText(context, "Broadcast Receiver Triggered", Toast.LENGTH_SHORT).show();
    }
}
