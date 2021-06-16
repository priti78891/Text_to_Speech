package com.e.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class ResultMainActivity extends AppCompatActivity {
    TextToSpeech TTS;

    EditText textForSpeechField;
    SeekBar pitchSeekBar,speedSeekBar;
    Button speakBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_main);

        Intent intent = getIntent();

        initialize();

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });



    }

    private void initialize() {
        textForSpeechField=findViewById(R.id.textForSpeech);
        pitchSeekBar=findViewById(R.id.pitchSeekBar);
        speedSeekBar=findViewById(R.id.speedSeekBar);
        speakBtn = findViewById(R.id.speakBtn);

        TTS=new TextToSpeech(ResultMainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status == TextToSpeech.SUCCESS){

                    int checkLanguageData= TTS.setLanguage(Locale.ENGLISH);

                    if(checkLanguageData==TextToSpeech.LANG_MISSING_DATA
                            || checkLanguageData== TextToSpeech.LANG_NOT_SUPPORTED){

                        Log.e("TextToSpeech","Language Not Supported");

                    }else{
                        speakBtn.setEnabled(true);
                    }

                }else{

                    Log.e("TextToSpeech","Language Initialization Failed");
                }


            }
        });
    }

    private void speak()
    {
        String textForSpeech = textForSpeechField.getText().toString().trim();
        float voicePitch= (float)pitchSeekBar.getProgress();
        float voiceSpeed=(float) speedSeekBar.getProgress();
        try {
            voicePitch=voicePitch/50;
            voiceSpeed=voiceSpeed/50;
        }catch(Exception e){
            voicePitch=0;
            voiceSpeed=0;
        }
        if(voicePitch<0.1){ voicePitch=0.1f;}
        if(voiceSpeed<0.1){voiceSpeed=0.1f;}

        TTS.setPitch(voicePitch);
        TTS.setSpeechRate(voiceSpeed);

        TTS.speak(textForSpeech, TextToSpeech.QUEUE_FLUSH,null);
    }

}