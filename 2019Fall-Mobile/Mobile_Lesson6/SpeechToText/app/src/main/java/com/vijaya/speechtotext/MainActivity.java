package com.vijaya.speechtotext;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.preference.ListPreference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Calendar;
import android.speech.tts.TextToSpeech;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private static final int REQ_TTS_STATUS_CHECK = 0;
    private static final String TAG = "TTS Testing";
    TextToSpeech tts;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    playMessage("Hello");
                }
            }
        });
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    String response = call(result.get(0));
                    if (response == "What is your name?") {

                    }
                    playMessage(response);
                }
                break;
            }

        }
    }
    public String call(String call){
        String response = "";
/*        if ( call.substring(0,10) == "my name is";
        {

        }*/
        switch (call) {
            case "hello":
                response = "What is your name?";
                break;
            case "I am not feeling good what should I do":
            case "I'm not feeling good what should I do":
                response = "I can understand. Please tell your symptoms in short.";
                break;
            case "thank you my medical assistant":
                response = "Thank you too " +  " Take care";
                break;
            case "what time is it":
                response = "The time is " + Calendar.getInstance().getTime();
                break;
            case "what medicines should i take":
            case "what medication should I take":
                response = "I think you have fever. Please take this medicine and eat a bunch of sugar.";
                break;
            default :
                response = "Sorry I'm not familiar with that";
        }
        return response;
    }
    @Override
    public void onPause()
    {
        super.onPause();
        // if we're losing focus, stop talking
        if( this.tts != null)
            this.tts.stop();
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.tts.shutdown();
    }
    private void playMessage(String message){
        this.tts.speak(message, TextToSpeech.QUEUE_ADD, null);
    }
}