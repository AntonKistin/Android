package com.example.codemorse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{

    private static final String STRING = "STRING";
    private static  final String FLAG = "FLAG";

    private Button btnStart;
    private EditText editText;
    private Switch mySwitch;
    private CheckBox checkBoxSignal;
    String inputText = "";

    private Camera camera;
    Camera.Parameters params;
    private boolean hasFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextActMain);
        btnStart = (Button) findViewById(R.id.btnActMain);
        checkBoxSignal = (CheckBox) findViewById(R.id.checkBox);

        mySwitch = (Switch) findViewById(R.id.newSwitch);
        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(this);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!hasFlash){
            errorDialog();
        }else{
            camera = Camera.open();
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            Toast toast = Toast.makeText(MainActivity.this, "Вывод на дисплей!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            btnStart.setEnabled(true);
        } else if (!hasFlash) {
            errorDialog();
            btnStart.setEnabled(false);
        } else {
            Toast toast = Toast.makeText(MainActivity.this, "Вывод на вспышку!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            btnStart.setEnabled(true);
        }
    }

    public void errorDialog(){
        AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
        alert.setTitle("Ошибка");
        alert.setMessage("У Вашего устройства нет вспышки!");
        alert.show();
    }

    private void turnOn(){
        if(camera != null) {
            params = camera.getParameters();
            if (params != null) {
                params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
            }
        }
    }

    private void turnOff(){
        if(camera != null){
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
        }
    }

    public void onClick(View view) {
        inputText = editText.getText().toString();
        if (!validation(inputText)) {
            editText.setError("Неккоректные данные!");
        }else{
            Intent intent = new Intent(this, ActivityTwo.class);
            intent.putExtra(STRING, editText.getText().toString());
            intent.putExtra(FLAG,checkBoxSignal.isChecked());
            startActivity(intent);
        }
    }

    public boolean validation(String editText) {
        Pattern pattern = Pattern.compile("^[а-яА-Я0-9]+");
        Matcher matcher = pattern.matcher(editText);
        return matcher.matches();
    }
}
