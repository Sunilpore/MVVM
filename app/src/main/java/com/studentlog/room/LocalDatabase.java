package com.studentlog.room;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.studentlog.BaseApp;
import com.studentlog.model.local.Student;

@Database(entities = {Student.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase mIinstance;

    public abstract StudentDao studentDao();

    public static synchronized LocalDatabase getInstance(){
        if(mIinstance==null){
            mIinstance = Room.databaseBuilder(BaseApp.getContext(),
                    LocalDatabase.class, "student_db")
                    .fallbackToDestructiveMigration()
                    .setJournalMode(JournalMode.TRUNCATE)
                    .build();
        }
        return mIinstance;
    }

}
