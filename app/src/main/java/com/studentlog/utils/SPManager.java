package com.studentlog.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.studentlog.BaseApp;

public class SPManager {

    private static SPManager mInstance;
    private static SharedPreferences sp;


    private SPManager(Context context) {
        if(sp == null){
            sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
    }

    public static synchronized SPManager getInstance(Context context) {
        return mInstance !=null ?mInstance : new SPManager(context);
    }


    //-----------------***------------------------//

    public void setLoginStatus(boolean value){
        sp.edit().putBoolean(Constants.USER_LOGIN_STATUS,value).apply();
    }

    public void setUserLoginName(String value){
        sp.edit().putString(Constants.USER_LOGIN_NAME, value).apply();
    }

    public void setUserLoginPass(String value){
        sp.edit().putString(Constants.USER_LOGIN_PASS, value).apply();
    }

    public void setUserLoginId(int value){
        sp.edit().putInt(Constants.USER_LOGIN_ID, value).apply();
    }

    //-----------------***------------------------//


    public boolean isLogin() {
        return sp.getBoolean(Constants.USER_LOGIN_STATUS,false);
    }

    public String getUserLoginName(){
        return sp.getString(Constants.USER_LOGIN_NAME,"");
    }

    public String getUserLoginPass(){
        return sp.getString(Constants.USER_LOGIN_PASS,"");
    }

    public int getUserLoginId(){
        return sp.getInt(Constants.USER_LOGIN_ID,0);
    }

}
