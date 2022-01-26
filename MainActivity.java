package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private String[] voyelle = {"A", "E", "I", "O", "U", "Y"};
    private String[] consonne = {"B", "C", "D", "F", "J", "K", "L", "M", "N", "P", "H", "Q", "R", "S", "T", "V", "W", "X", "Z", "G"};
    Button b_consonne;
    Button b_voyelle;
    ImageButton b_rejouer;
    Button b_go;
    EditText wordEditText;
    LinearLayout listletter1;
    LinearLayout listletter2;
    TextView View_mot,tv_timer,score;
    TextView ListMotTextView;
    int len;
    int indiceLettre;
    String Letter;
    Animation smallbigforth;
    int compteur;
    int SecondRemaider=30;
    ProgressBar prog_timer;
    InputStream F ;
    BufferedReader br ;
    String line="";
    int nbrLignes=0;
    String Mot[];
    int amount;
    String ch3;
    DBHelper DB;
    DBscore dbs;
    String sc;




    CountDownTimer timer =new CountDownTimer(30000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            SecondRemaider--;
            tv_timer.setText(Integer.toString(SecondRemaider)+"sec");
            prog_timer.setProgress(30-SecondRemaider);

        }

        @Override
        public void onFinish() {

            tv_timer.setText(Integer.toString(SecondRemaider)+"sec");
            Toast toast = Toast. makeText(getApplicationContext(), "time is finished", Toast. LENGTH_SHORT);
            toast. show();
            b_consonne.setEnabled(false);
            b_voyelle.setEnabled(false);
            b_go.setEnabled(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        compteur=0;
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("usernameIntent");;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_timer=findViewById(R.id.tv_timer);
        score=findViewById(R.id.score);
        tv_timer.setText("0Sec");
        prog_timer=findViewById(R.id.prog_timer);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        listletter1 = (LinearLayout) findViewById(R.id.listletter1);
        listletter2 = (LinearLayout) findViewById(R.id.listletter2);
        wordEditText = (EditText) findViewById(R.id.word);
        ListMotTextView=(TextView)findViewById(R.id.ListMotTextView);
        wordEditText.setText("");
        b_consonne = (Button) findViewById(R.id.consonne);
        b_rejouer = (ImageButton)findViewById(R.id.Rejouer);
        b_voyelle = (Button) findViewById(R.id.voyelle);
        b_go=(Button)findViewById(R.id.go);
        Random r = new Random();
        ArrayList<String> FinalList = new ArrayList<String>();
        ArrayList<String> WordLetter = new ArrayList<String>();
        DB = new DBHelper(this);
        dbs = new DBscore(this);






        //creation de la table de mots
        try {
            F = getResources().openRawResource(R.raw.liste_francais);
            br=new BufferedReader(new InputStreamReader(F));
            while (line!= null)
            {
                line=br.readLine();
                nbrLignes++;

            }
            nbrLignes--;
            br.close();
            F.close();
            F = getResources().openRawResource(R.raw.liste_francais);
            br=new BufferedReader(new InputStreamReader(F));
            Mot=new String[nbrLignes]; int i=0;
            line="";
            while(line!=null&&i<nbrLignes)
            {
                line=br.readLine();
                Mot[i++]=new String(line.toUpperCase().toString());

            }
            br.close();
            F.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //****************************************************


        b_consonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondRemaider=30;

                len=0;
                indiceLettre=0;
                Letter="";
                len = b_consonne.length();
                indiceLettre = r.nextInt(len-1);
                Letter = consonne[indiceLettre];
                WordLetter.add(Letter);
                compteur++;
                if (compteur <= 5) {
                    addView(listletter1, Letter, wordEditText);
                } else {
                    addView(listletter2, Letter, wordEditText);
                }
                if(compteur==9){timer.start();}
               }
        });


        b_voyelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondRemaider=30;
                 len=0;
                indiceLettre=0;
                Letter="";
                len = b_voyelle.length();
                indiceLettre = r.nextInt(len-1);
                Letter = voyelle[indiceLettre];
                WordLetter.add(Letter);
                compteur++;
                if (compteur <= 5) {
                    addView(listletter1, Letter, wordEditText);
                } else {
                    addView(listletter2, Letter, wordEditText);
                }
                if(compteur==9){timer.start();}
                }
        });


        b_rejouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SecondRemaider=30;
                compteur=0;
                listletter1.removeAllViews();
                listletter2.removeAllViews();
                wordEditText.setText("");
                tv_timer.setText("0 Sec");
                b_voyelle.setEnabled(true);
                b_consonne.setEnabled(true);
                b_go.setEnabled(true);
                ListMotTextView.setText("");
                score.setText("Score");
                FinalList.clear();
                WordLetter.clear();

            }
        });



     //BOUTON GO ***********************************************
             amount=0;
        b_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compteur=0;

                ch3="";
                int max=0;
                for(String ltr:WordLetter)
                    ch3+=ltr;
                String[] TabLetter=ch3.split("");
                ArrayList<String> WordLetter2 = new ArrayList<String>();
                for(String let:TabLetter)
                    WordLetter2.add(let);


                for(String word : Mot)
                {
                    if(TestWordValid(word,WordLetter2))
                    {   if (max<word.length())
                    {max=word.length();}
                        FinalList.add(word);
                    }
                }

                String ch_word="";

                ch_word=wordEditText.getText().toString();

                if(FinalList.contains(ch_word))
                    {
                            if (ch_word.length() == max)
                            {
                                score.setText("10 points");
                            }
                            else
                                {
                                score.setText("5 points");
                                }
                    }
                else {
                    Toast.makeText(getApplicationContext(), "this word doesn't exist", Toast.LENGTH_SHORT).show();
                     }
               sc= score.getText().toString();
                Boolean insert = dbs.insertData(name, sc);







                StringBuilder builder = new StringBuilder();
                String newLine = System.getProperty("line.separator");
                for(String e:FinalList)
                {builder.append(e).append(newLine);}
                ListMotTextView.setText(builder.toString());



            }
        });




    }


    private void addView(LinearLayout viewParent, final String text, final EditText editText) {

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.rightMargin = 20;
        final TextView textView = new TextView(this);
        textView.setLayoutParams(linearLayoutParams);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.teal_200));

        textView.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                editText.setText(editText.getText().toString() + text);
                textView.startAnimation(smallbigforth);
                textView.animate().alpha(0).setDuration(300);
                textView.setClickable(false);

            }
        });

        viewParent.addView(textView);


    }


    public static boolean TestWordValid(String st ,ArrayList al)
    {
        String[] stTAB;

        ArrayList<String> ListLetter = new ArrayList<String>(al);

        stTAB=st.toUpperCase().split("");
        for (String e:stTAB)
        {
            if(ListLetter.indexOf(e)==-1)
            {
                return false;
            }
            else
                ListLetter.set(ListLetter.indexOf(e),"-");

        }

        return true;
    }

}

