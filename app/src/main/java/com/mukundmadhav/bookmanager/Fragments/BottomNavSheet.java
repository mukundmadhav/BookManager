package com.mukundmadhav.bookmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mukundmadhav.bookmanager.R;
import com.mukundmadhav.bookmanager.activities.ChatActivity;
import com.mukundmadhav.bookmanager.activities.MessageActivity;
import com.mukundmadhav.bookmanager.activities.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomNavSheet extends BottomSheetDialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView textEmail = view.findViewById(R.id.nav_Email);
        TextView textName = view.findViewById(R.id.nav_userName);
        ImageView imgUserPic = view.findViewById(R.id.nav_user_Img);

        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.bringToFront();

        textEmail.setText(mAuth.getCurrentUser().getEmail());
        textName.setText(mAuth.getCurrentUser().getDisplayName());

        Glide.with(view).load(mAuth.getCurrentUser().getPhotoUrl()).into(imgUserPic);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new PostsFragment())
                                .commit();
                        break;
                    case R.id.nav_chats:
                        getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));

                        break;
                    case R.id.nav_settings:
                        getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        getActivity().finish();
                        break;
                }
                return true;
            }
        });

        return view;

    }
}
