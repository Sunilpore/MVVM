package note.mvvm.com.mvvmarcheg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "note.mvvm.com.mvvmarcheg.extra_id";
    public static final String EXTRA_TITLE = "note.mvvm.com.mvvmarcheg.extra_title";
    public static final String EXTRA_DESCRIPTION = "note.mvvm.com.mvvmarcheg.extra_desciption";
    public static final String EXTRA_PRIORITY = "note.mvvm.com.mvvmarcheg.extra_priority";

    private EditText mTitle;
    private EditText mDescription;
    private NumberPicker mNumPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mTitle = findViewById(R.id.edit_title);
        mDescription = findViewById(R.id.edit_description);
        mNumPicker = findViewById(R.id.edit_num_picker);

        mNumPicker.setMinValue(1);
        mNumPicker.setMaxValue(20);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            mTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            mDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mNumPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));
        }else{
            setTitle("Add note");
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = mTitle.getText().toString();
        String description = mDescription.getText().toString();
        int priority = mNumPicker.getValue();

        if(TextUtils.isEmpty(title.trim()) && TextUtils.isEmpty(description.trim())){
            Toast.makeText(this, "Please insert Title and Description", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id !=-1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }


}
