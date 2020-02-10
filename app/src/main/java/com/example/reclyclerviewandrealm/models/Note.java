package com.example.reclyclerviewandrealm.models;

import com.example.reclyclerviewandrealm.app.Myapplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Note extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String descripcion;
    @Required
    private Date createAt;

    public Note() {
    }

    public Note(String descripcion) {
        this.id = Myapplication.NoteId.incrementAndGet();
        this.createAt = new Date();
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreateAt() {
        return createAt;
    }

}
