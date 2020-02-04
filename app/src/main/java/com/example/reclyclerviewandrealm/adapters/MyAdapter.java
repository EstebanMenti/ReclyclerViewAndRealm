package com.example.reclyclerviewandrealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reclyclerviewandrealm.R;
import com.example.reclyclerviewandrealm.models.Board;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private List<Board> list;
    private int layout;

    private OnItemClickListener itemClickListener;

    public MyAdapter(List<Board> list, int layout, OnItemClickListener itemClickListener) {
        this.list = list;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView notes;
        public TextView createAt;
        private FloatingActionButton fab;

        public ViewHolder(View view){
            super(view);
            this.title = view.findViewById(R.id.textViewBoardTitle);
            this.notes = view.findViewById(R.id.textViewBoardNotes);
            this.createAt = view.findViewById(R.id.textViewBoardDate);
        }

        public void bind(final Board board, final OnItemClickListener listener){
            title.setText(board.getTitle());
            int numOfNote = board.getNotes().size();
            String textForNotes = (numOfNote == 1) ? "Note: " + numOfNote : "Notes: " + numOfNote;
            notes.setText(textForNotes);

            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            String formatDate = df.format(board.getCreateAt());
            createAt.setText(formatDate);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(board, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Board board, int position);
    }

}
