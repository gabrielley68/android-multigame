package com.mds.gab.multi_game.application;

import android.app.Application;

import com.mds.gab.multi_game.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MultiGameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(3)
                .name(getResources().getString(R.string.app_name)+".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
