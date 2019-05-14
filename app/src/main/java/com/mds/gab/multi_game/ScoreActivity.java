package com.mds.gab.multi_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mds.gab.multi_game.utils.ActivityUtils;

public class ScoreActivity extends AppCompatActivity {

    TextView titleTv;
    TextView scoreTv;
    Button menuB;
    Button leaveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        titleTv = findViewById(R.id.score_title);
        scoreTv = findViewById(R.id.score_score);
        menuB = findViewById(R.id.score_menu);
        leaveB = findViewById(R.id.score_leave);

        Bundle bundle = getIntent().getExtras();
        titleTv.setText(bundle.getString("title"));
        scoreTv.setText(Integer.toString(bundle.getInt("score")));

        menuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchActivity(ScoreActivity.this, MainActivity.class);
            }
        });

        leaveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }
}
