package com.example.randomsequencegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        dbHelper = new DatabaseHelper(this);
        backButton = findViewById(R.id.backButton);

        // Fetch the top scores from the database
        ArrayList<String> highScores = dbHelper.fetchTopScores();

        // Populate the ListView with the scores
        ListView highScoresListView = findViewById(R.id.highScoreList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, highScores);
        highScoresListView.setAdapter(adapter);

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
