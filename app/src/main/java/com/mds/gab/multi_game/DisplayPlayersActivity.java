package com.mds.gab.multi_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mds.gab.multi_game.adapter.PlayerAdapter;
import com.mds.gab.multi_game.model.Player;
import com.mds.gab.multi_game.utils.ActivityUtils;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class DisplayPlayersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_players);
        recyclerView = findViewById(R.id.player_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlayerAdapter adapter = new PlayerAdapter(getAllPlayers());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DisplayPlayersActivity.this.overridePendingTransition(0, R.anim.slide_out_to_right);
    }

    private ArrayList<Player> getAllPlayers(){
        Realm mRealmInstance = Realm.getDefaultInstance();
        RealmQuery query = mRealmInstance.where(Player.class);
        ArrayList<Player> players = new ArrayList<Player>(query.findAll());
        return players;
    }
}
