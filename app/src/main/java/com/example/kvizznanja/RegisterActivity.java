package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, password;
    private Button registerBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String e = email.getText().toString();
        String p = password.getText().toString();

        if(e.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Unesi email i lozinku", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(this, "Registracija uspješna", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, MainMenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registracija nije uspjela: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
