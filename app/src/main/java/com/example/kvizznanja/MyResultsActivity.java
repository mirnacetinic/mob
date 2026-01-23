package com.example.kvizznanja;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyResultsActivity extends AppCompatActivity {

    private ListView resultsListView;
    private ArrayList<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);

        resultsListView = findViewById(R.id.resultsListView);

        // Dummy lista rezultata, kasnije poveži s Firebase Firestore
        results = new ArrayList<>();
        results.add("Ivan - 80");
        results.add("Ana - 70");
        results.add("Marko - 60");

        ResultsAdapter adapter = new ResultsAdapter(this, results);
        resultsListView.setAdapter(adapter);
    }
}
