package com.invariablestudio.nokiaringtone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.invariablestudio.nokiaringtone.Utils.DataBasHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements JcPlayerManagerListener {
    private static final String TAG = "dayay-->";
    public RecyclerView recyclerview;
    List<GSRingtone> gsRingtones;
    private Cursor cursor;
    JcPlayerView jcplayerView;
   // ImageView bg_shadow_IV;
    public static AdapterFavourite dialogRecyclerView;
    static FavouriteActivity activityMainThis;
    public static int currentPlayItem = -1;
    ArrayList<JcAudio> fullArrayList = new ArrayList();
    ArrayList<JcAudio> singleArrayList = new ArrayList();
    TextView tv_nodata;
    RelativeLayout rlPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        CONST.PACKAGE_NAME = getApplication().getPackageName();
        activityMainThis = this;
        gsRingtones = new ArrayList<>();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
       // this.bg_shadow_IV = findViewById(R.id.bg_shadow_IV);
        this.tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        this.rlPlayer = (RelativeLayout) findViewById(R.id.rl_player);

        gsRingtones.clear();
        cursor = new DataBasHelper(getApplicationContext()).retriveData();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String id = cursor.getString(0);
            String showName = cursor.getString(1);
            String rowName = cursor.getString(2);
            String myName = cursor.getString(3);

            gsRingtones.add(new GSRingtone(rowName, showName, myName));
            cursor.moveToNext();

        }
        if (gsRingtones.size() > 0) {
            tv_nodata.setVisibility(View.GONE);
            rlPlayer.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(FavouriteActivity.this, RecyclerView.VERTICAL, false);
            recyclerview.setLayoutManager(layoutManager);
            dialogRecyclerView = new AdapterFavourite(FavouriteActivity.this, gsRingtones,tv_nodata,rlPlayer);
            recyclerview.setAdapter(dialogRecyclerView);

            dialogRecyclerView.setOnItemClickListener(new AdapterFavourite.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                    currentPlayItem = position;
                    jcplayerView.playAudio(jcplayerView.getMyPlaylist().get(currentPlayItem));
                    int height = jcplayerView.getLayoutParams().height;
               //     bg_shadow_IV.getLayoutParams().height = height;

                }

                @Override
                public void onSongItemDeleteClicked(int position) {

                }
            });


            fullArrayList = CONST.getRingtoneJC(this);

            for (int i = 0; i < gsRingtones.size(); i++) {
                for (int j = 0; j < fullArrayList.size(); j++) {
                    if (fullArrayList.get(j).getTitle().equals(gsRingtones.get(i).MY_NAME)) {
                        singleArrayList.add(fullArrayList.get(j));
                    }
                }
            }


            jcplayerView = (JcPlayerView) findViewById(R.id.jcplayerView);

            jcplayerView.initPlaylist(singleArrayList, this);
        } else {
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
            tv_nodata.setVisibility(View.VISIBLE);
            rlPlayer.setVisibility(View.GONE);
        }
        setToolBarClick();
    }

    public static void showDialog(Context context, String str, String str2, String str3, @Nullable DialogInterface.OnClickListener onClickListener, @Nullable String str4, @Nullable DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (str2 != null) {
            builder.setTitle((CharSequence) str);
        }
        if (str2 != null) {
            builder.setMessage((CharSequence) str2);
        }
        if (str3 != null) {
            builder.setPositiveButton((CharSequence) str3, onClickListener);
        }
        if (str4 != null) {
            builder.setNegativeButton((CharSequence) str4, onClickListener2);
        }
        builder.create().show();
    }

    public static void openDialogForPermission() {
        showDialog(activityMainThis, activityMainThis.getResources().getString(R.string.permission_title), activityMainThis.getResources().getString(R.string.permission_msg), activityMainThis.getResources().getString(R.string.permission_positive), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityMain.GrantAllPermissions();
            }
        }, null, null);
    }

    public static void GrantAllPermissions() {
        ActivityCompat.requestPermissions(activityMainThis, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS"}, 101);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gsRingtones.size() > 0) {
            jcplayerView.createNotification(R.drawable.music);
        }
    }

    public void setToolBarClick() {
        findViewById(R.id.back_bt).setOnClickListener(V -> onBackPressed());


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onCompletedAudio() {
        currentPlayItem = -1;
        dialogRecyclerView.notifyDataSetChanged();
    }

    @Override
    public void onContinueAudio(JcStatus jcStatus) {

    }

    @Override
    public void onJcpError(Throwable throwable) {

    }

    @Override
    public void onPaused(JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(JcStatus jcStatus) {

    }

    @Override
    public void onPreparedAudio(JcStatus jcStatus) {

        Log.e(TAG, "onPreparedAudio: " + jcStatus.getCurrentPosition());
        Log.e(TAG, "onPreparedAudio: " + jcStatus.getJcAudio().getPosition());

        currentPlayItem = jcStatus.getJcAudio().getPosition();
        dialogRecyclerView.updateAdapter(currentPlayItem);
    }

    @Override
    public void onTimeChanged(JcStatus jcStatus) {

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: " );
                jcplayerView.pause();
                jcplayerView.onStopped(jcStatus);
                jcStatus.setPlayState(JcStatus.PlayState.STOP);
            }
        },jcStatus.getCurrentPosition());*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (gsRingtones.size() > 0) {
            currentPlayItem = -1;
            dialogRecyclerView.notifyDataSetChanged();
            jcplayerView.createNotification(R.drawable.music);
        }
    }


    @Override
    public void onStopped(JcStatus jcStatus) {

    }


    @TargetApi(23)
    public void permissionForWRITE_SETTINGS() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_SETTINGS") != 0) {
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(getPackageName());
            intent.setData(Uri.parse(stringBuilder.toString()));
            startActivityForResult(intent, CONST.REQUEST_FOR_WRITE_SETTINGS);
            return;
        }
        new SetTone(this).execute();
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 101) {
            return;
        }
        if (iArr[0] == 0 && iArr[1] == 0 && iArr[2] == 0) {
            permissionForWRITE_SETTINGS();
        } else {
            openDialogForPermission();
        }
    }

    @SuppressLint({"NewApi"})
    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == CONST.REQUEST_FOR_PICK_CONTACT && i2 == -1 && intent.getData() != null) {
            try {
                Cursor query = getContentResolver().query(intent.getData(), new String[]{"_id", "display_name"}, null, null, null);
                if (query != null) {
                    query.moveToFirst();
                    String contactId = query.getString(query.getColumnIndexOrThrow("_id"));
                    String contactName = query.getString(query.getColumnIndexOrThrow("display_name"));

                    AudioStream.getInstance().assignRingtoneToContact(FavouriteActivity.this, contactId, contactName);
                }
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
                e.getLocalizedMessage();
            }
        } else if (i == CONST.REQUEST_FOR_WRITE_SETTINGS && Settings.System.canWrite(this)) {
            new SetTone(this).execute();
        }
    }


}