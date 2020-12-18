package com.example.jarvis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.provider.AlarmClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    EditText txt;
    TextToSpeech answer;
    Button resultbyJarvis;


    EditText Hour;
    EditText Minutes;




    // Speech Input
    public void getSpeechInput(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);


        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent, 10);

        }else {
            Toast.makeText(this, "You are not Supported",Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode){
            case 10:
                if(resultCode== RESULT_OK && data!=null){
                    ArrayList<String>  result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt.setText(result.get(0));

                }
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt= findViewById(R.id.EditText);
        resultbyJarvis = findViewById(R.id.button2);
        final EditText cn = (EditText)findViewById(R.id.contactnumber);


        // Speech Output
        answer = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    answer.setLanguage(Locale.ENGLISH);
                }



            }
        });
        resultbyJarvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tospeak = txt.getText().toString();
                final EditText hr = (EditText)findViewById(R.id.Hour);
                final EditText Min = (EditText)findViewById(R.id.Minute);
                Button set = (Button)findViewById(R.id.button3);
                Button bt = findViewById(R.id.button);
                EditText usern;


                if(tospeak.equals("what can you do")){
                    String output1 = "I Can Do Many Things. You Just Need To Ask, for example you can just ask , introduce your self";
                    answer.speak(output1,TextToSpeech.QUEUE_FLUSH,null);
                }else if (tospeak.equals("hello Jarvis")|| tospeak.equals("hello jarvis") ){
                    String output2 = "hello , What can I do For you ";
                    answer.speak(output2,TextToSpeech.QUEUE_FLUSH,null);
                }else if (tospeak.equals("introduce yourself")|| tospeak.equals("can you please introduce yourself")){
                    String intro = "Hello, I am an artificial robot made by my master soham";
                    answer.speak(intro,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("thank you Jarvis")||tospeak.equals("thank you very much Jarvis")||tospeak.equals("Jarvis thank you")){
                    String welcome = "Welcome Sir, I will be always at your help";
                    answer.speak(welcome,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("what is the time right now")){
                    SimpleDateFormat formatter = new SimpleDateFormat( "HH:mm");
                    Date date =new Date();
                    String time = "Currently its "+formatter.format(date);
                    answer.speak(time,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("which date is today")||tospeak.equals("what is the date today")||tospeak.equals("what is today's date")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd");
                    Date date = new Date();
                    String tdate = "Today's date is " + formatter.format(date);
                    answer.speak(tdate, TextToSpeech.QUEUE_FLUSH, null);
                }else if(tospeak.equals("which year is going on")||tospeak.equals("which is the current year")){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                    Date date = new Date();
                    String year = "Currently "+formatter.format(date);
                    answer.speak(year,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("which day is father's day")){
                    String fd = "Twenty june";
                    answer.speak("Father's day is on "+fd,TextToSpeech.QUEUE_FLUSH,null);
                }else if (tospeak.equals("When is mothers day")||tospeak.equals("which day is mothers day")){
                    String md = "Nine may";
                    answer.speak(md,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("open YouTube")){
                    String openingy = "Ok Sir, Opening YouTube";
                    answer.speak(openingy,TextToSpeech.QUEUE_FLUSH,null);
                    Intent webintent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/"));
                    try {
                        MainActivity.this.startActivity(webintent);

                    }catch (ActivityNotFoundException ex){
                        String sry = "Sorry I am not able to open Youtube";
                        answer.speak(sry,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }else if(tospeak.equals("set an alarm")||tospeak.equals("set alarm")) {
                    hr.setVisibility(View.VISIBLE);
                    Min.setVisibility(View.VISIBLE);
                    set.setVisibility(View.VISIBLE);
                    set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hour = Integer.parseInt(hr.getText().toString());
                            int Minute = Integer.parseInt(Min.getText().toString());
                            Intent setthealrm = new Intent(AlarmClock.ACTION_SET_ALARM);
                            setthealrm.putExtra(AlarmClock.EXTRA_HOUR, hour);
                            setthealrm.putExtra(AlarmClock.EXTRA_MINUTES, Minute);
                            if (hour <= 24 && Minute <= 60) {
                                startActivity(setthealrm);
                            }
                        }
                    });
                }else if (tospeak.equals("open photos")||tospeak.equals("open google photos")){
                    answer.speak("opening photos",TextToSpeech.QUEUE_FLUSH,null);
                    Intent photo = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://photos.google.com/"));
                    try {
                        MainActivity.this.startActivity(photo);
                    }catch (ActivityNotFoundException px){
                        answer.speak("sorry I am not able to open photos",TextToSpeech.QUEUE_FLUSH,null);

                    }

                }else if(tospeak.equals("open calculator")){
                    answer.speak("opening calculator",TextToSpeech.QUEUE_FLUSH,null);
                    Intent calc = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/search?q=calculator"));
                    try {
                        MainActivity.this.startActivity(calc);
                    }catch (ActivityNotFoundException cx){
                        answer.speak("Sorry, There is some problem in opening calculator",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.equals("open spotify")){
                    answer.speak("opening spotify",TextToSpeech.QUEUE_FLUSH,null);
                    Intent mu = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.spotify.com/in/"));
                    try {
                        MainActivity.this.startActivity(mu);

                    }catch (ActivityNotFoundException mx){
                        answer.speak("Sorry, There is some problem in opening spotify",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.equals("open maps")){
                    answer.speak("opening google maps",TextToSpeech.QUEUE_FLUSH,null);
                    Intent map = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/@18.4942592,73.8164736,13z"));

                    try{
                        MainActivity.this.startActivity(map);

                    }catch (ActivityNotFoundException mapx){
                        answer.speak("Sorry, There is some problem in opening maps",TextToSpeech.QUEUE_FLUSH,null);

                    }
                }else if(tospeak.substring(0,4).equals("show")){
                    int leng = tospeak.length();
                    String location = tospeak.substring(5,leng);
                    answer.speak("showing"+location,TextToSpeech.QUEUE_FLUSH,null);
                    Intent usl = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com/maps/place/"+location));
                    try {
                        MainActivity.this.startActivity(usl);
                    }catch (ActivityNotFoundException ulx){
                        answer.speak("Sorry, There is some problem in showing"+location,TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.equals("open play store")|| tospeak.equals("open Play Store")){
                    answer.speak("opening Play Store",TextToSpeech.QUEUE_FLUSH,null);
                    Intent play = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store"));
                    try{
                        MainActivity.this.startActivity(play);
                    }catch (ActivityNotFoundException plx){
                        answer.speak("Sorry, There is some problem in opening playstore",TextToSpeech.QUEUE_FLUSH,null);
                    }
                }else if(tospeak.equals("add a contact")){

                    startActivity(new Intent(MainActivity.this,  Contacts.class));

                }String func = txt.getText().toString();
                 if (func.substring(0,4).equals("call")){
                     int numberlength = func.length();

                     String numbtodial = func.substring(5,numberlength);
                     Intent call = new Intent(Intent.ACTION_CALL);
                     call.setData((Uri.parse("tel:"+numbtodial)));
                     startActivity(call);
                }


                else{
                    answer.speak("sorry I  couldn't hear that",TextToSpeech.QUEUE_FLUSH,null);
                }


                Toast.makeText(getApplicationContext(),txt.toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onPAUse() {
        if (answer != null) {
            answer.stop();
            answer.shutdown();
        }
        super.onPause();

    }}


// Speech Input




