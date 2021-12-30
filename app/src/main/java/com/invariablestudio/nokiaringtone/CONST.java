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
            R.raw.ringtone000,
            R.raw.ringtone001,
            R.raw.ringtone002,
            R.raw.ringtone003,
            R.raw.ringtone004,
            R.raw.ringtone005,
            R.raw.ringtone006,
            R.raw.ringtone007,
            R.raw.ringtone008,
            R.raw.ringtone009,
            R.raw.ringtone010,
            R.raw.ringtone011,
            R.raw.ringtone012,
            R.raw.ringtone013,
            R.raw.ringtone014,
            R.raw.ringtone015,
            R.raw.ringtone016,
            R.raw.ringtone017,
            R.raw.ringtone018,
            R.raw.ringtone019,
            R.raw.ringtone020,
            R.raw.ringtone021,
            R.raw.ringtone022,
            R.raw.ringtone023,
            R.raw.ringtone024,
            R.raw.ringtone025,
            R.raw.ringtone026,
            R.raw.ringtone027,
            R.raw.ringtone028,
            R.raw.ringtone029,
            R.raw.ringtone030,
            R.raw.ringtone031,
            R.raw.ringtone032,
            R.raw.ringtone033,
            R.raw.ringtone034,
            R.raw.ringtone035,
            R.raw.ringtone036,
            R.raw.ringtone037,
            R.raw.ringtone038,
            R.raw.ringtone039,
            R.raw.ringtone040,
            R.raw.ringtone041,
            R.raw.ringtone042,
            R.raw.ringtone043,
            R.raw.ringtone044,
            R.raw.ringtone045,
            R.raw.ringtone046,
            R.raw.ringtone047,
            R.raw.ringtone048,
            R.raw.ringtone049,
            R.raw.ringtone050,
            R.raw.ringtone051,
            R.raw.ringtone052,
            R.raw.ringtone053,
            R.raw.ringtone054,
            R.raw.ringtone055,
            R.raw.ringtone056,
            R.raw.ringtone057,
            R.raw.ringtone058,
            R.raw.ringtone059,
            R.raw.ringtone060,
            R.raw.ringtone061,
            R.raw.ringtone062,
            R.raw.ringtone063,
            R.raw.ringtone064,
            R.raw.ringtone065,
            R.raw.ringtone066,
            R.raw.ringtone067,
            R.raw.ringtone068,
            R.raw.ringtone069,
            R.raw.ringtone070,
            R.raw.ringtone071,
            R.raw.ringtone072,
            R.raw.ringtone073,
            R.raw.ringtone074,
            R.raw.ringtone075,
            R.raw.ringtone076,
            R.raw.ringtone077,
            R.raw.ringtone078,
            R.raw.ringtone079,
            R.raw.ringtone080,
            R.raw.ringtone081,
            R.raw.ringtone082,
            R.raw.ringtone083,
            R.raw.ringtone084,
            R.raw.ringtone085,
            R.raw.ringtone086,
            R.raw.ringtone087,
            R.raw.ringtone088,
            R.raw.ringtone089,
            R.raw.ringtone090,
            R.raw.ringtone091,
            R.raw.ringtone092,
            R.raw.ringtone093,
            R.raw.ringtone094,
            R.raw.ringtone095,
            R.raw.ringtone096,
            R.raw.ringtone097,
            R.raw.ringtone098,
            R.raw.ringtone099,
            R.raw.ringtone100,
            R.raw.ringtone101,
            R.raw.ringtone102,
            R.raw.ringtone103,
            R.raw.ringtone104,
            R.raw.ringtone105,
            R.raw.ringtone106,
            R.raw.ringtone107,
            R.raw.ringtone108,
            R.raw.ringtone109,
            R.raw.ringtone110,
            R.raw.ringtone111,
            R.raw.ringtone112,
            R.raw.ringtone113,
            R.raw.ringtone114,
            R.raw.ringtone115,
            R.raw.ringtone116,
            R.raw.ringtone117,
            R.raw.ringtone118,
            R.raw.ringtone119,
            R.raw.ringtone120,
            R.raw.ringtone121,
            R.raw.ringtone122,
            R.raw.ringtone123,
            R.raw.ringtone124,
            R.raw.ringtone125,
            R.raw.ringtone126,
            R.raw.ringtone127,
            R.raw.ringtone128,
            R.raw.ringtone129,
            R.raw.ringtone130,

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
