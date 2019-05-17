package com.mds.gab.multi_game.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Score extends RealmObject {

    @PrimaryKey
    private String id;
    private String game;
    private int value;

    public Score(){

    }

    public Score(String game, int value){
        id = UUID.randomUUID().toString();
        setGame(game);
        setValue(value);
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
