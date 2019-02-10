package note.mvvm.com.mvvmarcheg;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

/*Why we extend AndroidViewModel here ????
    You can't store the Context of an activity here and refrence of an activity here
    Because ViewModel is design to outlift an activity here after its destroy
    And if you hold the refrence of already destroy activity here, it would causes a memory leakage problem
    But we required context to initiallize our database instance and therefore here we extends AndroidViewModel and it passes an application and then to the Database
*/

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
