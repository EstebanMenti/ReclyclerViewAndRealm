package com.example.reclyclerviewandrealm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reclyclerviewandrealm.R;
import com.example.reclyclerviewandrealm.models.Board;
import com.example.reclyclerviewandrealm.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolder>{
    private Context context;
    private List<Note> list;
    Activity activity;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnOptionMenu optionMenu;


    public AdapterNote(List<Note> list, Activity activity, int layout, AdapterNote.OnItemClickListener itemClickListener, OnOptionMenu optionMenu) {
        this.list = list;
        this.layout = layout;
        this.activity = activity;
        this.itemClickListener = itemClickListener;
        this.optionMenu = optionMenu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        View view = LayoutInflater.from(activity).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView description;
        public TextView createAt;

        public ViewHolder(View view){
            super(view);
            this.description = view.findViewById(R.id.textViewNoteDescreption);
            this.createAt = view.findViewById(R.id.textViewNoteCreateAt);
            // Añadimos al view el listener para el context menu, en vez de hacerlo en
            // el activity mediante el método registerForContextMenu
           //view.setOnCreateContextMenuListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            menu.setHeaderTitle("Titulo: " /* + list.get(info.position).getDescripcion()*/); // ver la forma de poner algun titulo relacionado a la nota
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.contex_menu_note_activity, menu);

            // Por último, añadimos uno por uno, el listener onMenuItemClick para
            // controlar las acciones en el contextMenu, anteriormente lo manejábamos
            // con el método onContextItemSelected en el activity
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item)
        {
            int position = getAdapterPosition();

            switch (item.getItemId()) {
                case R.id.edit_Note:
                    optionMenu.onEditNote(list.get(position));
                    return true;
                case R.id.delete_Note:
                    optionMenu.onClearNote(list.get(position));
                return true;
                default:
                    return false;
            }
        }



        public void bind(final Note note, final AdapterNote.OnItemClickListener listener){

            description.setText(note.getDescripcion());
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            String formatDate = df.format(note.getCreateAt());
            createAt.setText(formatDate);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(note, getAdapterPosition());
                }
            });
        }

    }


    public interface OnItemClickListener{
        void onItemClick(Note note, int position);
    }

    public interface OnOptionMenu{
        void onClearNote(Note note);
        void onEditNote(Note note);
    }
}
