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
            String value = sharedPreferences.getString(preference.getKey(), "");
            setPrefSummary(preference, value);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


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


}
