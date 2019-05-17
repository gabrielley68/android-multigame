package com.mds.gab.multi_game.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;


import com.mds.gab.multi_game.CreatePlayerActivity;
import com.mds.gab.multi_game.DisplayPlayersActivity;
import com.mds.gab.multi_game.MainActivity;
import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.adapter.ScoreAdapter;
import com.mds.gab.multi_game.manager.PlayerManager;
import com.mds.gab.multi_game.model.Score;
import com.mds.gab.multi_game.utils.ActivityUtils;


import io.realm.RealmList;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.score_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ScoreAdapter adapter = new ScoreAdapter(getScore());
        recyclerView.setAdapter(adapter);

        Button deconnexionB = view.findViewById(R.id.settings_deconnexion);
        deconnexionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchActivity((MainActivity)getActivity(), CreatePlayerActivity.class);
            }
        });

        Button changePlayerB = view.findViewById(R.id.settings_change_player);
        changePlayerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchActivity((AppCompatActivity)getActivity(), DisplayPlayersActivity.class);
            }
        });

        return view;
    }

    private RealmList<Score> getScore(){
        return PlayerManager.getInstance().getPlayer().getScore();
    }
}
