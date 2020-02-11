package com.example.reclyclerviewandrealm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reclyclerviewandrealm.adapters.AdapterBoard;
import com.example.reclyclerviewandrealm.models.Board;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>> {

    private Realm realm;
    private ListView listView;
    private AdapterBoard adapters;
    private RealmResults<Board> boards;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private AdapterBoard.OnItemClickListener onItemClickListener;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        //Configura que cambios en la lista Board genere un llamado al metodo "onChange"
        boards.addChangeListener(this);

        recyclerView = findViewById(R.id.recyclerViewBoard);
        recyclerViewLayoutManager = new LinearLayoutManager(this);


        onItemClickListener = new AdapterBoard.OnItemClickListener() {
            @Override
            public void onItemClick(Board board, int position) {
                Intent intent = new Intent(BoardActivity.this, NoteActivity.class);
                intent.putExtra("id",boards.get(position).getId());
                startActivity(intent);
            }
        };

        recyclerViewAdapter = new AdapterBoard(boards, R.layout.list_view_board_item, onItemClickListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        fab = findViewById(R.id.fobAddBoard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Add new Board","Type a name for your board");
            }
        });
    }

    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }
    /* Borra una Board de la DB*/
    private void deleteBoard( Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }

    private void editBoard(String name, Board board){
        realm.beginTransaction();
        board.setTitle(name);
        realm.copyToRealm(board);
        realm.commitTransaction();
    }
    /*
    Pop-Up
    */
    private void showAlertForCreatingBoard( String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(title != null)builder.setTitle(title);
        if(msg != null)builder.setMessage(msg);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board,null);
        builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName =input.getText().toString().trim();

                if(boardName.length() > 0){
                    createNewBoard(boardName);
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


    /* Evento */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAll:
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                return true;
             default:
                 return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
