package com.invariablestudio.nokiaringtone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.invariablestudio.nokiaringtone.Utils.DataBasHelper;

import java.util.ArrayList;
import java.util.List;

import es.claucookie.miniequalizerlibrary.EqualizerView;

public class AdapterRingtone1 extends RecyclerView.Adapter<AdapterRingtone1.viewHolder> {
    public static List<GSRingtoneNEW> data;
    public static int currentpos = 0;
    private static OnItemClickListener mListener;
    String TAG = "AdapterRingtone1";
    private Context _context;
    private Activity activity;
    String type = "list";

    public void updateAdapter(int currentpos) {
        AdapterRingtone1.currentpos = currentpos;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterRingtone1.OnItemClickListener listener) {
        mListener = listener;
    }

    AdapterRingtone1(Activity activity, List<GSRingtoneNEW> list) {
        this._context = activity.getBaseContext();
        this.data = list;
        this.activity = activity;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterRingtone1.viewHolder(((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_ringtone, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        GSRingtoneNEW gsRingtone = data.get(holder.getAbsoluteAdapterPosition());


        holder.header_title.setText(gsRingtone.AUDIO_MYNAME);

        holder.header_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
               /*     if(new DataBasHelper(_context).isRel(gsRingtone.MY_NAME)){
                        new DataBasHelper(_context).UpdateRel(gsRingtone.MY_NAME);
                    }else{
                        new DataBasHelper(_context).InsertRel(gsRingtone.MY_NAME,"0");*/
                    new DataBasHelper(_context).UpdateRel(gsRingtone.AUDIO_MYNAME);
                    /* }*/
                    mListener.onItemClick(position);
                }
            }
        });

        View.OnClickListener anonymousClass1 = new View.OnClickListener() {
            public void onClick(View view) {

                if (mListener != null) {
                    mListener.onItemClick(position);
                }
//                currentpos = i;
//
//                notifyDataSetChanged();
            }
        };

        holder.ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new DataBasHelper(_context).getFav(gsRingtone.AUDIO_ID)) {
                    new DataBasHelper(_context).UpdateFav(gsRingtone.AUDIO_ID, "false");
                    Toast.makeText(_context, "Remove to Favourite", Toast.LENGTH_SHORT).show();
                    holder.ivFav.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));

                    //      data.set(holder.getAbsoluteAdapterPosition(),new GSRingtoneNEW(gsRingtone.AUDIO_ID,gsRingtone.AUDIO_RAWFILENAME, gsRingtone.AUDIO_SHOWFILENAME, gsRingtone.AUDIO_MYNAME, "0", gsRingtone.AUDIO_RELCOUNT));
                } else {
                    new DataBasHelper(_context).UpdateFav(gsRingtone.AUDIO_ID, "true");
                    holder.ivFav.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    Toast.makeText(_context, "Added to Favourite", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                  //  data.set(holder.getAbsoluteAdapterPosition(),new GSRingtoneNEW(gsRingtone.AUDIO_ID,gsRingtone.AUDIO_RAWFILENAME, gsRingtone.AUDIO_SHOWFILENAME, gsRingtone.AUDIO_MYNAME, "1", gsRingtone.AUDIO_RELCOUNT));

                }

            }
        });

        if (new DataBasHelper(_context).getFav(gsRingtone.AUDIO_ID)) {
            holder.ivFav.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
        } else {
            holder.ivFav.setImageDrawable(_context.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
        }
        holder.relViewPlayButton.setOnClickListener(anonymousClass1);
        holder.btn_play.setOnClickListener(anonymousClass1);
        holder.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CONST.POS = position;
                CONST.SELECTED_RAW_FILE_NAME = data.get(position).AUDIO_RAWFILENAME;
                CONST.SELECTED_SHOW_FILE_NAME = data.get(position).AUDIO_SHOWFILENAME;
                Log.e(TAG, "onClick: position: " + position);
                Log.e(TAG, "onClick: name: " + CONST.SELECTED_RAW_FILE_NAME);

                Dialog dialog = new Dialog(activity);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_lay);
                dialog.show();
                LinearLayout lay1 = dialog.findViewById(R.id.lay1);
                LinearLayout lay2 = dialog.findViewById(R.id.lay2);
                LinearLayout lay3 = dialog.findViewById(R.id.lay3);
                LinearLayout lay4 = dialog.findViewById(R.id.lay4);
                LinearLayout lay5 = dialog.findViewById(R.id.lay5);

                lay1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 1;
                        setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 4;
                        setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 2;
                        setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 104;
                        setAsDefault();
                        dialog.dismiss();
                    }
                });
                lay5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CONST.SELECTED_TYPE = 105;
                        setAsDefault();
                        dialog.dismiss();
                    }
                });
            }
        });

        if (ActivityMain.currentPlayItem == -1) {
            holder.equalizer_IV.setVisibility(View.VISIBLE);
            holder.equalizer.setVisibility(View.GONE);
            holder.equalizer.stopBars();
        } else if (ActivityMain.currentPlayItem == position) {
            holder.equalizer_IV.setVisibility(View.GONE);
            holder.equalizer.setVisibility(View.VISIBLE);
            holder.equalizer.animateBars(); // Whenever you want to tart the animation
        } else {
            holder.equalizer_IV.setVisibility(View.VISIBLE);
            holder.equalizer.setVisibility(View.GONE);
            holder.equalizer.stopBars();
        }

        RecyclerView.LayoutParams marginLayoutParams =
                new RecyclerView.LayoutParams(holder.relativeFull.getLayoutParams());
        marginLayoutParams.leftMargin = 5;
        marginLayoutParams.rightMargin = 5;
        marginLayoutParams.topMargin = 3;
        if (position == data.size() - 1) {
            marginLayoutParams.bottomMargin = 110;
            Log.e(TAG, "onBindViewHolder: last pos" + position);
        } else {
            marginLayoutParams.bottomMargin = 3;
        }
        holder.itemView.setLayoutParams(marginLayoutParams);

    }

    private void setAsDefault() {
        if (Build.VERSION.SDK_INT < 23 || Settings.System.canWrite(this._context)) {
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

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        EqualizerView equalizer;
        RelativeLayout relativeFull;
        AppCompatImageView btn_next;
        ImageView ivFav;
        private ImageView btn_play, equalizer_IV;
        private TextView header_title;
        private RelativeLayout relViewPlayButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            btn_next = (AppCompatImageView) itemView.findViewById(R.id.btn_next);
            this.relativeFull = (RelativeLayout) itemView.findViewById(R.id.relativeFull);
            this.header_title = (TextView) itemView.findViewById(R.id.header_title);
            this.relViewPlayButton = (RelativeLayout) itemView.findViewById(R.id.relViewPlayButton);
            this.btn_play = (ImageView) itemView.findViewById(R.id.btn_play);
            this.equalizer_IV = (ImageView) itemView.findViewById(R.id.equalizer_IV);
            this.ivFav = (ImageView) itemView.findViewById(R.id.ivFav);
            this.equalizer = (EqualizerView) itemView.findViewById(R.id.equalizer_view);
        }
    }
}
