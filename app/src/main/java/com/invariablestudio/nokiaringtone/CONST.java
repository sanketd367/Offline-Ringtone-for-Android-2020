package com.invariablestudio.nokiaringtone;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.invariablestudio.nokiaringtone.Utils.DataBasHelper;

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
            R.raw.ringtone0,
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
            R.raw.ringtone1,
            R.raw.ringtone10,
            R.raw.ringtone1043,
            R.raw.ringtone1044,
            R.raw.ringtone1050,
            R.raw.ringtone1051,
            R.raw.ringtone1101,
            R.raw.ringtone1103,
            R.raw.ringtone1106,
            R.raw.ringtone1107,
            R.raw.ringtone1108,
            R.raw.ringtone1109,
            R.raw.ringtone1110,
            R.raw.ringtone1112,
            R.raw.ringtone1129,
            R.raw.ringtone1130,
            R.raw.ringtone2,
            R.raw.ringtone2061,
            R.raw.ringtone2062,
            R.raw.ringtone2063,
            R.raw.ringtone2066,
            R.raw.ringtone2067,
            R.raw.ringtone2069,
            R.raw.ringtone2071,
            R.raw.ringtone2072,
            R.raw.ringtone2073,
            R.raw.ringtone2074,
            R.raw.ringtone2084,
            R.raw.ringtone2085,
            R.raw.ringtone2115,
            R.raw.ringtone2116,
            R.raw.ringtone2121,
            R.raw.ringtone2125,
            R.raw.ringtone2126,
            R.raw.ringtone3,
            R.raw.ringtone3096,
            R.raw.ringtone4,
            R.raw.ringtone4102,
            R.raw.ringtone5,
            R.raw.ringtone6,
            R.raw.ringtone7,
            R.raw.ringtone8,
            R.raw.ringtone8100,
            R.raw.ringtone8104,
            R.raw.ringtone8105,
            R.raw.ringtone8111,
            R.raw.ringtone8113,
            R.raw.ringtone8114,
            R.raw.ringtone8117,
            R.raw.ringtone8118,
            R.raw.ringtone8119,
            R.raw.ringtone8120,
            R.raw.ringtone8122,
            R.raw.ringtone8123,
            R.raw.ringtone8124,
            R.raw.ringtone8127,
            R.raw.ringtone8128,
            R.raw.ringtone9,
            R.raw.ringtone9010,
            R.raw.ringtone9011,
            R.raw.ringtone9012,
            R.raw.ringtone9013,
            R.raw.ringtone9014,
            R.raw.ringtone9015,
            R.raw.ringtone9016,
            R.raw.ringtone9017,
            R.raw.ringtone9018,
            R.raw.ringtone9019,
            R.raw.ringtone9020,
            R.raw.ringtone9021,
            R.raw.ringtone9022,
            R.raw.ringtone9023,
            R.raw.ringtone9024,
            R.raw.ringtone9025,
            R.raw.ringtone9026,
            R.raw.ringtone9027,
            R.raw.ringtone9028,
            R.raw.ringtone9029,
            R.raw.ringtone9031,
            R.raw.ringtone9033,
            R.raw.ringtone9036,
            R.raw.ringtone9037,
            R.raw.ringtone9038,
            R.raw.ringtone9040,
            R.raw.ringtone9041,
            R.raw.ringtone9046,
            R.raw.ringtone9047,
            R.raw.ringtone9048,
            R.raw.ringtone9049,
            R.raw.ringtone9052,
            R.raw.ringtone9054,
            R.raw.ringtone9055,
            R.raw.ringtone9056,
            R.raw.ringtone9057,
            R.raw.ringtone9059,
            R.raw.ringtone9070,
            R.raw.ringtone9075,
            R.raw.ringtone9076,
            R.raw.ringtone9077,
            R.raw.ringtone9078,
            R.raw.ringtone9079,
            R.raw.ringtone9080,
            R.raw.ringtone9081,
            R.raw.ringtone9082,
            R.raw.ringtone9083,
            R.raw.ringtone9086,
            R.raw.ringtone9087,
            R.raw.ringtone9088,
            R.raw.ringtone9089,
            R.raw.ringtone9090,
            R.raw.ringtone9091,
            R.raw.ringtone9092,
            R.raw.ringtone9093,
            R.raw.ringtone9094,
            R.raw.ringtone9095,
            R.raw.ringtone9097,
            R.raw.ringtone9098,
            R.raw.ringtone9099,

    };

    public static ArrayList<JcAudio> getRingtoneJC(Context context) {

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        ArrayList<GSRingtoneNEW> ringtone = new ArrayList<>();
        ringtone = new DataBasHelper(context).getAudio();
       // Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < ringtone.size(); i++) {

            String resid = ringtone.get(i).AUDIO_MYNAME;
            try {
                Class res = R.raw.class;
                Field field = res.getField(resid);
                int rawId = field.getInt(null);

                jcAudios.add(JcAudio.createFromRaw(ringtone.get(i).AUDIO_MYNAME, rawId));
            } catch (Exception e) {
                Log.e("MyTag", "Failure to get drawable id.", e);
            }

        }

  /*      for (int i = 0; i < rawarray.length; i++) {

            jcAudios.add(JcAudio.createFromRaw(songnamearray[i],rawarray[i]));

        }*/


        return jcAudios;
    }

   /* public static void getRings(Context context){
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            if (!new DataBasHelper(context).isTableExists()) {
                new DataBasHelper(context).InsertAudioData(fields[i].getName(), stringBuilder.toString(), fields[i].getName(), "false", 0);
            }
        }
    }*/

    public static void addRingtone(Context context) {
        Field[] fields = R.raw.class.getFields();
        if (new DataBasHelper(context).isTableExists()) {
            if (new DataBasHelper(context).getAudio().size() == 0) {
                for (int i = 0; i < fields.length; i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(fields[i].getName());
                    stringBuilder.append(".mp3");
                    new DataBasHelper(context).InsertAudioData(fields[i].getName(), stringBuilder.toString(), fields[i].getName(), "0", 0);

                }
            }

        } else {
            for (int i = 0; i < fields.length; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(fields[i].getName());
                stringBuilder.append(".mp3");
                new DataBasHelper(context).InsertAudioData(fields[i].getName(), stringBuilder.toString(), fields[i].getName(), "false", 0);

            }
        }


 /*   public static List<GSRingtoneNEW> getRingtone(Context context) {

        List<GSRingtone> arrayList = new ArrayList();
        List<GSRingtone> arrayList2 = new ArrayList();
        List<RELRingtone> relRingtoneList = new ArrayList();
        relRingtoneList = new DataBasHelper(context).getAllRel();

        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            //arrayList.add(new GSRingtoneNEW(stringBuilder.toString(), fields[i].getName(), fields[i].getName()));

            new DataBasHelper(context).InsertAudioData(fields[i].getName(),stringBuilder.toString(),fields[i].getName(),"false",0);


        }



     */
    /*   for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < relRingtoneList.size(); j++) {

                if (fields[i].getName().equals(relRingtoneList.get(j).REL_NAME)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(fields[i].getName());
                    stringBuilder.append(".mp3");
                    arrayList.add(new GSRingtone(stringBuilder.toString(), fields[i].getName(), fields[i].getName()));

                }

            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            arrayList2.add(new GSRingtone(stringBuilder.toString(), fields[i].getName(), fields[i].getName()));
        }
        arrayList.addAll(arrayList2);*/
        /*

         */
    /*for (int i = 0; i < fields.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fields[i].getName());
            stringBuilder.append(".mp3");
            arrayList.add(new GSRingtone(stringBuilder.toString(), fields[i].getName(), songnamearray[i]));
        }*/
    /*

        return new DataBasHelper(context).getAudio();
    }*/

    }
}
