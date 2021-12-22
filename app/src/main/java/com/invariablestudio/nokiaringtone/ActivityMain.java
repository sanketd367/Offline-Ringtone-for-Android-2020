package com.invariablestudio.nokiaringtone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class ActivityMain extends AppCompatActivity implements JcPlayerManagerListener {

    public static InterstitialAd interstitialAd;
    public static AdapterRingtone dialogRecyclerView;
    public static int currentPlayItem = -1;
    static ActivityMain activityMainThis;
    public RecyclerView recyclerview;
    String TAG = "ActivityMain";
    LinearLayout adContainer;
    JcPlayerView jcplayerView;
    ImageView bg_shadow_IV;

    ArrayList<JcAudio> fullArrayList = new ArrayList();
    ArrayList<JcAudio> singleArrayList = new ArrayList();
    private AdView bannerAdView;

    public static void shareApp(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getResources().getString(R.string.share_text));
        stringBuilder.append(" ");
        stringBuilder.append(context.getResources().getString(R.string.app_name));
        stringBuilder.append("\n\n");
        stringBuilder.append(context.getResources().getString(R.string.share_link));
        stringBuilder.append(context.getApplicationContext().getPackageName());
        String stringBuilder2 = stringBuilder.toString();
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", stringBuilder2);
        context.startActivity(intent);
    }

    public static void showDialog(Context context, String str, String str2, String str3, @Nullable DialogInterface.OnClickListener onClickListener, @Nullable String str4, @Nullable DialogInterface.OnClickListener onClickListener2) {
        Builder builder = new Builder(context);
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
    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.privacy_policy) {
            privacyDialog();
            return true;
        } else if (itemId != R.id.share_app) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            shareApp(this);
            return true;
        }
    }*/

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
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.main);
        CONST.PACKAGE_NAME = getApplication().getPackageName();
        activityMainThis = this;
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //toolbar.setTitle((CharSequence) getString(R.string.app_name));
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_toolbar_back));
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ActivityMain.this.onBackPressed();
                }
            });
            setSupportActionBar(toolbar);
        }*/
        this.bannerAdView = new AdView(this);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId(getString(R.string.bannerAdId));
        this.adContainer = (LinearLayout) findViewById(R.id.adContainer);
        this.bg_shadow_IV = findViewById(R.id.bg_shadow_IV);
        this.adContainer.addView(this.bannerAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        this.bannerAdView.loadAd(adRequest);
        this.bannerAdView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int adError) {
                AdRequest adRequest = new AdRequest.Builder().build();
                bannerAdView.loadAd(adRequest);
            }

            @Override
            public void onAdLoaded() {
                ActivityMain.this.adContainer.setVisibility(View.VISIBLE);
            }
        });


        interstitialAd = new InterstitialAd(this);

        interstitialAd.setAdUnitId(getString(R.string.interstitialAdId));
        interstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }


        });
        interstitialAd.loadAd(new AdRequest.Builder().build());


        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.dialogRecyclerView = new AdapterRingtone(this, CONST.getRingtone(this));


        dialogRecyclerView.setOnItemClickListener(new AdapterRingtone.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                currentPlayItem = position;

               /* singleArrayList.clear();
                singleArrayList.add(fullArrayList.get(position));*/

                //jcplayerView.initPlaylist(singleArrayList, ActivityMain.this);

                jcplayerView.playAudio(jcplayerView.getMyPlaylist().get(currentPlayItem));

                int height = jcplayerView.getLayoutParams().height;
                bg_shadow_IV.getLayoutParams().height = height;

            }

            @Override
            public void onSongItemDeleteClicked(int position) {

            }
        });

        fullArrayList = CONST.getRingtoneJC(this);

        for (int pos = 0; pos < fullArrayList.size(); pos++) {
            singleArrayList.add(fullArrayList.get(pos));
        }


        jcplayerView = (JcPlayerView) findViewById(R.id.jcplayerView);

        jcplayerView.initPlaylist(singleArrayList, this);

        this.recyclerview.setAdapter(this.dialogRecyclerView);
        setToolBarClick();

    }

    public void setToolBarClick() {
        findViewById(R.id.back_bt).setOnClickListener(V -> onBackPressed());
        findViewById(R.id.i_bt).setOnClickListener(V -> privacyDialog());
        findViewById(R.id.share_bt).setOnClickListener(V -> shareApp(this));

    }

    @Override
    protected void onPause() {
        super.onPause();
        jcplayerView.createNotification(R.drawable.music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            CONST.userCount = 1;
        }
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                    CONST.userCount = 1;
                    ActivityMain.this.finish();
                    ActivityMain.this.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                }
            });
            return;
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    void privacyDialog() {
        showDialog(this, getResources().getString(R.string.disclaimer_title), getResources().getString(R.string.disclaimer_msg), getResources().getString(R.string.disclaimer_ok), null, null, null);
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

                    AudioStream.getInstance().assignRingtoneToContact(ActivityMain.this, contactId, contactName);
                }
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
                e.getLocalizedMessage();
            }
        } else if (i == CONST.REQUEST_FOR_WRITE_SETTINGS && System.canWrite(this)) {
            new SetTone(this).execute();
        }
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
        currentPlayItem = -1;
        dialogRecyclerView.notifyDataSetChanged();
        jcplayerView.createNotification(R.drawable.music);
    }


    @Override
    public void onStopped(JcStatus jcStatus) {

    }
}
