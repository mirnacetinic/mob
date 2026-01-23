package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class QuizActivity extends BaseActivity {

    private TextView questionText, scoreText, timerText;
    private Button[] options = new Button[4];
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0, score = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        options[0] = findViewById(R.id.option1);
        options[1] = findViewById(R.id.option2);
        options[2] = findViewById(R.id.option3);
        options[3] = findViewById(R.id.option4);

        loadQuestions();
        showQuestion();
        startTimer();
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
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

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionText.setText(q.getQuestion());
            options[0].setText(q.getOption1());
            options[1].setText(q.getOption2());
            options[2].setText(q.getOption3());
            options[3].setText(q.getOption4());

            for (Button btn : options) {
                btn.setOnClickListener(v -> checkAnswer(btn.getText().toString(), q.getCorrectAnswer()));
            }
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer(String selected, String correct) {
        if (selected.equals(correct)) score += 10;
        scoreText.setText("Bodovi: " + score);
        currentQuestionIndex++;
        showQuestion();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) { timerText.setText("Vrijeme: " + l / 1000 + "s"); }
            @Override
            public void onFinish() { finishQuiz(); }
        }.start();
    }

    private void finishQuiz() {
        if (countDownTimer != null) countDownTimer.cancel();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseFirestore.getInstance().collection("results").add(new Result(email, score));

        Intent i = new Intent(this, CelebrationActivity.class);
        i.putExtra("score", score);
        startActivity(i);
        finish();
    }
}