package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private Button startQuizBtn, myResultsBtn, topResultsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        startQuizBtn = findViewById(R.id.startQuizBtn);
        myResultsBtn = findViewById(R.id.myResultsBtn);
        topResultsBtn = findViewById(R.id.topResultsBtn);

        startQuizBtn.setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, QuizActivity.class)));
        myResultsBtn.setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, MyResultsActivity.class)));
        topResultsBtn.setOnClickListener(v -> startActivity(new Intent(MainMenuActivity.this, TopResultsActivity.class)));
    }
}
