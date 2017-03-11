package com.hacker.eaun.cigmanotes.Utils.prefs;

/**
 * Settings preferences go here
 * By Eaun Ballinger 09/08/2016
 */

import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hacker.eaun.cigmanotes.R;

public class AppPreferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(R.id.setting_list,settingsFragment,"SETTINGS_FRAGMENT");
        fragmentTransaction.commit();
    }

    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
        }
    }
}
