package com.invariablestudio.nokiaringtone;

import android.content.Context;
import android.util.Log;

import com.example.jean.jcplayer.model.JcAudio;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CONST {

    public static final String FAIL_TASK = "FAIL";
    public static String PACKAGE_NAME = "APP";
    public static String RATE = "RATE1";
    public static final int REQUEST_FOR_PICK_CONTACT = 505;
    public static final int REQUEST_FOR_WRITE_SETTINGS = 404;
    public static String SELECTED_RAW_FILE_NAME = null;
    public static String SELECTED_SHOW_FILE_NAME = null;
    public static int SELECTED_TYPE = 0;
    public static String SELECTED_URL = null;
    public static String SHARE_AUDIO_TITLE = "Share Audio File";
    public static final String SUCCESS_TASK = "SUCCESS";
    public static int userCount = 1;
    public static int POS = 0;

    public static int[] rawArray = new int[]{
            R.raw.ringtone00,
            R.raw.ringtone01,
            R.raw.ringtone02,
            R.raw.ringtone03,
            R.raw.ringtone04,
            R.raw.ringtone05,
            R.raw.ringtone06,
            R.raw.ringtone07,
            R.raw.ringtone08,
            R.raw.ringtone09,
            R.raw.ringtone10,
            R.raw.ringtone11,
            R.raw.ringtone12,
            R.raw.ringtone13,
            R.raw.ringtone14,
            R.raw.ringtone15,
            R.raw.ringtone16,
            R.raw.ringtone17,
            R.raw.ringtone18,
            R.raw.ringtone19,
            R.raw.ringtone20,
            R.raw.ringtone21,
            R.raw.ringtone22,
            R.raw.ringtone23,
            R.raw.ringtone24,
            R.raw.ringtone25,
            R.raw.ringtone26,
            R.raw.ringtone27,
            R.raw.ringtone28,
            R.raw.ringtone29,
            R.raw.ringtone30,
            R.raw.ringtone31,
            R.raw.ringtone32,
            R.raw.ringtone33,
            R.raw.ringtone34,
            R.raw.ringtone35,
            R.raw.ringtone36,
            R.raw.ringtone37,
            R.raw.ringtone38,
            R.raw.ringtone39,
            R.raw.ringtone40,
            R.raw.ringtone41,
            R.raw.ringtone42,
            R.raw.ringtone43,
            R.raw.ringtone44,
            R.raw.ringtone45,
            R.raw.ringtone46,
            R.raw.ringtone47,
            R.raw.ringtone48,
            R.raw.ringtone49,
            R.raw.ringtone50,
            R.raw.ringtone51,
            R.raw.ringtone52,
            R.raw.ringtone53,
            R.raw.ringtone54,
            R.raw.ringtone55,
            R.raw.ringtone56,
            R.raw.ringtone57,
            R.raw.ringtone58,
            R.raw.ringtone59,
            R.raw.ringtone60,
//            R.raw.ringtone61,
//            R.raw.ringtone62,
//            R.raw.ringtone63,
//            R.raw.ringtone64,
//            R.raw.ringtone65,
//            R.raw.ringtone66,
//            R.raw.ringtone67,
//            R.raw.ringtone68,
//            R.raw.ringtone69,
    };


    public static ArrayList<JcAudio> getRingtoneJC(Context context) {

        ArrayList<JcAudio> jcAudios = new ArrayList<>();


        Field[] fields = R.raw.class.getFields();


        for (int i = 0; i < fields.length; i++) {


            String resid = fields[i].getName();
            try {
                Class res = R.raw.class;
                Field field = res.getField(resid);
                int rawId = field.getInt(null);

                jcAudios.add(JcAudio.createFromRaw(fields[i].getName(), rawId));
            } catch (Exception e) {
                Log.e("MyTag", "Failure to get drawable id.", e);
            }


        }


  /*      for (int i = 0; i < rawarray.length; i++) {

            jcAudios.add(JcAudio.createFromRaw(songnamearray[i],rawarray[i]));

        }*/


        return jcAudios;
    }


    public static List<GSRingtone> getRingtone(Context context) {

        List<GSRingtone> arrayList = new ArrayList();

        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            arrayList.add(new GSRingtone(stringBuilder.toString(), fields[i].getName(), fields[i].getName()));
        }

        /*for (int i = 0; i < fields.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            arrayList.add(new GSRingtone(stringBuilder.toString(), fields[i].getName(), songnamearray[i]));
        }*/


        return arrayList;
    }

}
