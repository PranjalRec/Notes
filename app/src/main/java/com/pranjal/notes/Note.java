package com.pranjal.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_Table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    public String description;

    public String date;


    public Note(String title,String description, String date){
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public String getDate(){
        return date;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
