package com.mds.gab.multi_game.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.model.Score;


import io.realm.Realm;
import io.realm.RealmList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {
    private final RealmList<Score> scores;

    public ScoreAdapter(RealmList<Score> scores){
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.fragment_settings_row, viewGroup, false);
        return new ScoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Score score = scores.get(i);
        viewHolder.score.setText(score.getValue() + "");
        viewHolder.gameName.setText(score.getGame());

        //Récupération du score maximal
        Realm realm = Realm.getDefaultInstance();
        if(Long.valueOf(score.getValue()).equals(realm.where(Score.class).equalTo("game", score.getGame()).max("value"))){
            viewHolder.winner.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return scores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView winner;
        private final TextView gameName;
        private final TextView score;


        ViewHolder(View itemView){
            super(itemView);
            winner = itemView.findViewById(R.id.score_row_winner);
            gameName = itemView.findViewById(R.id.score_row_name);
            score = itemView.findViewById(R.id.score_row_score);
        }
    }

}
