package com.hacker.eaun.cigmanotes.Utils.prefs;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.hacker.eaun.cigmanotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalVariable extends Application {

    private static final String BOOLEAN_KEY_FIRST_LAUNCH = "bol_key_first";
    private static final String KEY_COLOR = "key_color";
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        sharedPreferences = getSharedPreferences("MAIN_PREF", MODE_PRIVATE);
        super.onCreate();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(BOOLEAN_KEY_FIRST_LAUNCH, true);
    }

    public void setFirstLaunch(boolean flag) {
        sharedPreferences.edit().putBoolean(BOOLEAN_KEY_FIRST_LAUNCH, flag).apply();
    }

    public String getThemeColor() {
        return sharedPreferences.getString(KEY_COLOR, "");
    }

    /**
     * For theme color
     */
    public void setThemeColor(String color) {
        sharedPreferences.edit().putString(KEY_COLOR, color).apply();
    }

    public int getThemeColorInt() {
        if (getThemeColor().equals("")) {
            return getResources().getColor(R.color.colorPrimary);
        }
        return Color.parseColor(getThemeColor());
    }


    public String generateCurrentDate(int format_key) {
        Date curDate = new Date();
        String DateToStr = "";
        //default 11-5-2014 11:11:51
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        switch (format_key) {
            case 1:

                format = new SimpleDateFormat("dd/MM/yyy");
                DateToStr = format.format(curDate);
                break;

            case 2:
                //May 11, 2014 11:37 PM
                DateToStr = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(curDate);
                break;
            case 3:
                //11-5-2014 11:11:51
                format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                DateToStr = format.format(curDate);
                break;
        }
        return DateToStr;
    }

}
