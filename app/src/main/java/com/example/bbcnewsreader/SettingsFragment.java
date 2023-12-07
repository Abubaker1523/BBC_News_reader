package com.example.bbcnewsreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.bbcnewsreader.R;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

        Preference languagePreference = findPreference("language_preference");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener(this);
        }

        // Set initial language for the preference category title and summary
        updateLanguageForPreferenceCategoryTitleAndSummary(sharedPreferences.getString("selected_language", "English"));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String selectedLanguage = newValue.toString();
        setLocale(selectedLanguage);
        updateLanguageForPreferenceCategoryTitleAndSummary(selectedLanguage);
        restartActivity();
        return true;
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Save the selected language to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_language", language);
        editor.apply();
    }

    private void restartActivity() {
        ((SettingsActivity) requireActivity()).restartActivity();
    }

    private void updateLanguageForPreferenceCategoryTitleAndSummary(String language) {
        PreferenceCategory languageCategory = findPreference("language_category");
        if (languageCategory != null) {
            languageCategory.setTitle(getString(R.string.language_settings_title));
            languageCategory.setSummary(getString(R.string.choose_language_summary));
            String appName;
            appName = getString(R.string.app_name);
            requireActivity().setTitle(appName);

            // Force the preference screen to reload
            getPreferenceScreen().removeAll();
            addPreferencesFromResource(R.xml.settings);
        }
    }




}
