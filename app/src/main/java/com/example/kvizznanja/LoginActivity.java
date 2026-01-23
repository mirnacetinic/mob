package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn, registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this); // inicijalizacija Firebase-a
        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(v -> loginUser());
        registerBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        // Ako je već prijavljen, odmah ide na izbornik
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            finish();
        }
    }

    private void loginUser() {
        String e = email.getText().toString();
        String p = password.getText().toString();

        if(e.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Unesi email i lozinku", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
