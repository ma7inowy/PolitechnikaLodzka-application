package com.team.szkielet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.team.szkielet.quiz.QuizMainActivity;

public class MainActivityBetter extends AppCompatActivity {

    CardView cvPlanZajec, cvProwadzacy, cvWydarzenia, cvQuiz, cvSale, cvUstawienia;
    private long backPressedTime;
    TextView tvHello, tvAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_better);

        tvHello = findViewById(R.id.tvHello);
        tvAgain = findViewById(R.id.tvAgain);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        Plany.czyMamyZapisaneDane = readingFromSharedPreferences();

        cvPlanZajec = findViewById(R.id.cvPlanZajec);
        buttonEffect(cvPlanZajec);
        cvPlanZajec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(Plany.czyMamyZapisaneDane){
                    intent = new Intent(MainActivityBetter.this, Plany.class);
                    startActivity(intent);
                }
                else {
                    intent = new Intent(MainActivityBetter.this, PopUpInPlany.class);
                    startActivity(intent);
                }
            }
        });

        cvProwadzacy = findViewById(R.id.cvProwadzacy);
        buttonEffect(cvProwadzacy);
        cvProwadzacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityBetter.this, Prowadzacy.class);
                startActivity(intent);
            }
        });

        cvWydarzenia = findViewById(R.id.cvWydarzenia);
        buttonEffect(cvWydarzenia);
        cvWydarzenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityBetter.this, Aktualnosci.class);
                startActivity(intent);
            }
        });

        cvQuiz = findViewById(R.id.cvQuiz);
        buttonEffect(cvQuiz);
        cvQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivityBetter.this, "Witamy w Quizie", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivityBetter.this, QuizMainActivity.class));
            }
        });

        cvSale = findViewById(R.id.cvSale);
        buttonEffect(cvSale);
        cvSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cvUstawienia = findViewById(R.id.cvUstawienia);
        buttonEffect(cvUstawienia);
        cvUstawienia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityBetter.this, PopUpInPlany.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        readingFromSharedPreferences();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        }
        else {
            Toast.makeText(MainActivityBetter.this, "Press back again to quit app", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    boolean readingFromSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("UserInfo", 0);
        String name = sharedPref.getString("name", "");
        String stopien = sharedPref.getString("stopien", "");
        String kierunek = sharedPref.getString("kierunek", "");
        String rodzaj = sharedPref.getString("rodzaj", "");
        String rok = sharedPref.getString("rok", "");
        if(name.length()>0 && !rok.equals("0")) {
            //tvHello.setText("Cześć " + name + "!!!\nStopień: " + stopien + " Kierunek: " + kierunek + "\nRodzaj: " + rodzaj + " Rok: " + rok);
            tvHello.setText("Cześć " + name + "!!!");
            tvAgain.setText("Here we go again...");
            PopUpInPlany.wasSaved = true;
            return true;
        }
        else if(name.length()>0 && rok.equals("0")) {
            //tvHello.setText("Cześć " + name + "!!!\nStopień: " + stopien + " Kierunek: " + kierunek + "\nRodzaj: " + rodzaj);
            tvHello.setText("Cześć " + name + "!!!");
            tvAgain.setText("Here we go again...");
            PopUpInPlany.wasSaved = true;
            return true;
        }
        PopUpInPlany.wasSaved = false;
        return false;
    }

    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {
        boolean wasClicked = false;
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0FCE1D9, PorterDuff.Mode.SRC_ATOP);
                        v.setElevation(10);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                v.setAlpha(0.7f);
                                v.invalidate();
                            }
                        }, 200);

                        wasClicked = true;
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if(wasClicked){
                            v.getBackground().setColorFilter(0xe0FCE1D9, PorterDuff.Mode.SRC_ATOP);
                            v.setAlpha(0.7f);
                            v.setElevation(10);
                            v.invalidate();
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                v.getBackground().clearColorFilter();
                                v.setAlpha(1f);
                                v.setElevation(6);
                                v.invalidate();
                                wasClicked = false;
                            }
                        }, 350);

                        break;
                    }
                }
                return false;
            }
        });
    }
}
