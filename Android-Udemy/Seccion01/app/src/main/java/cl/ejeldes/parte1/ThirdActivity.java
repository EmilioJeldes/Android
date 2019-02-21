package cl.ejeldes.parte1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imageBtnPhone;
    private ImageButton imageBtnWeb;
    private ImageButton imageBtnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = ((EditText) findViewById(R.id.editTextPhone));
        editTextWeb = ((EditText) findViewById(R.id.editTextWeb));
        imageBtnPhone = ((ImageButton) findViewById(R.id.imageButtonPhone));
        imageBtnCamera = ((ImageButton) findViewById(R.id.imageButtonCamera));
        imageBtnWeb = ((ImageButton) findViewById(R.id.imageButtonWeb));

        // ImageBtnPhone Listener
        imageBtnPhoneOnClickListener();
    }

    private void imageBtnPhoneOnClickListener() {
        imageBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhone.getText().toString();
                if (!phoneNumber.isEmpty()) {
                    Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                    startActivity(intentCall);
                }
            }
        });
    }

    public boolean checkPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
