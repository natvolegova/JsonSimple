package com.example.intentsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;

public class NextActivity extends AppCompatActivity {
    TextView txtResult;
    TextView timerValue;
    Button startButton;
    Button pauseButton;
    Button stopButton;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        txtResult = findViewById(R.id.txtResult);
        Intent intent = getIntent();
        if (intent.hasExtra(intent.EXTRA_TEXT)) {
            String value = intent.getStringExtra(intent.EXTRA_TEXT);
            txtResult.setText(value);
        }
        timerValue = findViewById(R.id.txt_timer);
        startButton = (Button) findViewById(R.id.btn_start);
        pauseButton = (Button) findViewById(R.id.btn_pause);
        stopButton = (Button) findViewById(R.id.btn_stop);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = 0L;
                customHandler.removeCallbacks(updateTimerThread);
                timerValue.setText(getString(R.string.timer_val));
            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

}
