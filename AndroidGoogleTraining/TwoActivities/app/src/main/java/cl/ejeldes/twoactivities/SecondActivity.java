package cl.ejeldes.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "cl.ejeldes.twoactivities.extra.REPLY";
    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Cargo el EditText para pasarlo al intent
        mReply = findViewById(R.id.editText_second);

        // Obtengo el intent con metodo getIntent()
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Obtengo el textview
        TextView textView = findViewById(R.id.text_message);

        // Seteo el texto
        textView.setText(message);

        // texto blanco
        findViewById(R.id.editText_main);
    }

    public void returnReply(View view) {
        String reply = mReply.getText().toString();

        Intent intentReply = new Intent();
        intentReply.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, intentReply);
        finish();
    }
}
