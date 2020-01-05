package com.master.snapshotwizard;

import android.app.Activity;

public class Log {

    public static final String TAG = "SNAPSHOTWIZARD";

    public static String getClassName(Class a){
        return a.getSimpleName();
    }

    public static String getClassName(Activity a){
        return a.getLocalClassName();
    }


    public static String getClassName(Object o){
        return o.getClass().getSimpleName();
    }


    public static String getMethodName(){
        return new Throwable().getStackTrace()[2].getMethodName();
    }

    public static String appendTagWithClass(String className){
        return TAG + "|" + className;
    }

    public static void d (Activity caller, String message){
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d( tag, message);
    }

    public static void d (Activity caller){
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d( tag, getMethodName());
    }

    public static void d (Object caller){
        String tag = appendTagWithClass(getClassName(caller));
        android.util.Log.d( tag, getMethodName());
    }


    public static void e (Activity caller){
        android.util.Log.e( appendTagWithClass(getClassName(caller)), getMethodName());
    }


    public static void e (Activity caller, String message){
        android.util.Log.e(appendTagWithClass(getClassName(caller)), message);
    }

    public static void d (Class caller, String message){
        android.util.Log.d( appendTagWithClass(getClassName(caller)), message);
    }

    public static void d (Object caller, String message){
        android.util.Log.d( appendTagWithClass(getClassName(caller)), message);
    }


    public static void e (Class caller, String message){
        android.util.Log.e(appendTagWithClass(getClassName(caller)), message);
    }
}
