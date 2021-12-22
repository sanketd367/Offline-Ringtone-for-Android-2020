package com.invariablestudio.nokiaringtone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.claucookie.miniequalizerlibrary.EqualizerView;

public class AdapterRingtone extends Adapter<ViewHolder> {
    public static List<GSRingtone> data;
    public static int currentpos = 0;
    public static GSRingtone gSRingtone;
    private static OnItemClickListener mListener;
    String TAG = "AdapterRingtone";
    ListViewHolder listViewHolder;
    List<GSRingtone> fullArrayList = new ArrayList<>();
    //public static String ringtone;
    private Context _context;
    private Activity activity;

    AdapterRingtone(Activity activity, List<GSRingtone> list) {
        this._context = activity.getBaseContext();
        this.data = list;
        this.activity = activity;
        fullArrayList = CONST.getRingtone(activity);
    }

    public void updateAdapter(int currentpos) {
        AdapterRingtone.currentpos = currentpos;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListViewHolder(((LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_ringtone, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        //Log.e("onBindViewHolder", "" + i);

        /*final FrameLayout.MarginLayoutParams marginLayoutParams = new
                FrameLayout.MarginLayoutParams(listViewHolder.itemView.getLayoutParams());*/


        gSRingtone = (GSRingtone) this.data.get(i);
        listViewHolder = (ListViewHolder) viewHolder;

        listViewHolder.header_title.setText(gSRingtone.MY_NAME);
        /*listViewHolder.equalizer.animateBars();
        listViewHolder.equalizer.setVisibility(View.VISIBLE);
        listViewHolder.btn_play.setImageResource(R.drawable.ic_play_btn);*/


       /*  if (i == currentpos) {


           if (ringtone.equals(data.get(i).RAW_FILE_NAME)) {

                //Log.e("equalizer", "equalizer");

                listViewHolder.equalizer.animateBars();
                listViewHolder.equalizer.setVisibility(View.VISIBLE);
                listViewHolder.btn_play.setImageResource(R.drawable.ic_pause_btn);


            }
        }*/

        listViewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null) {
                    //listViewHolder.equalizer.animateBars(); // Whenever you want to tart the animation
                    mListener.onItemClick(i);
                }

            }
        });

        OnClickListener anonymousClass1 = new OnClickListener() {
            public void onClick(View view) {


                if (mListener != null) {
                    mListener.onItemClick(i);
                }

//                currentpos = i;
//
//                notifyDataSetChanged();


            }
        };
        listViewHolder.relViewPlayButton.setOnClickListener(anonymousClass1);
        listViewHolder.btn_play.setOnClickListener(anonymousClass1);
        listViewHolder.btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                CONST.POS = i;
                CONST.SELECTED_RAW_FILE_NAME = fullArrayList.get(i).RAW_FILE_NAME;
                CONST.SELECTED_SHOW_FILE_NAME = fullArrayList.get(i).SHOW_FILENAME;
                Log.e(TAG, "onClick: position: " + i);
                Log.e(TAG, "onClick: name: " + CONST.SELECTED_RAW_FILE_NAME);

