package com.pranjal.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;
    RecyclerView recyclerView;

    ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;
    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        registerActivityForUpdateNote();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update recycler view
                noteAdapter.setNotes(notes);


            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note deletedNote = noteAdapter.getNote(viewHolder.getAdapterPosition());
                noteViewModel.delete(noteAdapter.getNote(viewHolder.getAdapterPosition()));

                Snackbar.make(MainActivity.this,recyclerView,"To Undo click on Undo button",Snackbar.LENGTH_LONG)
                        .setAction("undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                noteViewModel.insert(deletedNote);
                            }
                        }).show();


            }
        }).attachToRecyclerView(recyclerView);



        noteAdapter.setOnItemClickListner(new NoteAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("title",note.getTitle());
                intent.putExtra("description",note.getDescription());
                intent.putExtra("id",note.getId());
                setResult(RESULT_OK);
                startActivityForResult(intent,2);


            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this,AddNote.class);
                startActivityForResult(intent,1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

//    public void registerActivityForUpdateNote(){
//
//        activityResultLauncherForUpdateNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
//                , new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//
//                        Toast.makeText(MainActivity.this, "in main", Toast.LENGTH_SHORT).show();
//                        int resultCode = result.getResultCode();
//                        Intent data1 = result.getData();
//                        if(resultCode == RESULT_OK && data1 != null){
//                            String title = data1.getStringExtra("titleLast");
//                            String description = data1.getStringExtra("descriptionLast");
//                            int id = data1.getIntExtra("id", -1);
//
//                            Note note1 = new Note(title,description);
//                            note1.setId(id);
//                            noteViewModel.update(note1);
//
//                        }
//                    }
//                });
//
//    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        String title,description;
        if(requestCode == 1 && resultCode == RESULT_OK && data !=  null){
            title = data.getStringExtra("title");
            description = data.getStringExtra("description");

            Note note = new Note(title,description);
            noteViewModel.insert(note);
        }

        if(requestCode == 2 && resultCode == RESULT_OK && data !=  null){
            title = data.getStringExtra("titleLast");
            description = data.getStringExtra("descriptionLast");
            int id = data.getIntExtra("id",-1);

            Note note = new Note(title,description);
            note.setId(id);
            noteViewModel.update(note);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show().create();

    }
}