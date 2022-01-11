package com.invariablestudio.nokiaringtone;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SetTone extends AsyncTask<String, String, String> {
    private static final String TAG = "SetTone";
    private Activity _context;
    private File savedFile;

    SetTone(Activity context) {
        this._context = context;
    }

    @Override
    protected String doInBackground(String... strArr) {


        int id = _context.getResources().getIdentifier(_context.getPackageName()+":raw/"+CONST.SELECTED_RAW_FILE_NAME, null, null);
        Log.i(TAG, "doInBackground: "+id);
        byte[] buffer = null;
        InputStream fIn = _context.getResources().openRawResource(id);
        int size = 0;

        try {
            size = fIn.available();
            buffer = new byte[size];
            fIn.read(buffer);
            fIn.close();
        } catch (final IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            return CONST.FAIL_TASK;
        }

        String path = AudioStream.getInstance().getRingtonePath(_context).getAbsolutePath();
        String filename = "temp";

        if (CONST.SELECTED_SHOW_FILE_NAME.contains(".mp3")) {
            filename = CONST.SELECTED_SHOW_FILE_NAME;
        } else {
            filename = CONST.SELECTED_SHOW_FILE_NAME + ".mp3";
        }


        boolean exists = (new File(path)).exists();
        if (!exists) {
            new File(path).mkdirs();
        }

        FileOutputStream save;
        try {
            save = new FileOutputStream(path + "/" + filename);
            save.write(buffer);
            save.flush();
            save.close();
        } catch (final FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException: " + e.getMessage());
            return CONST.FAIL_TASK;
        } catch (final IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            return CONST.FAIL_TASK;
        }


        savedFile = new File(path + "/" + filename);


        return CONST.SUCCESS_TASK;

    }

    @Override
    protected void onPostExecute(String mTask) {

        String str2 = CONST.SELECTED_SHOW_FILE_NAME;
        int i = CONST.SELECTED_TYPE;
        StringBuilder stringBuilder;


        switch (i) {
            case 1:
                AudioStream.getInstance().setRingtone(_context, CONST.POS);
                return;
            case 2:
                AudioStream.getInstance().setNotification(_context, CONST.POS);
                return;
            case 4:
                AudioStream.getInstance().setAlarm(_context, CONST.POS);
                return;
            case 104:
                ((ActivityMain) this._context).startActivityForResult(new Intent("android.intent.action.PICK", Contacts.CONTENT_URI), CONST.REQUEST_FOR_PICK_CONTACT);
                return;
            case 105:

                if (mTask.equals(CONST.SUCCESS_TASK)) {
                    Uri mediaUri = FileProvider.getUriForFile(_context, _context.getPackageName() + ".provider", savedFile);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("audio/mp3");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra("android.intent.extra.STREAM", mediaUri);


                    Intent chooser = Intent.createChooser(intent, CONST.SHARE_AUDIO_TITLE);
                    List<ResolveInfo> resInfoList = _context.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        _context.grantUriPermission(packageName, mediaUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }

                    this._context.startActivity(chooser);
                } else {
                    final Activity activity = _context;

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Something Want Wrong...", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                return;
        }

    }
}
