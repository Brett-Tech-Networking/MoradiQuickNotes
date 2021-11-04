package com.moradi.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNotesActivity extends AppCompatActivity {

    EditText title, description;
    Button addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        addNote = findViewById(R.id.addNote);

        addNote.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
                    DatabaseClass db = new DatabaseClass(AddNotesActivity.this);
                    db.addNotes(title.getText().toString(), description.getText().toString());

                    Intent intent = new Intent(AddNotesActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(AddNotesActivity.this, "Title & Description Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}