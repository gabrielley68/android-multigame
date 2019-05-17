package com.mds.gab.multi_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mds.gab.multi_game.manager.PlayerManager;
import com.mds.gab.multi_game.model.Player;
import com.mds.gab.multi_game.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class PlayerDetail extends AppCompatActivity {

    private ImageView playerAvatarIv;
    private TextView playerNameTv;
    private TextView playerSurnameTv;
    private TextView playerAgeTv;
    private TextView playerLocationTv;
    private Button modifyB;
    private Button deleteB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        playerAvatarIv = findViewById(R.id.player_detail_photo);

        playerSurnameTv = findViewById(R.id.player_detail_surname);
        playerNameTv = findViewById(R.id.player_detail_name);
        playerAgeTv = findViewById(R.id.player_detail_age);
        playerLocationTv = findViewById(R.id.player_detail_localisation);

        //Pour l'instant on ne peux pas modifier
        modifyB = findViewById(R.id.player_detail_modify);
        modifyB.setEnabled(false);

        deleteB = findViewById(R.id.player_detail_delete);

        //On remplit les champs à partir de Realm
        final Player player = PlayerManager.getInstance().getPlayer();
        playerSurnameTv.setText(player.getFirstName());
        playerNameTv.setText(player.getName());
        playerAgeTv.setText(Integer.toString(player.getAge()));
        playerLocationTv.setText(player.getLocation());
        Picasso.get().load(player.getPicture()).into(playerAvatarIv);

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                try {
                    player.deleteFromRealm();
                    realm.commitTransaction();
                    finish();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
