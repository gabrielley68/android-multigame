package com.mds.gab.multi_game;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mds.gab.multi_game.manager.PlayerManager;
import com.mds.gab.multi_game.model.Player;
import com.mds.gab.multi_game.utils.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class CreatePlayerActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_PICTURE = 1;
    private static final int REQUEST_LOCATION = 2;

    private EditText nameEt;
    private EditText surnameEt;
    private EditText ageEt;
    private EditText localisationEt;
    private ImageView avatarIv;
    private Button playersB;
    private Button validateB;
    private ImageView localisationIv;
    private String avatarPath;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        nameEt = findViewById(R.id.create_player_name);
        surnameEt = findViewById(R.id.create_player_surname);
        ageEt = findViewById(R.id.create_player_age);
        localisationEt = findViewById(R.id.create_player_localisation);
        avatarIv = findViewById(R.id.create_player_avatar);
        playersB = findViewById(R.id.create_player_players);
        validateB = findViewById(R.id.create_player_validate);
        localisationIv = findViewById(R.id.create_player_localisation_button);

        /*
         * Changement d'avatar
         */
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(Intent.createChooser(intent, "Choix de la photo"), REQUEST_PICK_PICTURE);
            }
        });

        /*
        * Géoloc
         */
        localisationIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreatePlayerActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    getUserLocation();
                }
            }
        });

        validateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameEt.getText().toString().equals("")
                    && !surnameEt.getText().toString().equals("")
                    && !ageEt.getText().toString().equals("")
                    && !localisationEt.getText().toString().equals("")
                    && avatarPath != null)
                {
                    Player player = new Player(nameEt.getText().toString(), surnameEt.getText().toString(), ageEt.getText().toString(), localisationEt.getText().toString(), avatarPath);
                    addPlayerInBdd(player);
                    PlayerManager.getInstance().setPlayer(player);

                    ActivityUtils.launchActivity(CreatePlayerActivity.this, MainActivity.class, false, ActivityUtils.SLIDE_RIGHT);
                } else {
                    Toast.makeText(CreatePlayerActivity.this, "Il manque un ou plusieurs champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playersB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.launchActivity(CreatePlayerActivity.this, DisplayPlayersActivity.class, false, ActivityUtils.SLIDE_RIGHT);
            }
        });
    }

    private void addPlayerInBdd(Player player){
        Realm mRealmInstance = Realm.getDefaultInstance();
        mRealmInstance.beginTransaction();
        try{
            mRealmInstance.copyToRealmOrUpdate(player);
                mRealmInstance.commitTransaction();
        } catch (Exception e){
            String a = "";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_LOCATION) {
            if(ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(CreatePlayerActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                getUserLocation();
            }
        }
    }

    /**
     * Retour du choix de l'image
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PICK_PICTURE && resultCode == RESULT_OK){
            Uri uri = data.getData();
            Picasso.get().load(uri).into(avatarIv);
            avatarPath = data.getData().getPath();
        }
    }

    @SuppressLint("MissingPermission")
    private void getUserLocation(){
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                localisationEt.setText(location.getLatitude() + "," + location.getLongitude());
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);
    }
}
