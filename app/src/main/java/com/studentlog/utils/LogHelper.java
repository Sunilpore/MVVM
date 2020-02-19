package com.studentlog.utils;

import android.util.Log;

public class LogHelper {

    private static final String TAGData = "myTag";
    private static final String TAGContextData = "myTag_context";
    private static final String TAGError = "ErrorTag";

    /**
     * print error log
     */
    public static void printError(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * print error Debug msg
     */
    public static void printDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void showLogData(String message) {
        Log.d(TAGData, message);
    }

    public static void showLogError(String message) {
        Log.d(TAGError, "ErrorModel: " + message);
    }

}
