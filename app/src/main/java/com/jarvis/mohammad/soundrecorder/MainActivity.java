package com.jarvis.mohammad.soundrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void textToSpeach(View view) {
        Intent  intent= new Intent(this,Text2Voice.class);
        startActivity(intent);
    }

    public void voiceToText(View view) {
        Intent intent= new Intent(this,Voice2Text.class);
        startActivity(intent);
    }

}
