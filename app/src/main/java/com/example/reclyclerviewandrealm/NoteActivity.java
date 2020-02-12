package com.example.reclyclerviewandrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reclyclerviewandrealm.adapters.AdapterNote;
import com.example.reclyclerviewandrealm.models.Board;
import com.example.reclyclerviewandrealm.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private Realm realm;
    private RealmList<Note> notes;

    private int boardId;
    private Board board;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
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
        // Hablita que cambios en este elemento (board) genera un llamado al metodo onChange
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());


        recyclerView = findViewById(R.id.recyclerViewNote);
        recyclerViewLayoutManager = new LinearLayoutManager(this);

        onItemClickListener = new AdapterNote.OnItemClickListener() {
            @Override
            public void onItemClick(Note note, int position) {
                Toast.makeText(getApplicationContext(),"Se selecciono una nota",Toast.LENGTH_SHORT).show();

                //menu.setHeaderTitle( notes.get(info.position).getDescripcion() );
                 //getMenuInflater().inflate(R.menu.contex_menu_note_activity, menu);
            }
        };

        //registerForContextMenu( recyclerView );

        adapter = new AdapterNote(notes, this, R.layout.list_view_note_item, onItemClickListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        fab = findViewById(R.id.fobAddNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingNote("Add new Note","Type a name for your Note for "+ board.getTitle());
            }
        });



        //registerForContextMenu();
    }


    /*
    Pop-Up
    */
    private void showAlertForCreatingNote( String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null)builder.setTitle(title);
        if(msg != null)builder.setMessage(msg);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note,null);
        builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteName =input.getText().toString().trim();

                if(noteName.length() > 0){
                    createNewNote(noteName);
                }else{
                    Toast.makeText(getApplicationContext(),"Es requerido un nombre",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"No se guarda nada",Toast.LENGTH_SHORT).show();
            }
        });

        //Lo crea y lo muestra
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.deleteAll:
                deleteAllNotes();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    private void createNewNote(String note){
        realm.beginTransaction();
        Note _note = new Note(note);
        realm.copyToRealm(_note);
        board.getNotes().add(_note);
        realm.commitTransaction();
    }

    /* Borra todas las notas de una board */
    private void deleteAllNotes(){
        realm.beginTransaction();
        board.getNotes().deleteAllFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void onChange(Board board) {
        adapter.notifyDataSetChanged();
    }
}
