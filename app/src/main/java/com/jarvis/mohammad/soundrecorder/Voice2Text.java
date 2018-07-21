package com.jarvis.mohammad.soundrecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Voice2Text extends AppCompatActivity {
    SpeechRecognizer speechRecognizer;
    ImageButton btnSpeak,mute;
    Intent mSpeechRecognizerIntent;
    EditText edtSpeaked;
    String speechText="";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice2_text);
      //  checkPermission();
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getAvailableLocales());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String > matches=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches !=null){
                    speechText+=matches.get(0)+" ";
                    edtSpeaked.setText(speechText);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        btnSpeak=findViewById(R.id.imageButtonMic);

        edtSpeaked=findViewById(R.id.editText_text);
        btnSpeak.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{edtSpeaked.setText("");
                        edtSpeaked.setHint("Listening");
                        speechRecognizer.startListening(mSpeechRecognizerIntent);
                    break;}
                    case MotionEvent.ACTION_UP:  {  speechRecognizer.stopListening();
                        edtSpeaked.setText("Press and hold the mic for listening");
                        Toast.makeText(Voice2Text.this,"stopped listening",Toast.LENGTH_SHORT).show();}
                    break;
                }
                return false;
            }
        });


    }

    public void clear(View view) {
        speechText="";
        edtSpeaked.setText(speechText);
    }

   /* private void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED){
                Intent intent= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri.parse("package:"+getApplicationContext());
                startActivity(intent);
                finish();
            }
        }
    }*/
}
