package com.studentlog;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class BaseApp extends Application {

    private static WeakReference<BaseApp> mInstance = new WeakReference<>(null);

    public static Context getContext() {
        return mInstance.get().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance.clear();
        mInstance = new WeakReference<>(this);

    }



}
