package com.example.codemorse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    EditText editText;
    private boolean hasFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextActMain);
        btnStart = (Button) findViewById(R.id.btnActMain);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle("Ошибка");
            alert.setMessage("У Вашего устройства нет вспышки!");
            alert.show();
            //return;
        }
    }

    public void goTwoActivity(View v){
        switch (v.getId()){
            case R.id.btnActMain:
                if(inputCheck()){
                    Intent intent = new Intent(this, ActivityTwo.class);
                    intent.putExtra("string", editText.getText().toString());

                    startActivity(intent);
                }
        }
    }

    public boolean validation(String nameEditText) {
        Pattern pattern = Pattern.compile("[а-яА-Я]+$");
        Matcher matcher = pattern.matcher(nameEditText);
        return matcher.matches();
    }

    boolean inputCheck() {
        if(validation(editText.getText().toString()) == false) {
            editText.setError("Поле пустое или неккоректный ввод!");
            return false;
        }
        return true;
    }
}
