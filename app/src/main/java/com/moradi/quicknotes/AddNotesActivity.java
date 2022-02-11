package com.moradi.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AddNotesActivity extends AppCompatActivity {

    EditText title, description;
    Button addNote;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        //TESTING this is for "done" button on add note screen
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_baseline_check_24);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);

        //Title Desc & add note
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        addNote = findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
                    DatabaseClass db = new DatabaseClass(AddNotesActivity.this);
                    db.addNotes(title.getText().toString(), description.getText().toString());
                    Intent intent = new Intent(AddNotesActivity.this, MainActivity.class);
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
    // function to the button on back button press top left
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //execute auto save when back is clicked
        if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
            DatabaseClass db = new DatabaseClass(AddNotesActivity.this);
            db.addNotes(title.getText().toString(), description.getText().toString());

            Intent intent = new Intent(AddNotesActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(AddNotesActivity.this, "Title & Description Required", Toast.LENGTH_SHORT).show();
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
            DatabaseClass db = new DatabaseClass(AddNotesActivity.this);
            db.addNotes(title.getText().toString(), description.getText().toString());

            Intent intent = new Intent(AddNotesActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(AddNotesActivity.this, "Title & Description Required", Toast.LENGTH_SHORT).show();
        }
    }
}