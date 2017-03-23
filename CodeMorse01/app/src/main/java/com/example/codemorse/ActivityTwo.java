package com.example.codemorse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityTwo extends AppCompatActivity {

    TextView result;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        result = (TextView) findViewById(R.id.txtVActTwo);
        txtView = (TextView) findViewById(R.id.txtVHead);
        String txtString = getIntent().getStringExtra("string");
        result.setText(txtString.toLowerCase());
    }
}
