package com.example.sipo.mulmed;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Locale;


public class Speech extends ActionBarActivity implements TextToSpeech.OnInitListener{
    ImageButton btnsback;
    Button btnspeech;
    EditText txtspeech;
    TextToSpeech TTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        getSupportActionBar().hide();

        TTS = new TextToSpeech(Speech.this,Speech.this);
        btnsback = (ImageButton)findViewById(R.id.btnSback);
        btnspeech = (Button)findViewById(R.id.btnSpeech);
        txtspeech = (EditText)findViewById(R.id.txtSpeech);

        btnsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Speech.this.finish();
                //Intent intent = new Intent(getBaseContext(),Menumenu.class);
                //startActivity(intent);
            }
        });

        btnspeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TTS.speak(txtspeech.getText().toString(),TextToSpeech.QUEUE_ADD,null);
            }
        });

    }

    @Override
    public void onInit(int status) {
        TTS.setLanguage(Locale.ENGLISH);
    }
}
