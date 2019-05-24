package com.scode.game2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;
    public MainActivity() {
        mainActivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTv = (TextView) findViewById(R.id.Scoretv);


    }

    private TextView scoreTv;

    public int getScore() {
        return score;
    }

    private int score = 0;

    public void clearScore() {
        score = 0;
        showScore();
    }

    public void showScore() {
        scoreTv.setText(score + "");
    }

    public void addScore(int score) {
        this.score += score;
        showScore();
    }

}
