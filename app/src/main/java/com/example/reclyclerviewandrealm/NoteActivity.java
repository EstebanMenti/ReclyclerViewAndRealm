package com.example.reclyclerviewandrealm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.reclyclerviewandrealm.adapters.AdapterBoard;
import com.example.reclyclerviewandrealm.adapters.AdapterNote;
import com.example.reclyclerviewandrealm.models.Board;
import com.example.reclyclerviewandrealm.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmList;

public class NoteActivity extends AppCompatActivity {

    private Realm realm;
    private ListView listView;
    private AdapterNote adapters;
    private RealmList<Note> notes;

    private int boardId;
    private Board board;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private AdapterNote.OnItemClickListener onItemClickListener;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if( getIntent().getExtras() != null){
            boardId = getIntent().getExtras().getInt("id");
        }

        board = realm.where(Board.class).equalTo("id",boardId).findFirst();
        notes = board.getNotes();

        this.setTitle(board.getTitle());


        //boards = realm.where(Board.class).findAll();

    }
}
