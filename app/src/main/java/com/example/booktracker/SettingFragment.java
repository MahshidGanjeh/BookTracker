package com.example.booktracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

/**
 * Created by Hp on 2/5/2018.
 */

public class SettingFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_fragment);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int prefItemsCount = preferenceScreen.getPreferenceCount();

        for (int i = 0; i < prefItemsCount; i++) {

            Preference preference = preferenceScreen.getPreference(i);
            //dar sharedpref key_value save mishavad
            //alan ma un value e ke be  in key nesbat dade shode ra be dast miavarim va
            //summary pref ra set mikonim
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPrefSummary(preference, value);
        }
    }

    //this method gets called whenever a preference has changed
    //we override this method to update the right summary after changing by user
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference changedPreference = findPreference(key);
        if (changedPreference != null) {
            setPrefSummary(changedPreference, sharedPreferences.getString(changedPreference.getKey(), ""));
        }
    }

    public void setPrefSummary(Preference p, String value) {

        if (p instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) p;
            int index = listPreference.findIndexOfValue(value);

            listPreference.setSummary(listPreference.getEntries()[index]);
        } else if (p instanceof EditTextPreference) {
            p.setSummary(value);
        }

    }

    //Registering the SharedPreference Change Listener
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

    }
}
