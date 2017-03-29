package com.example.codemorse;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTwo extends AppCompatActivity {

    private TextView result;
    private TextView txtView;
    private int MyMelody = 1;
    private int mPlay = 1;
    private SoundPool soundSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        result = (TextView) findViewById(R.id.txtVActTwo);
        txtView = (TextView) findViewById(R.id.txtVHead);
        String txtString = getIntent().getStringExtra("STRING");
        result.setText(txtString.toLowerCase());

        soundSignal = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundSignal.load(this, R.raw.sound, 1);

        if(getIntent().getExtras().getBoolean("FLAG")){
            Toast toast = Toast.makeText(ActivityTwo.this, "CheckBox нажат!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            mPlay = soundSignal.play(MyMelody, 1, 1, 1, 0, 1);
        }
    }
}
