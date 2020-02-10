package com.example.reclyclerviewandrealm.app;

import android.app.Application;

import com.example.reclyclerviewandrealm.models.Board;
import com.example.reclyclerviewandrealm.models.Note;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Myapplication extends Application {

    public static AtomicInteger BoardId = new AtomicInteger();
    public static AtomicInteger NoteId = new AtomicInteger();

    @Override
    public void onCreate() {
        //Esta linea la agrege yo!!!!!
        Realm.init(getApplicationContext());

        // Configuración
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();

        BoardId = getIdByTable(realm, Board.class);
        NoteId = getIdByTable(realm, Note.class);
        realm.close();
    }

      private void setUpRealmConfig(){
        /*
        RealmConfiguration config = new RealmConfiguration
                .Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
         */
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        //return (results.size()>0)? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger;

        if( results.size() > 0){
            return new AtomicInteger(results.max("id").intValue());
        }
        return new AtomicInteger();
    }
}
