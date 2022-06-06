package com.pranjal.notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public static NoteDatabase instance;

    public abstract NoteDao noteDao();




    public static synchronized NoteDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            NoteDao noteDao = instance.noteDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
                    String date = simpleDateFormat.format(cal.getTime());


                    noteDao.insert(new Note("title 1","description 1",date));
                    noteDao.insert(new Note("title 2","description 2",date));
                    noteDao.insert(new Note("title 3","description 3",date));
                    noteDao.insert(new Note("title 4","description 4",date));
                    noteDao.insert(new Note("title 5","description 5",date));
                }
            });
        }
    };

}
