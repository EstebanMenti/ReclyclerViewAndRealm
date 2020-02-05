package com.example.reclyclerviewandrealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    private int layout;
    private AdapterNote.OnItemClickListener itemClickListener;


    public AdapterNote(Context context, List<Note> list, int layout, AdapterNote.OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        AdapterNote.ViewHolder viewHolder = new AdapterNote.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNote.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView description;
        public TextView createAt;

        public ViewHolder(View view){
            super(view);
            this.description = view.findViewById(R.id.textViewNoteDescreption);
            this.createAt = view.findViewById(R.id.textViewNoteCreateAt);
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
}
