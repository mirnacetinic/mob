package com.example.kvizznanja;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class TopResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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

                    if(results.isEmpty()){
                        Toast.makeText(this, "Nema rezultata za prikaz", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new TopResultsAdapter(results);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Ne mogu dohvatiti rezultate: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
