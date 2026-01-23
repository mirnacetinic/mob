package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, scoreText, timerText;
    private Button option1, option2, option3, option4;

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 60 sekundi

    private boolean isPaused = false;
    private boolean timerRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        // Učitavanje pitanja
        loadQuestions();

        // Pokreni kviz
        if(!questions.isEmpty()){
            loadQuestion(currentQuestionIndex);
            startTimer();
        } else {
            Toast.makeText(this, "Nema pitanja za kviz", Toast.LENGTH_SHORT).show();
        }

        // Opcije click listeneri
        option1.setOnClickListener(v -> checkAnswer(option1.getText().toString()));
        option2.setOnClickListener(v -> checkAnswer(option2.getText().toString()));
        option3.setOnClickListener(v -> checkAnswer(option3.getText().toString()));
        option4.setOnClickListener(v -> checkAnswer(option4.getText().toString()));
    }

    private void loadQuestions() {
        questions = new ArrayList<>();

        // Primjer pitanja iz IT-a
        questions.add(new Question("Što je JVM?", "Java Virtual Machine", "Java Very Much", "Just Virtual Method", "Java Verified Module", "Java Virtual Machine"));
        questions.add(new Question("Što znači SQL?", "Structured Query Language", "Simple Query List", "Structured Question Language", "Sequential Query Language", "Structured Query Language"));
        questions.add(new Question("Koja metoda pokreće thread?", "start()", "run()", "init()", "execute()", "start()"));
        questions.add(new Question("Što je Android Studio?", "IDE za Android", "Database", "Emulator", "Framework", "IDE za Android"));
        questions.add(new Question("Koja je Java verzija LTS 2023?", "Java 17", "Java 16", "Java 18", "Java 11", "Java 17"));
        questions.add(new Question("Što je Git?", "Sustav kontrole verzija", "IDE", "Programski jezik", "Database", "Sustav kontrole verzija"));
        questions.add(new Question("Što je API?", "Application Programming Interface", "Application Protocol Instruction", "Advanced Programming Interface", "Application Performance Index", "Application Programming Interface"));
        questions.add(new Question("Koja oznaka označava privatnu varijablu u Javi?", "private", "protected", "public", "default", "private"));
        questions.add(new Question("Koji HTTP status znači 'Not Found'?", "404", "200", "500", "403", "404"));
        questions.add(new Question("Što je JSON?", "Format za podatke", "Programski jezik", "Database", "Editor", "Format za podatke"));
    }

    private void loadQuestion(int index) {
        if(index < 0 || index >= questions.size()){
            finishQuiz();
            return;
        }

        Question q = questions.get(index);
        questionText.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());
        option4.setText(q.getOption4());
        scoreText.setText("Bodovi: " + score);
    }

    private void checkAnswer(String selectedOption){
        Question q = questions.get(currentQuestionIndex);

        if(selectedOption.equals(q.getCorrectAnswer())){
            score += 10;
            Toast.makeText(this, "Točno!", Toast.LENGTH_SHORT).show();
        } else {
            score -= 5;
            Toast.makeText(this, "Netočno!", Toast.LENGTH_SHORT).show();
        }

        // Sljedeće pitanje
        currentQuestionIndex++;
        if(currentQuestionIndex < questions.size()){
            loadQuestion(currentQuestionIndex);
        } else {
            finishQuiz();
        }
    }

    private void startTimer() {
        timerRunning = true;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timerText.setText("Vrijeme: " + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                finishQuiz();
            }
        }.start();
    }


    private void finishQuiz(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }

        // Dohvati trenutno prijavljenog korisnika
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Result result = new Result(user.getEmail(),score);
            db.collection("results").add(result);
        }

        // ➡️ Idi na Celebration screen
        Intent intent = new Intent(QuizActivity.this, CelebrationActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (countDownTimer != null && timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            isPaused = true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isPaused && timeLeftInMillis > 0 && !timerRunning) {
            startTimer();
            isPaused = false;
        }
    }



}
