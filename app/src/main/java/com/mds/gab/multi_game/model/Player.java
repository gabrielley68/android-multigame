package com.mds.gab.multi_game.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {

    @PrimaryKey
    private String name;
    private String firstName;
    private int age;
    private String location;
    private String picture;
    private int score;

    public Player(){

    }

    public Player(String name, String firstName, String age, String localisation, String picture){
        setName(name);
        setFirstName(firstName);
        setAge(Integer.parseInt(age));
        setLocation(localisation);
        setPicture(picture);
        score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getScore(){return score;}

    public void setScore(int score){this.score = score;}

    public void addScore(int score){this.score += score;}
}
