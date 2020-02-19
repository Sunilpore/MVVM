package com.studentlog.dashboard.fragment;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.studentlog.model.Data;
import com.studentlog.model.local.Student;
import com.studentlog.room.LocalDatabase;
import com.studentlog.room.StudentDao;
import com.studentlog.utils.LogHelper;

import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StudentFormVM extends ViewModel {

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private MutableLiveData<Data<Long>> userInserResult = new MutableLiveData<>();


    LiveData<Data<Long>> getInserResult(){
        return userInserResult;
    }


    void createStudent(String name, String rollNo, String std, String mobNo, String email, String pass){

        mDisposable.add( LocalDatabase.getInstance().studentDao().
                insertStudentRx(new Student(name,rollNo,std,mobNo,email,pass))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    userInserResult.setValue(Data.success(result));
                }, throwable -> {
                    LogHelper.showLogData(throwable.getMessage());
                    userInserResult.setValue(Data.error(throwable.getMessage(),null));
                }));

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }



    private class InsertStudentAsyncTask extends AsyncTask<Student,Void,Void> {

        private StudentDao studentDao;

        InsertStudentAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudent(students[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userInserResult.setValue(Data.success(null));
        }
    }

}
