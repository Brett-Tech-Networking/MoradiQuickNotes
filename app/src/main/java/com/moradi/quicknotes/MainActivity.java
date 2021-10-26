package com.moradi.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Adapter adapter;
    List<Model> notesList;
    DatabaseClass databaseClass;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setTheme(R.style.LightTheme);
        setContentView(R.layout.nav_activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
        coordinatorLayout = findViewById(R.id.layout_main);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotesActivity.class);
                startActivity(intent);
            }

        });

        notesList = new ArrayList<>();
        databaseClass = new DatabaseClass(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }


    void fetchAllNotesFromDatabase() {
        Cursor cursor = databaseClass.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Currently No Notes", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                notesList.add(new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
// Search notes conf
        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Notes Here");

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        };
        searchView.setOnQueryTextListener(listener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_all_notes) {
            deleteAllNotes();
        }
       else
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;
        }
        else
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        DatabaseClass db = new DatabaseClass(MainActivity.this);
        db.deleteAllNotes();
        recreate();
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Model item = adapter.getList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Note Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION)) {
                                DatabaseClass db = new DatabaseClass(MainActivity.this);
                                db.deleteSingleItem(item.getId());
                            }


                        }
                    });


            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }

    };
    public void webButton(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/RiverRougeCOGOP"));
        startActivity(browserIntent);
    }

}