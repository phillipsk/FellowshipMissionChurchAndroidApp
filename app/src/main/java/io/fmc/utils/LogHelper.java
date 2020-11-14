package io.fmc.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by  Kevin Phillips and Sunday Akinsete on 05/04/2018.
 */

public class LogHelper {

    public static void e(@NonNull String tag, @NonNull Object message) {
        Log.e(tag, String.valueOf(message));
    }
}