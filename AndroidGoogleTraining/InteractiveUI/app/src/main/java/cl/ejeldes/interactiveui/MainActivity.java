package cl.ejeldes.interactiveui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int counter;
    private TextView tvCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCounter = ((TextView) findViewById(R.id.show_count));
    }

    public void showToast(View v) {
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT).show();
    }

    public void incrementCounter(View v) {
        ++counter;
        if (tvCounter != null) {
            tvCounter.setText(String.valueOf(counter));
        }
    }
}
