package com.example.coolfood;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    public SwitchPreferenceCompat notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
    }


}
