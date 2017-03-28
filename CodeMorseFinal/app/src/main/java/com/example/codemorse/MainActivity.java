package com.example.codemorse;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static  final String STRING = "STRING";
    //private static  final String FLAG = "FLAG";

    Button btnStart;
    EditText editText;
    Switch mySwitch;
    CheckBox checkBoxSignal;

    private Camera camera;
    private boolean isFlashOn;
    Camera.Parameters params;
    private boolean hasFlash;

    public void errorDialog(){
        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Ошибка");
        alert.setMessage("У Вашего устройства нет вспышки!");
        alert.show();
    }

    public void getCamera(){
        if(camera == null){
            try{
                camera = Camera.open();
                params = camera.getParameters();
            }catch (RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    private void turnOn(){
        if(!hasFlash){
            if(camera == null || params == null){
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    private void turnOff(){
        if(hasFlash){
            if(camera == null || params == null){
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextActMain);
        btnStart = (Button) findViewById(R.id.btnActMain);
        checkBoxSignal = (CheckBox) findViewById(R.id.checkBox);

        mySwitch = (Switch) findViewById(R.id.newSwitch);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    btnStart.setEnabled(true);
                } else if (!hasFlash) {
                    errorDialog();
                    btnStart.setEnabled(false);
                }
            }
        });

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        getCamera();
    }

    public void goTwoActivity(View view) {
        if (inputCheck()) { //checkBoxSignal.isChecked()
            Intent intent = new Intent(this, ActivityTwo.class);
            intent.putExtra(STRING, editText.getText().toString());
            //intent.putExtra(FLAG, true);

            startActivity(intent);
        }
    }

    public boolean validation(String nameEditText) {
        Pattern pattern = Pattern.compile("[а-яА-Я0-9 ]+$");
        Matcher matcher = pattern.matcher(nameEditText);
        return matcher.matches();
    }

    public boolean inputCheck() {
        if (!validation(editText.getText().toString()) || editText.getText().toString().equals("")) {
            editText.setError("Неккоректные данные!");
            return false;
        }
       return true;
    }
}
