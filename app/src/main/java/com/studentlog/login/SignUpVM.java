package com.studentlog.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.studentlog.model.Data;
import com.studentlog.model.local.Student;
import com.studentlog.room.LocalDatabase;
import com.studentlog.room.StudentDao;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SignUpVM extends ViewModel {

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private MutableLiveData<Data<Long>> signupSuccess = new MutableLiveData<>();

    public LiveData <Data<Long>> getInserResult(){
        return signupSuccess;
    }

    public void signUpUser(String userName, String password, String mobileNo){

        mDisposable.add( LocalDatabase.getInstance().studentDao().
                insertStudentRx(new Student(userName,password,mobileNo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    signupSuccess.setValue(Data.success(result));
                }));



                    /*.subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Long result) {
                        signupSuccess.setValue(Data.success(null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        signupSuccess.setValue(Data.error(Objects.requireNonNull(e.getMessage()),null));
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }



}
