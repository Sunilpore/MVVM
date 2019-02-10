package note.mvvm.com.mvvmarcheg;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData <List<Note>> allNotes;

    public NoteRepository(Application application) {

        NoteDatabase noteDb = NoteDatabase.getInstance(application);
        noteDao = noteDb.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    //This are in short our API to use Database call/Network Operation

    public void insert(Note note){
        new InsertNotesAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNotesAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNotesAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }


    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }


    //AsyncTask to initialize RoomDb
    //Initialize AsyncTask as a static so it does not have refrence to the Repository
    //Hence it avoid memory leakage problem

    private static class InsertNotesAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDao noteDao;

        InsertNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNotesAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDao noteDao;

        UpdateNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNotesAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDao noteDao;

        DeleteNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDao noteDao;

        DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }


}
