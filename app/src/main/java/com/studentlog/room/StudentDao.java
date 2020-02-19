package com.studentlog.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.studentlog.model.local.Student;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudent(Student student);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertStudentRx(Student student);

    @Query("SELECT * FROM student_table")
    LiveData<List<Student>> getAllStudents();

    @Delete
    void deleteStudent(Student student);

    @Delete
    Maybe<Integer> deleteStudentRx(Student student);

}
