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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Button btnStart;
    EditText editText;
    Switch mySwitch;

    private int Melody = 1;
    private int Play;
    private SoundPool soundPool;

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
        mySwitch = (Switch) findViewById(R.id.newSwitch);
        mySwitch.setOnCheckedChangeListener(this);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundPool.load(this, R.raw.sound, 1);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        getCamera();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.newSwitch:
                if (!isChecked) {
                    Play = soundPool.play(Melody,1,1,1,0,1);
                    Toast toast = Toast.makeText(MainActivity.this, "Вывод на дисплей", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    btnStart.setEnabled(true);
                } else if(!hasFlash) {
                    Play = soundPool.play(Melody,1,1,1,0,1);
                    /*Toast toast = Toast.makeText(MainActivity.this, "LED", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                    errorDialog();
                    btnStart.setEnabled(false);
                }
        }
    }

    public void goTwoActivity(View v) {
        switch (v.getId()) {
            case R.id.btnActMain:
                if (editText.getText().toString().equals("")) {
                    editText.setError("Поле пустое!");
                } else if (inputCheck()) {
                    Intent intent = new Intent(this, ActivityTwo.class);
                    intent.putExtra("string", editText.getText().toString());

                    startActivity(intent);
                }
        }
    }

    public boolean validation(String nameEditText) {
        Pattern pattern = Pattern.compile("[а-яА-Я0-9]+$");
        Matcher matcher = pattern.matcher(nameEditText);
        return matcher.matches();
    }

    boolean inputCheck() {
        if (!validation(editText.getText().toString())) {
            editText.setError("Неккоректный ввод!");
            return false;
        }
        return true;
    }
}
