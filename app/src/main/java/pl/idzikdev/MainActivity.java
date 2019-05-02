package pl.idzikdev;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.idzikdev.data.MyDatabase;
import pl.idzikdev.mapper.MyAdapter;
import pl.idzikdev.model.Note;

public class MainActivity extends AppCompatActivity {

    MyDatabase database;
    List<Note> listOfNotes;
    MyAdapter adapter;


    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.countInfo)
    TextView countInfo;

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    public void clickAdd() {
        Intent intent = new Intent(MainActivity.this, NotesActivity.class);
        startActivityForResult(intent, 1);

    }

    public void refreshNotes() {
        if (adapter!=null)adapter.clear();
        listOfNotes.clear();
        Cursor cursor = database.getAll();
        while (cursor.moveToNext()) {
            listOfNotes.add(new Note(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        Note[] notes = new Note[listOfNotes.size()];
        listOfNotes.toArray(notes);
        adapter=new MyAdapter(this,listOfNotes);
        listView.setAdapter(adapter);
        if (listOfNotes.size()>0) countInfo.setText(R.string.itemsTrue);
        else countInfo.setText(R.string.itemsFalse);
    }

    public void clickAll(){
        Cursor cursor=database.getAll();
        while (cursor.moveToNext()){
                Log.w("NAME",cursor.getString(1));
                Log.w("DATE",cursor.getString(2));
                Log.w("CATEGORY",cursor.getString(3));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        fab.setImageResource(R.drawable.ic_note_add_24dp);
        database=new MyDatabase(this);
        listOfNotes=new ArrayList<>();
        refreshNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            StringBuilder message = new StringBuilder("");
            String name = data.getStringExtra("name");
            String date = data.getStringExtra("date");
            String category = data.getStringExtra("category");
            message
                    .append(name).append(" ")
                    .append(date).append(" ")
                    .append(category);
            Snackbar.make(coordinatorLayout, R.string.success, Snackbar.LENGTH_LONG).show();
            if (database.registerNote(name,date,category))
            refreshNotes();
            else  showDialog();
        }
        else{
              showDialog();
        }
    }

    public void showDialog(){
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("ERROR!")
                .setMessage("Can not save your task. Try again")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       clickAdd();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeAll(MenuItem item) {
        database.deleteAll();
        refreshNotes();
    }
}
