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
import android.text.Editable;
import android.text.TextWatcher;


public class UpdateNotesActivity extends AppCompatActivity {

    EditText title, description;
    Button updateNotes;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        updateNotes = findViewById(R.id.updateNote);

        Intent i = getIntent();
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
        id = i.getStringExtra("id");

        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {

                    DatabaseClass db = new DatabaseClass(UpdateNotesActivity.this);
                    db.updateNotes(title.getText().toString(), description.getText().toString(), id);

                    Intent i = new Intent(UpdateNotesActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(UpdateNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //execute auto save when back is clicked
        if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
            DatabaseClass db = new DatabaseClass(UpdateNotesActivity.this);
            db.updateNotes(title.getText().toString(), description.getText().toString(), id);
            Intent i = new Intent(UpdateNotesActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(UpdateNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
        }

        // go back to home
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Phone Back Button Press
    @Override
    public void onBackPressed() {
        //execute auto save when back is clicked
        if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
            DatabaseClass db = new DatabaseClass(UpdateNotesActivity.this);
            db.updateNotes(title.getText().toString(), description.getText().toString(), id);
            Intent i = new Intent(UpdateNotesActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(UpdateNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
        }
    }
}
