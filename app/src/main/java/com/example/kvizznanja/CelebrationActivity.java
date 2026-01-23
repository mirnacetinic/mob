package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CelebrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebration);

        TextView scoreView = findViewById(R.id.scoreView);
        Button menuBtn = findViewById(R.id.menuBtn);
        Button myResultsBtn = findViewById(R.id.myResultsBtn);

        int score = getIntent().getIntExtra("score", 0);
        scoreView.setText("Tvoj rezultat: " + score);

        menuBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainMenuActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        myResultsBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, MyResultsActivity.class));
        });
    }
}
