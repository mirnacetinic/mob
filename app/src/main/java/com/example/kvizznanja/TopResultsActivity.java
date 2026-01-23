package com.example.kvizznanja;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.List;

public class TopResultsActivity extends BaseActivity { // Koristi BaseActivity zbog Firebase provjere

    private RecyclerView recyclerView;
    private ResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView title = findViewById(R.id.resultsTitle);
        title.setText("Top 10 Rezultata");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTopResults();
    }

    private void loadTopResults() {
        FirebaseFirestore.getInstance()
                .collection("results")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Result> results = querySnapshot.toObjects(Result.class);
                    if (!results.isEmpty()) {
                        adapter = new ResultsAdapter(results);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, "Nema spremljenih rezultata", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}