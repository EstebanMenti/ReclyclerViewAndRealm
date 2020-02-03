package com.example.reclyclerviewandrealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reclyclerviewandrealm.models.Board;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private List<Board> list;
    private int layout;

    private OnItemClickListener itemClickListener;

    public MyAdapter(Context context, List<Board> list, int layout, OnItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
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
        public TextView textViewName;
        public ImageView imageViewPoster;

        TextView title;
        TextView notes;
        TextView createAt;

        public ViewHolder(View view){
            super(view);
            //this.textViewName = view.findViewById(R.id.textViewName);
            this.title = view.findViewById(R.id.textViewBoardTitle);
            this.notes = view.findViewById(R.id.textViewBoardNotes);
            this.createAt = view.findViewById(R.id.textViewBoardDate);
        }

        public void bind(final Board board, final OnItemClickListener listener){
            //this.textViewName.setText(name);



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
