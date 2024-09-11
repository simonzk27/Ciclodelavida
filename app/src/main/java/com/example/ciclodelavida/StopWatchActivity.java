package com.example.ciclodelavida;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopWatchActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private List<String> lapTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            lapTimes = savedInstanceState.getStringArrayList("lapTimes");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putStringArrayList("lapTimes", (ArrayList<String>) lapTimes);
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        lapTimes.clear(); // Reiniciar el historial de vueltas
        updateLapTimesDisplay();
    }

    public void onClickLap(View view) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String lapTime = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        lapTimes.add(lapTime);
        updateLapTimesDisplay();
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateLapTimesDisplay() {
        TextView lapTimesView = (TextView) findViewById(R.id.time_view_laps);
        StringBuilder lapTimesText = new StringBuilder();
        for (String lapTime : lapTimes) {
            lapTimesText.append(lapTime).append("\n");
        }
        lapTimesView.setText(lapTimesText.toString());
        }
}