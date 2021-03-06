package com.master.browsingbutler.utils;

import android.app.Activity;

public class Log {

    private static final String TAG = "BROWSINGBUTLER";

    private static String getClassName(Class a) {
        return a.getSimpleName();
    }

    private static String getClassName(Activity a) {
        return a.getLocalClassName();
    }

    private static String getClassName(Object o) {
        return o.getClass().getSimpleName();
    }

    private static String getMethodName() {
        return new Throwable().getStackTrace()[2].getMethodName();
    }

    private static String appendTagWithClass(String className) {
        return TAG + "|" + className;
    }

    private static String appendTag() {
        return TAG + "|";
    }

    public static void d(Activity caller, String message) {
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d(tag, message);
    }

    static void d(Activity caller) {
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d(tag, getMethodName());
    }

    public static void d(Object caller) {
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d(tag, getMethodName());
    }

    public static void d(String text) {
        android.util.Log.d(appendTag(), text);
    }

    public static void e(Activity caller) {
        android.util.Log.e(appendTagWithClass(getClassName(caller)), getMethodName());
    }

    public static void e(Activity caller, String message) {
        android.util.Log.e(appendTagWithClass(getClassName(caller)), message);
    }

    public static void d(Class caller, String message) {
        android.util.Log.d(appendTagWithClass(getClassName(caller)), message);
    }

    public static void d(Object caller, String message) {
        android.util.Log.d(appendTagWithClass(getClassName(caller)), message);
    }

    public static void d(String tag, String message) {
        android.util.Log.d(appendTagWithClass(tag), message);
    }

    public static void e(Class caller, String message) {
        android.util.Log.e(appendTagWithClass(getClassName(caller)), message);
    }
}
