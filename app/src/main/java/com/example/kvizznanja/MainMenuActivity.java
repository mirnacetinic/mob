package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button startQuizBtn = findViewById(R.id.startQuizBtn);
        Button myResultsBtn = findViewById(R.id.myResultsBtn);
        Button topResultsBtn = findViewById(R.id.topResultsBtn);
        Button logoutBtn = findViewById(R.id.logoutBtn); // dodaj u XML

        startQuizBtn.setOnClickListener(v -> startActivity(new Intent(this, QuizActivity.class)));
        myResultsBtn.setOnClickListener(v -> startActivity(new Intent(this, MyResultsActivity.class)));
        topResultsBtn.setOnClickListener(v -> startActivity(new Intent(this, TopResultsActivity.class)));
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
