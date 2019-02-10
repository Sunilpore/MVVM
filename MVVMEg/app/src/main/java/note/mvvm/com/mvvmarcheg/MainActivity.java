package note.mvvm.com.mvvmarcheg;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;
    FloatingActionButton buttonAddNote;
    ArrayList<String> arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddNote = findViewById(R.id.but_add_note);

        buttonAddNote.setOnClickListener(view->{
            Intent intent = new Intent(this,AddEditNoteActivity.class);
            startActivityForResult(intent,ADD_NOTE_REQUEST);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter =new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //In this way ViewModel get to know which lifecycle it has to be scoped now
        //Android System destroy this ViewModel once this activity get finished
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        //It is a live data method and it is a life cycle aware, means its only update the activity when it is in foreground
        //When our activity destroy it is automatically clean up the refrence to the activity and this way we avoid our memory leak problem.
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {

            //It will only called when our activity is on foreground and update Live data
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                //Update Recyclerview here
                adapter.setNotes(notes);
             }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(note -> {
            Intent i = new Intent(MainActivity.this, AddEditNoteActivity.class);

            i.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());
            i.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.getTitle());
            i.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note.getDescription());
            i.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,note.getPriority());
            startActivityForResult(i,EDIT_NOTE_REQUEST);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,0);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            
            if(id == -1){
                Toast.makeText(this, "Not can't ", Toast.LENGTH_SHORT).show();
                return;
            }


            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,0);

            Note note = new Note(title,description,priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Not updated Sucessfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu);*/

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


/*
Why ViewModel ?????
If we restore the data in activity or fragment using thier Callback methods when it again call Oncreate e.g. If we change screen orientation
This is useful during restroing small amount of data, but it would be problematic tedious process
But when any Asynchronous operation in activity/fragment you have to ensure that it would be start and restart in correct Life-cycle,
 otherwise it will holds objects in the memory that you don't needed cause a problem called as a memory leakage

Solution:-
  Putting the data into the ViewModel it dosen't get lost and It doesn't interrupting anything when any configuration changes happened

  ViewModel is only removed from the memory when corresponding activity/fragment lifecycle is over ie. on activity finish or fragment dettached

*/


