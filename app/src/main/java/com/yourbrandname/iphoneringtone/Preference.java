package com.yourbrandname.iphoneringtone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preference {
    private Editor editor;
    private SharedPreferences pref;

    Preference(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONST.PACKAGE_NAME);
        stringBuilder.append(1);

        this.pref = context.getSharedPreferences(stringBuilder.toString(), 0);

        editor = pref.edit();
    }

    void setBoolean(String str, Boolean bool) {
        this.editor.putBoolean(str, bool.booleanValue());
        this.editor.commit();
    }

    boolean getBoolean(String str) {
        return this.pref.getBoolean(str, false);
    }
}
