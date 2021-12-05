package com.yourbrandname.iphoneringtone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.io.File;

public class AudioStream {

    private static AudioStream audioStream;

    public static synchronized AudioStream getInstance() {
        if (audioStream == null) {
            audioStream = new AudioStream();
        }
        return audioStream;
    }

    public File getRingtonePath(Activity activity) {
        ContextWrapper wrapper = new ContextWrapper(activity);
        File file = new File(wrapper.getExternalFilesDir(Environment.DIRECTORY_RINGTONES), activity.getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public void assignRingtoneToContact(Activity activity, String contactId, String contactName) {

        Uri uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + CONST.rawArray[CONST.POS]);

        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contacts.CUSTOM_RINGTONE, uri.toString());
        activity.getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values, "_id=" + contactId, null);
        showToast(activity, "Ringtone set on " + contactName);
    }

    public void setRingtone(Activity activity, int position) {
        Uri uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + CONST.rawArray[position]);
        RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_RINGTONE, uri);
        showToast(activity, "Ringtone set Success");

    }

    public void setNotification(Activity activity, int position) {
        Uri uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + CONST.rawArray[position]);
        RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_NOTIFICATION, uri);
        showToast(activity, "Notification set Success");

    }

    public void setAlarm(Activity activity, int position) {
        Uri uri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + CONST.rawArray[position]);
        RingtoneManager.setActualDefaultRingtoneUri(activity, RingtoneManager.TYPE_ALARM, uri);
        showToast(activity, "Alarm set Success");
    }


    private void showToast(final Activity activity, final String msg) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
    }


}
