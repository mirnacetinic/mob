package com.example.kvizznanja;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class MyResultsActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView title = findViewById(R.id.resultsTitle);
        title.setText("Moji rezultati");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMyResults();
    }

    private void loadMyResults() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        FirebaseFirestore.getInstance()
                .collection("results")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Result> results = querySnapshot.toObjects(Result.class);
                    if (results.isEmpty()) {
                        Toast.makeText(this, "Nemate spremljenih rezultata", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new ResultsAdapter(results);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}