package io.fmc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Kevin Phillips and Sunday Akinsete on 5/26/16.
 */
public class Utilities {

    public static void showAlert(final Context context, Object message, final boolean b) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(String.valueOf(message))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (b) {
                            ((Activity) context).finish();
                        }

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        try {
            alertDialog.show();
        }catch (Exception w){

        }
    }

    public static long unixTimeStamp(){
        return System.currentTimeMillis() / 1000L;
    }

    public static void showToast(Context c, Object text){
        Toast.makeText(c, String.valueOf(text), Toast.LENGTH_LONG).show();
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }


    public static String getDeviceID(Context c) {
        return Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
