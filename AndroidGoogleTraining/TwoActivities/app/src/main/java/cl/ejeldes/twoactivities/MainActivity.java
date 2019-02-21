package cl.ejeldes.twoactivities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "cl.ejeldes.twoactivities.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    private EditText mEdditeEditText;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdditeEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_reply_header);
        mReplyTextView = findViewById(R.id.text_message_reply);
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked");

        Intent intent = new Intent(this, SecondActivity.class);
        String message = mEdditeEditText.getText().toString();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEdditeEditText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Chequeo si el result code es el mismo al enviado
        if (requestCode == TEXT_REQUEST) {
            // Chequeo si el result es OK
            if (resultCode == RESULT_OK) {
                // Chequeo si el intent no es nulo
                if (data != null) {

                    // Obtengo el string del intent reply
                    String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);

                    // Seteo visibilidad
                    mReplyTextView.setVisibility(View.VISIBLE);
                    mReplyHeadTextView.setVisibility(View.VISIBLE);

                    // Paso el texto
                    mReplyTextView.setText(reply);

                }
            }
        }
    }
}
