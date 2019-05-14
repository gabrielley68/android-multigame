package com.mds.gab.multi_game.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mds.gab.multi_game.DisplayPlayersActivity;
import com.mds.gab.multi_game.MainActivity;
import com.mds.gab.multi_game.R;
import com.mds.gab.multi_game.manager.PlayerManager;
import com.mds.gab.multi_game.model.Player;
import com.mds.gab.multi_game.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private ArrayList<Player> players;

    public PlayerAdapter(ArrayList<Player> players){
        this.players = players;

    }

    @NonNull
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.activity_display_players_row, viewGroup, false);
        return new PlayerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerAdapter.ViewHolder viewHolder, int i) {
        final Player player = players.get(i);
        viewHolder.surname.setText(player.getFirstName());
        viewHolder.name.setText(player.getName());
        Picasso.get().load(player.getPicture()).into(viewHolder.avatar);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerManager.getInstance().setPlayer(player);
                ActivityUtils.launchActivity((AppCompatActivity)v.getContext(), MainActivity.class, true, ActivityUtils.SLIDE_RIGHT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView surname;
        private LinearLayout container;


        ViewHolder(View itemView){
            super(itemView);
            avatar = itemView.findViewById(R.id.display_players_row_avatar);
            name = itemView.findViewById(R.id.display_players_row_name);
            surname = itemView.findViewById(R.id.display_players_row_surname);
            container = itemView.findViewById(R.id.display_players_row_container);
        }
    }
}
