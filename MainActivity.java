package com.example.jarvis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.provider.AlarmClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


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
        final String tospeak;
        tospeak =txt.getText().toString();
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
                String tospeak;
                tospeak =txt.getText().toString();
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
                }




                else if(tospeak.equals("which date is today")||tospeak.equals("what is the date today")||tospeak.equals("what is today's date")) {
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
                }else if (tospeak.equals("When is mother's day")||tospeak.equals("which day is mothers day")){
                    String md = "Nine may";
                    answer.speak(md,TextToSpeech.QUEUE_FLUSH,null);
                }else if(tospeak.equals("open youtube")){
                    String openingy = "Ok Sir, Opening YouTube";
                    answer.speak(openingy,TextToSpeech.QUEUE_FLUSH,null);
                    Intent webintent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com"));
                    try {
                        MainActivity.this.startActivity(webintent);

                    }catch (ActivityNotFoundException ex){
                        String sry = "Sorry I am not able to open Youtube";
                        answer.speak(sry,TextToSpeech.QUEUE_FLUSH,null);
                    }

                }else if(tospeak.equals("set an alarm")||tospeak.equals("set alarm")){
                    hr.setVisibility(View.VISIBLE);
                    Min.setVisibility(View.VISIBLE);
                    set.setVisibility(View.VISIBLE);
                    set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hour = Integer.parseInt(hr.getText().toString());
                            int Minute = Integer.parseInt(Min.getText().toString());
                            Intent setthealrm = new Intent(AlarmClock.ACTION_SET_ALARM);
                            setthealrm.putExtra(AlarmClock.EXTRA_HOUR,hour);
                            setthealrm.putExtra(AlarmClock.EXTRA_MINUTES,Minute);
                            if(hour <= 24 && Minute<= 60){
                                startActivity(setthealrm);
                            }
                        }
                    });
                }else if(tospeak.substring(0,8).equals("remember")){
                    EditText usersnotes = (EditText)findViewById(R.id.un);
                    SharedPreferences remember = getApplicationContext().getSharedPreferences("com.example.jarvis", Context.MODE_PRIVATE);
                    remember.edit().putString("To remember",txt.getText().toString()).apply();

                    usersnotes.setText(remember.getString("To remember",""));

                }else if(tospeak.equals("get my notes")){
                    EditText usersnotes = (EditText)findViewById(R.id.un);
                    answer.speak(usersnotes.toString(), TextToSpeech.QUEUE_FLUSH, null);
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
                }else if(tospeak.substring(0,17).equals("send a message to")){
                    Integer eni = tospeak.length();
                    String Message = tospeak.substring(37,eni);
                    String phoneno = tospeak.substring(17,30);
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneno,null,Message,null,null);
                    answer.speak("A Message has been sent to"+phoneno,TextToSpeech.QUEUE_FLUSH,null);
                    Toast.makeText(getApplicationContext(),phoneno,Toast.LENGTH_SHORT).show();
                }else if(tospeak.substring(0,19).equals("good morning Jarvis")){
                    SimpleDateFormat formatter = new SimpleDateFormat( "HH:mm");
                    Date date =new Date();
                    String time = formatter.format(date);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd");
                    String currentDateandTime = sdf.format(new Date());

                    String tosay = "Good Morning Sir. I am feeling that today's day is going to be exciting. Today's date is "+currentDateandTime+"and. Currently its "+time;
                    answer.speak(tosay, TextToSpeech.QUEUE_FLUSH, null);


                }


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
