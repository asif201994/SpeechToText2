package com.jarvis.mohammad.soundrecorder;

import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class Text2Voice extends AppCompatActivity {

    private static final String TAG2 ="MYFUK" ;
    Bundle bundle;
    Button btnSpeak;
    EditText edtSpeak;
    TextToSpeech textToSpeech;
    final String TAG="complete";
    File dir= Environment.getRootDirectory().getAbsoluteFile();
    private String envPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/Download";
    private Uri fileUri;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2_voice);


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == textToSpeech.LANG_MISSING_DATA || result == textToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(Text2Voice.this, "This language is not supported", Toast.LENGTH_SHORT).show();

                    } else {
                        btnSpeak.setEnabled(true);
                        textToSpeech.setPitch(0.5f);
                        textToSpeech.setSpeechRate(0.9f);
                        speak();
                    }
                }
            }
        });


        edtSpeak = findViewById(R.id.editText2);
        btnSpeak = findViewById(R.id.btn_speak);
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        String text = edtSpeak.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            textToSpeech.speak(text, textToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, textToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }



       public void save(View view){

               String inputText = edtSpeak.getText().toString();
               String destFileName = envPath + "/" + "tts_file.wav";

               int sr = textToSpeech.synthesizeToFile(inputText, bundle, new File(destFileName),TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);

               Log.d(TAG2, "synthesize returns = " + sr);
               File fileTTS = new File(destFileName);

               if (fileTTS.exists()) {
                   Log.d(TAG, "successfully created fileTTS");
               }
               else {
                   Log.d(TAG, "failed while creating fileTTS");
               }

               fileUri = Uri.fromFile(fileTTS);
               Log.d(TAG, "successfully created uri link: " + fileUri.getPath());
           }
       }
