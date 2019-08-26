package com.example.coolfood;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    SwitchPreference notifications;
    ListPreference language;
    Preference resetPass;
    private FirebaseUser user;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        notifications = (SwitchPreference) findPreference("notification");
        notifications.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (o.equals(true)) {
                    FirebaseMessaging.getInstance().subscribeToTopic("notification")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), R.string.subscribe, Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("notification")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getContext(), R.string.unsubscribe, Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                return true;
            }
        });

        language = (ListPreference) findPreference("language");
        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (o.equals("1"))
                    setLocal("en");
                else if (o.equals("2"))
                    setLocal("sr");
                else
                    setLocal("en");
                getActivity().recreate();
                return true;
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        resetPass = findPreference("resetPass");
        resetPass.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.reset_password);
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.new_password_layout, (ViewGroup) getView(), false);
                final EditText emailTV = viewInflated.findViewById(R.id.email);
                final EditText oldPassTV = viewInflated.findViewById(R.id.old_password);
                final EditText newPassTV = viewInflated.findViewById(R.id.new_password);
                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String oldPass = oldPassTV.getText().toString();
                        final String newPass = newPassTV.getText().toString();

                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getContext(), R.string.went_wrong + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getContext(), R.string.password_modified, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), R.string.auth_failed + " " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });

    }

    public void setLocal(String lang) {
        Resources res = getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang.toLowerCase())); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

    }


}


