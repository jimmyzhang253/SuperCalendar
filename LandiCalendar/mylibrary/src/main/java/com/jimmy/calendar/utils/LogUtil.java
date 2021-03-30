package com.jimmy.calendar.utils;

import android.util.Log;

public class LogUtil {
    private static final String DEFAULT_TAG = "landi_log";
    public static boolean isDebug = true;

    // verbose
    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    // debug
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    // info
    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    // warning
    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    // error
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }
}
