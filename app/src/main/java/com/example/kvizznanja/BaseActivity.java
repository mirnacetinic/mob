package com.example.kvizznanja;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        boolean isLoginOrRegister = this instanceof LoginActivity || this instanceof RegisterActivity;

        if (user == null) {
            if (!isLoginOrRegister) {
                redirect(LoginActivity.class);
            }
        } else {
            if (isLoginOrRegister) {
                redirect(MainMenuActivity.class);
            }
        }
    }

    private void redirect(Class<?> targetClass) {
        Intent i = new Intent(this, targetClass);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}