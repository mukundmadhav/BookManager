package com.mukundmadhav.bookmanager.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mukundmadhav.bookmanager.Activities.LoginActivity;
import com.mukundmadhav.bookmanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomNavSheet extends BottomSheetDialogFragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_bottomsheet,container,false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        TextView textEmail = view.findViewById(R.id.nav_Email);
        TextView textName = view.findViewById(R.id.nav_userName);
        ImageView imgUserPic = view.findViewById(R.id.nav_user_Img);

        NavigationView navigationView =  view.findViewById(R.id.navigation_view);
        navigationView.bringToFront();

        textEmail.setText(mAuth.getCurrentUser().getEmail());
        textName.setText(mAuth.getCurrentUser().getDisplayName());

        Glide.with(view).load(mAuth.getCurrentUser().getPhotoUrl()).into(imgUserPic);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new PostsFragment())
                                .commit();
                        Toast.makeText(view.getContext(), "Nav Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_chats :
                        Toast.makeText(getActivity(), "Nav Chats", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings :
                        Toast.makeText(getActivity(), "Nav Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout :
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
