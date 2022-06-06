package com.pranjal.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>{

    List<Note> notes = new ArrayList<>();

    private OnItemClickListner listner;


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        holder.textViewTitle.setText(notes.get(position).getTitle());
        holder.textViewDescription.setText(notes.get(position).getDescription());
        holder.textViewDate.setText(notes.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position){
        return notes.get(position);
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle,textViewDescription,textViewDate;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewTime);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listner != null && position != RecyclerView.NO_POSITION){
                        listner.onItemClick(notes.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListner{

        void onItemClick(Note note);
    }

    public void setOnItemClickListner(OnItemClickListner listner){

        this.listner = listner;

    }
}
