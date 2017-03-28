package com.example.codemorse;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    TextView result;
    TextView txtView;

    int MyMelody;
    int mPlay;
    SoundPool soundSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        soundSignal = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundSignal.load(this, R.raw.sound, 1);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        result = (TextView) findViewById(R.id.txtVActTwo);
        txtView = (TextView) findViewById(R.id.txtVHead);
        String txtString = getIntent().getStringExtra("STRING");
        result.setText(txtString.toLowerCase());

        /*if(getIntent().getExtras().getBoolean("FLAG")){
            Toast toast = Toast.makeText(ActivityTwo.this, "Пришло!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            mPlay = soundSignal.play(MyMelody, 1, 1, 1, 0, 1);
        }*/
    }
}
