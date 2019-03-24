package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private ImageButton call;
    private ImageButton hangup;
    private ImageButton backspace;
    private Button genericButton;

    private GenericButtonListener genericButtonListener = new GenericButtonListener();
    private class GenericButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            editTextPhone.setText(editTextPhone.getText().toString() + ((Button)view).getText().toString());
        }
    }

    private HangupListener hangupListener = new HangupListener();
    private class HangupListener implements View.OnClickListener  {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private CallListener classListener = new CallListener();
    private class CallListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + editTextPhone.getText().toString()));
                startActivity(intent);
            }
        }
    }
    private BackSpaceListener backSpaceListener = new BackSpaceListener();
    private class BackSpaceListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String text = editTextPhone.getText().toString();
            if(text != null) {
                editTextPhone.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        editTextPhone = (EditText)findViewById(R.id.phone);
        call = (ImageButton)findViewById(R.id.call);
        call.setOnClickListener(classListener);
        hangup = (ImageButton)findViewById(R.id.hangup);
        hangup.setOnClickListener(hangupListener);
        backspace = (ImageButton)findViewById(R.id.back);
        backspace.setOnClickListener(backSpaceListener);
        for (int index = 0 ; index < Constants.buttonIds.length; index++) {
            genericButton = (Button)findViewById(Constants.buttonIds[index]);
            genericButton.setOnClickListener(genericButtonListener);
        }

    }


}
