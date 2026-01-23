package com.example.kvizznanja;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyResultsActivity extends AppCompatActivity {

    private ListView resultsListView;
    private ArrayList<String> results;
    private ResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);

        resultsListView = findViewById(R.id.resultsListView);
        results = new ArrayList<>();
        adapter = new ResultsAdapter(this, results);
        resultsListView.setAdapter(adapter);

        loadMyResults();
    }

    private void loadMyResults() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Korisnik nije prijavljen", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = user.getEmail();

        FirebaseFirestore.getInstance()
                .collection("results")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Result> resultList = querySnapshot.toObjects(Result.class);

                    if (resultList.isEmpty()) {
                        Toast.makeText(this, "Nema tvojih rezultata", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    results.clear();
                    for (Result r : resultList) {
                        results.add(r.getEmail() + " - " + r.getScore());
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this,
                            "Greška pri dohvaćanju: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}
