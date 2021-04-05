package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private Button keyButton;
    private ImageButton callImageButton;
    private ImageButton hangUpImageButton;
    private ImageButton backspaceImageButton;

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            phoneNumberEditText.setText(phoneNumberEditText.getText() + ((Button) v).getText().toString());
        }
    }

    private class BackspaceButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String crtNumber = phoneNumberEditText.getText().toString();
            if (crtNumber.length() > 0) {
                String newNumber = crtNumber.substring(0, crtNumber.length() - 1);
                phoneNumberEditText.setText(newNumber);
            }
        }
    }

    private class CallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private class HangUpButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private ButtonListener buttonListener = new ButtonListener();
    private BackspaceButtonListener backspaceButtonListener = new BackspaceButtonListener();
    private CallButtonListener callButtonListener = new CallButtonListener();
    private HangUpButtonListener hangUpButtonListener = new HangUpButtonListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberEditText = findViewById(R.id.number_text);
        callImageButton = findViewById(R.id.call);
        hangUpImageButton = findViewById(R.id.hangup);
        backspaceImageButton = findViewById(R.id.backspace);

        callImageButton.setOnClickListener(callButtonListener);
        hangUpImageButton.setOnClickListener(hangUpButtonListener);
        backspaceImageButton.setOnClickListener(backspaceButtonListener);

        for (int buttonIdx = 0; buttonIdx < Constants.buttonIds.length; buttonIdx++) {
            keyButton = findViewById(Constants.buttonIds[buttonIdx]);
            keyButton.setOnClickListener(buttonListener);
        }
    }
}