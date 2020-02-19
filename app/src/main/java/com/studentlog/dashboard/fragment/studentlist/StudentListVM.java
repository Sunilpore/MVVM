package com.studentlog.dashboard.fragment.studentlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.studentlog.model.Data;
import com.studentlog.model.local.Student;
import com.studentlog.room.LocalDatabase;
import com.studentlog.utils.LogHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StudentListVM extends ViewModel {

    private CompositeDisposable mDisposable = new CompositeDisposable();

    MutableLiveData<Data<Integer>> onDeleteRes = new MutableLiveData<>();

    public LiveData<Data<Integer>> onDeleteResObserver(){
        return onDeleteRes;
    }

    public void deleteStudent(Student student){

        mDisposable.add(LocalDatabase.getInstance().studentDao()
                        .deleteStudentRx(student)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                            onDeleteRes.setValue(Data.success(result));
                        }, throwable -> {
                            LogHelper.showLogData(throwable.getMessage());
                            onDeleteRes.setValue(Data.error(throwable.getMessage(),null));
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

}
