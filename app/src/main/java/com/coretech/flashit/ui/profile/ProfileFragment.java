package com.coretech.flashit.ui.profile;

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
import com.coretech.flashit.R;
import com.coretech.flashit.SettingActivity;
import com.coretech.flashit.ui.settings.SettingsFragment;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView image = view.findViewById(R.id.profile_card01);
        ImageView image2 = view.findViewById(R.id.profile_card02);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent2);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}