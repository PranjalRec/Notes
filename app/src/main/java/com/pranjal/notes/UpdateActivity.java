package com.pranjal.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {


    EditText editTextTitle;
    EditText editTextDescription;
    Button buttonCancel, buttonSave;

    String title,description,date;
    int id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Note");
        setContentView(R.layout.activity_update);


        editTextTitle = findViewById(R.id.editTextTitleUpdate);
        editTextDescription = findViewById(R.id.editTextDescriptionUpdate);
        buttonCancel = findViewById(R.id.buttonCancelUpdate);
        buttonSave = findViewById(R.id.buttonSaveUpdate);

        updateNote();

        editTextTitle.setText(title);
        editTextDescription.setText(description);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
        String date = simpleDateFormat.format(cal.getTime());






        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent();
                String titleLast = editTextTitle.getText().toString();
                String descriptionLast = editTextDescription.getText().toString();



                intent1.putExtra("titleLast",titleLast);
                intent1.putExtra("descriptionLast",descriptionLast);
                intent1.putExtra("date",date);

                if(id != -1){
                    intent1.putExtra("id",id);
                }
                setResult(RESULT_OK,intent1);
                finish();

            }
        });


    }

    void updateNote() {

        Intent i = getIntent();
        title = i.getStringExtra("title");
        description = i.getStringExtra("description");
        id = i.getIntExtra("id",-1);
    }


}