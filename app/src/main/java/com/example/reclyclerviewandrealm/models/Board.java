package com.example.reclyclerviewandrealm.models;

import com.example.reclyclerviewandrealm.app.Myapplication;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Board extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private Date createAt;

    private RealmList<Note> notes;

    public Board() {
    }

    public Board(String title) {
        this.id = Myapplication.BoardId.incrementAndGet();
        this.createAt = new Date();
        this.notes = new RealmList<>();
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateAt() {
        return createAt;
    }


    public RealmList<Note> getNotes() {
        return notes;
    }

}