                Dialog dialog = new Dialog(AdapterRingtone.this.activity);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_lay);
                dialog.show();
                LinearLayout lay1 = dialog.findViewById(R.id.lay1);
                LinearLayout lay2 = dialog.findViewById(R.id.lay2);
                LinearLayout lay3 = dialog.findViewById(R.id.lay3);
                LinearLayout lay4 = dialog.findViewById(R.id.lay4);
                LinearLayout lay5 = dialog.findViewById(R.id.lay5);


                lay1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 1;
                        AdapterRingtone.this.setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 4;
                        AdapterRingtone.this.setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 2;
                        AdapterRingtone.this.setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 104;
                        AdapterRingtone.this.setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay5.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 105;
                        AdapterRingtone.this.setAsDefault();
                        dialog.dismiss();
                    }
                });
            }
        });

        if (ActivityMain.currentPlayItem == -1) {
            listViewHolder.equalizer_IV.setVisibility(View.VISIBLE);
            listViewHolder.equalizer.setVisibility(View.GONE);
            listViewHolder.equalizer.stopBars();
        } else if (ActivityMain.currentPlayItem == i) {
            listViewHolder.equalizer_IV.setVisibility(View.GONE);
            listViewHolder.equalizer.setVisibility(View.VISIBLE);
            listViewHolder.equalizer.animateBars(); // Whenever you want to tart the animation
        } else {
            listViewHolder.equalizer_IV.setVisibility(View.VISIBLE);
            listViewHolder.equalizer.setVisibility(View.GONE);
            listViewHolder.equalizer.stopBars();
        }

        RecyclerView.LayoutParams marginLayoutParams =
                new RecyclerView.LayoutParams(listViewHolder.relativeFull.getLayoutParams());
        marginLayoutParams.leftMargin = 5;
        marginLayoutParams.rightMargin = 5;
        marginLayoutParams.topMargin = 3;
        if (i == data.size() - 1) {
            marginLayoutParams.bottomMargin = 110;
            Log.e(TAG, "onBindViewHolder: last pos" + i);
        } else {
            marginLayoutParams.bottomMargin = 3;
        }
        listViewHolder.itemView.setLayoutParams(marginLayoutParams);
    }

    private void setAsDefault() {
        if (VERSION.SDK_INT < 23 || System.canWrite(this._context)) {
            new SetTone(this.activity).execute();
        } else if (ContextCompat.checkSelfPermission(this._context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this._context, "android.permission.WRITE_CONTACTS") == 0 && ContextCompat.checkSelfPermission(this._context, "android.permission.WRITE_SETTINGS") == 0) {
            new SetTone(this.activity).execute();
        } else {
            ActivityMain.openDialogForPermission();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onSongItemDeleteClicked(int position);
    }

    public class ArrayAdapterWithIcon extends ArrayAdapter<String> {
        private List<Integer> images;

        ArrayAdapterWithIcon(Context context, String[] strArr, Integer[] numArr) {
            super(context, android.R.layout.select_dialog_item, strArr);
            this.images = Arrays.asList(numArr);
        }

        @NonNull
        @Override
        public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
            view = super.getView(i, view, viewGroup);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextColor(AdapterRingtone.this._context.getResources().getColor(R.color.colorPrimaryDark));
            if (VERSION.SDK_INT >= 17) {
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(((Integer) this.images.get(i)).intValue(), 0, 0, 0);
            } else {
                textView.setCompoundDrawablesWithIntrinsicBounds(((Integer) this.images.get(i)).intValue(), 0, 0, 0);
            }
            textView.setCompoundDrawablePadding((int) TypedValue.applyDimension(1, 12.0f, getContext().getResources().getDisplayMetrics()));
            return view;
        }
    }

    private class ListViewHolder extends ViewHolder {
        EqualizerView equalizer;
        RelativeLayout relativeFull;
        AppCompatImageView btn_next;
        View itemView;
        private ImageView btn_play, equalizer_IV;
        private TextView header_title;
        private RelativeLayout relViewPlayButton;

        ListViewHolder(View view) {
            super(view);
            itemView = view;
            btn_next = (AppCompatImageView) view.findViewById(R.id.btn_next);
            this.relativeFull = (RelativeLayout) view.findViewById(R.id.relativeFull);
            this.header_title = (TextView) view.findViewById(R.id.header_title);
            this.relViewPlayButton = (RelativeLayout) view.findViewById(R.id.relViewPlayButton);
            this.btn_play = (ImageView) view.findViewById(R.id.btn_play);
            this.equalizer_IV = (ImageView) view.findViewById(R.id.equalizer_IV);
            this.equalizer = (EqualizerView) view.findViewById(R.id.equalizer_view);
        }
    }
}
