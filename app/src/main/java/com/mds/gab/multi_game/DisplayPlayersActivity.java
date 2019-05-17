package com.mds.gab.multi_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mds.gab.multi_game.adapter.PlayerAdapter;
import com.mds.gab.multi_game.model.Player;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DisplayPlayersActivity extends AppCompatActivity {

    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_players);
        RecyclerView recyclerView = findViewById(R.id.player_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayerAdapter(getAllPlayers());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DisplayPlayersActivity.this.overridePendingTransition(0, R.anim.slide_out_to_right);
    }

    private ArrayList<Player> getAllPlayers(){
        final Realm mRealmInstance = Realm.getDefaultInstance();
        RealmQuery<Player> query = mRealmInstance.where(Player.class);
        RealmResults<Player> players = query.findAll();
        players.addChangeListener(new RealmChangeListener<RealmResults<Player>>() {
            @Override
            public void onChange(RealmResults<Player> players) {
                adapter.setItems((ArrayList<Player>)mRealmInstance.copyFromRealm(players));
                adapter.notifyDataSetChanged();
            }
        });
        return new ArrayList<>(players);
    }
}
