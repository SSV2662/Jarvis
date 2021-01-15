package com.example.jarvis;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    EditText useri ;
    public void speak(){
        String tospeak = useri.getText().toString();

        mTTs.speak(tospeak,TextToSpeech.QUEUE_FLUSH,null);

    }


    TextToSpeech mTTs;

    public void speechinput(View view){
        Intent pmsi = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        pmsi.putExtra(RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,RecognizerIntent.EXTRA_LANGUAGE_MODEL);
        pmsi.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        if(pmsi.resolveActivity(getPackageManager())!= null){
            startActivityForResult(pmsi,10);
        }else {
            Toast.makeText(getApplicationContext(),"You are not supported",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 10:
                if (resultCode==RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    useri.setText(result.get(0));

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        useri = (EditText)findViewById(R.id.ui);
        Button listen =(Button)findViewById(R.id.button4);
        Button result = (Button)findViewById(R.id.button5);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTs.setLanguage(Locale.US);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(MainActivity2.this,"Language not supported",Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tospeak = useri.getText().toString();
                if(tospeak.equals("hello")){
                    mTTs.speak("Working",TextToSpeech.QUEUE_FLUSH,null);
                    Format dateformat =  new SimpleDateFormat("EEEE");
                    String day = dateformat.format(new Date());
                    if(day.equals("Wednesday")){
                        mTTs.speak("Its One of your Favourite Days Sir. Its Wednesday",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.equals("exit productive mode")){
                    mTTs.speak("Exiting productive mode. Hope you have finished your work",TextToSpeech.QUEUE_FLUSH,null);
                    Intent back = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(back);
                }else if(tospeak.equals("what's my schedule today")||tospeak.equals("what's on my schedule today")||tospeak.equals("what is my schedule today")){
                    Format dayformat = new SimpleDateFormat("EEEE");
                    String td = dayformat.format(new Date());
                    if(td.equals("Thursday")){
                        mTTs.speak("Today is Thursday and therefore as of your given schedule. You have school from 9 to 11:30. Then you should take a break. And then lunch at 1:15 pm. M M C class from 4:15 to 6:30, and then your homework",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.substring(0,7).equals("what is")){
                    int endi = tospeak.length();
                    String tos = tospeak.substring(8, endi);
                    Intent search = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=" + tos));
                    try {
                        startActivity(search);
                    } catch (ActivityNotFoundException ex) {
                        mTTs.speak("I am not able to get that info", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }else if(tospeak.substring(0,4).equals("show")){
                    int endi = tospeak.length();
                    String search = tospeak.substring(5,endi);
                    Intent searchh = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/place/"+search));
                    try {
                        startActivity(searchh);
                    }catch (ActivityNotFoundException fx){
                        mTTs.speak("I am not able to search that info",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }


            }
        });

    }
}