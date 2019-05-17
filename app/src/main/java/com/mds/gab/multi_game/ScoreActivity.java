package com.mds.gab.multi_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mds.gab.multi_game.manager.PlayerManager;
import com.mds.gab.multi_game.model.Player;
import com.mds.gab.multi_game.model.Score;
import com.mds.gab.multi_game.utils.ActivityUtils;

import io.realm.Realm;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView titleTv = findViewById(R.id.score_title);
        TextView scoreTv = findViewById(R.id.score_score);
        Button menuB = findViewById(R.id.score_menu);
        Button leaveB = findViewById(R.id.score_leave);

        Bundle bundle = getIntent().getExtras();
        titleTv.setText(bundle != null ? bundle.getString("title") : null);
        scoreTv.setText(Integer.toString(bundle.getInt("score")));

        Realm realm = Realm.getDefaultInstance();
        Player player = PlayerManager.getInstance().getPlayer();
        realm.beginTransaction();
        try {
            boolean save = true;
            for(Score score : player.getScore()){
                if(score.getGame().equals(bundle.getString("title"))){
                    if(score.getValue() < bundle.getInt("score")){
                        score.setValue(bundle.getInt("score"));
                        realm.copyToRealmOrUpdate(score);
                    }
                    save = false;
                }
            }
            if(save){
                Score score = new Score(bundle.getString("title"), bundle.getInt("score"));
                realm.copyToRealmOrUpdate(score);
                player.addScore(score);
            }
            realm.copyToRealmOrUpdate(player);
            realm.commitTransaction();
            PlayerManager.getInstance().setPlayer(player);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

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
