package com.coretech.flashit.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.coretech.flashit.CreatingCardSetActivity;
import com.coretech.flashit.FeedbackActivity;
import com.coretech.flashit.MainActivity;
import com.coretech.flashit.ProfileActivity;
import com.coretech.flashit.R;
import com.coretech.flashit.SettingActivity;
import com.coretech.flashit.ui.settings.SettingsFragment;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        return view;
    }
}
