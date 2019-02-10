package note.mvvm.com.mvvmarcheg;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version=1 )
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //Synchronized allow only one thread to access the method at a time
    //If we accidentally created two instances of database and two different thread try to access methods then this synchronized avoid it and allow only one thread at a time
    public static synchronized NoteDatabase getInstance(Context context){

        if(instance== null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }


    //It will provide cache mechanism
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        //It will created Only once when Database is created
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(instance).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {

        private NoteDao noteDao;

        public PopulateDbAsync(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Descrption 1",1));
            noteDao.insert(new Note("Title 2","Descrption 2",2));
            noteDao.insert(new Note("Title 3","Descrption 3",3));
            return null;
        }
    }

}

// If we change the version no of database then i=gnerally SQLite throw 'Illegal State Exception'
//Hence using fallbackToDestructiveMigration() we can avoid it
//It will check if version no changes -> delete older DB -> create new One

