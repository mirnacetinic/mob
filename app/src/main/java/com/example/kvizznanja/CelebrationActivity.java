package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

public class CelebrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebration);

        TextView scoreView = findViewById(R.id.scoreView);
        TextView timeView = findViewById(R.id.timeView);
        TextView trophy = findViewById(R.id.trophy);
        TextView messageTitle = findViewById(R.id.messageTitle);
        Button menuBtn = findViewById(R.id.menuBtn);
        Button myResultsBtn = findViewById(R.id.myResultsBtn);

        int score = getIntent().getIntExtra("score", 0);
        scoreView.setText("Tvoj rezultat: " + score);

        int time = getIntent().getIntExtra("timeLeft", 0);
        timeView.setText("Preostalo ti je još: " + time + " s");

        if (score >= 50) {
            messageTitle.setText("Čestitamo!");
            trophy.setText("🏆");
        } else {
            messageTitle.setText("Više sreće drugi put!");
            trophy.setText("💪");
        }

        playCelebrationAnimation(scoreView, trophy);

        menuBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainMenuActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        myResultsBtn.setOnClickListener(v ->
                startActivity(new Intent(this, MyResultsActivity.class))
        );
    }

    private void playCelebrationAnimation(TextView scoreView, TextView trophy) {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(600);

        ScaleAnimation scale = new ScaleAnimation(
                0.5f, 1.2f, 0.5f, 1.2f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(600);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(fadeIn);
        set.addAnimation(scale);

        trophy.startAnimation(set);
        scoreView.startAnimation(fadeIn);
    }
}