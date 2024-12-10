package com.example.randomsequencegame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity {
    private ArrayList<Integer> sequence;
    private Random random;
    private TextView sequenceText;
    private Handler handler;
    private int score;
    private int round;
    private final int[] colorViewIds = {R.id.color1, R.id.color2, R.id.color3, R.id.color4};
    private final int[] colors = {
            0xFFFF6666,
            0xFF66B2FF,
            0xFF66FF66,
            0xFFFFFF66
    };
    private static final int COLOR_DISPLAY_TIME = 700;
    private static final int COLOR_RESET_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        sequenceText = findViewById(R.id.sequenceText);
        handler = new Handler();
        random = new Random();
        sequence = new ArrayList<>();

        score = getIntent().getIntExtra("score", 0);
        round = getIntent().getIntExtra("round", 1);

        Log.d("SequenceActivity", "Starting round: " + round); // Debug log

        generateSequence();
        showSequence();
    }

    private void generateSequence() {
        sequence.clear();
        int sequenceLength = 4 + (round - 1) * 2;
        Log.d("SequenceActivity", "Generating sequence of length: " + sequenceLength);
        for (int i = 0; i < sequenceLength; i++) {
            sequence.add(random.nextInt(4));
        }
    }

    private void showSequence() {
        sequenceText.setText("Watch the sequence");
        handler.postDelayed(() -> displaySequenceStep(0), 1000);
    }

    private void displaySequenceStep(int step) {
        if (step < sequence.size()) {
            int colorIndex = sequence.get(step);
            highlightColorView(colorIndex);

            handler.postDelayed(() -> {
                resetColorView(colorIndex);
                handler.postDelayed(() -> displaySequenceStep(step + 1), COLOR_RESET_TIME);
            }, COLOR_DISPLAY_TIME);
        } else {
            startPlayActivity();
        }
    }

    private void highlightColorView(int colorIndex) {
        View colorView = findViewById(colorViewIds[colorIndex]);
        if (colorView != null) {
            colorView.setBackgroundColor(colors[colorIndex]);
        }
    }

    private void resetColorView(int colorIndex) {
        View colorView = findViewById(colorViewIds[colorIndex]);
        if (colorView != null) {
            colorView.setBackgroundResource(R.color.accent_color);
        }
    }

    private void startPlayActivity() {
        handler.postDelayed(() -> {
            Intent intent = new Intent(SequenceActivity.this, PlayActivity.class);
            intent.putIntegerArrayListExtra("sequence", sequence);
            intent.putExtra("score", score);
            intent.putExtra("round", round);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
