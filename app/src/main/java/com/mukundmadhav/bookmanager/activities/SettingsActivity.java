package com.mukundmadhav.bookmanager.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mukundmadhav.bookmanager.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;
    TextView edit;
    TextView edit_profile_pic;
    ImageView profilePic;
    EditText email;
    EditText name;
    EditText password1;
    EditText password2;
    Button save;
    Button cancel;
    FirebaseUser mFireBaseUser;
    Boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edit = findViewById(R.id._edit);
        edit_profile_pic = findViewById(R.id._edit_profile_pic);
        profilePic = findViewById(R.id._profile);
        email = findViewById(R.id._email);
        name = findViewById(R.id._name);
        password1 = findViewById(R.id._password1);
        password2 = findViewById(R.id._password2);
        save = findViewById(R.id._save);
        cancel = findViewById(R.id._cancel);

        mFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();

        fetchUserDetails();

        edit.setOnClickListener(this);
        edit_profile_pic.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == edit) {
            name.setEnabled(true);
            password1.setEnabled(true);
            password2.setEnabled(true);
            findViewById(R.id._buttonLayout).setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
            editing = true;
        } else if (view == save) {
            save();
        } else if (view == cancel) {
            name.setEnabled(false);
            password1.setEnabled(false);
            password2.setEnabled(false);
            password1.setText("");
            editing = false;
            password2.setText("");
            edit.setVisibility(View.VISIBLE);
            fetchUserDetails();
            findViewById(R.id._buttonLayout).setVisibility(View.INVISIBLE);
        } else if (view == edit_profile_pic) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, REQUEST_CODE);
        }
    }

    private void fetchUserDetails() {
        name.setText(mFireBaseUser.getDisplayName());
        email.setText(mFireBaseUser.getEmail());
        Glide.with(this).load(mFireBaseUser.getPhotoUrl()).into(profilePic);
    }

    private void save() {
        String Name = name.getText().toString();
        String p1 = password1.getText().toString();
        String p2 = password2.getText().toString();

        if (!Name.equals(mFireBaseUser.getDisplayName()) && Name.length() >= 6) {
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(Name)
                    .setPhotoUri(mFireBaseUser.getPhotoUrl())
                    .build();

            mFireBaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    fetchUserDetails();
                    name.setEnabled(false);
                    password1.setEnabled(false);
                    password2.setEnabled(false);
                    password1.setText("");
                    password2.setText("");
                    edit.setVisibility(View.VISIBLE);
                    editing = false;
                    findViewById(R.id._buttonLayout).setVisibility(View.INVISIBLE);
                }
            });

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }

        if (p1.equals(p2) && p1.length() > 6) {
            mFireBaseUser.updatePassword(p1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    fetchUserDetails();
                    name.setEnabled(false);
                    password1.setEnabled(false);
                    password2.setEnabled(false);
                    password1.setText("");
                    editing = false;
                    password2.setText("");
                    edit.setVisibility(View.VISIBLE);
                    findViewById(R.id._buttonLayout).setVisibility(View.INVISIBLE);
                }
            });

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            Uri pickedImageUri = data.getData();
            profilePic.setImageURI(pickedImageUri);
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(pickedImageUri)
                    .build();

            mFireBaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    fetchUserDetails();
                    name.setEnabled(false);
                    password1.setEnabled(false);
                    password2.setEnabled(false);
                    password1.setText("");
                    password2.setText("");
                    edit.setVisibility(View.VISIBLE);
                    findViewById(R.id._buttonLayout).setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (!editing) {
            super.onBackPressed();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Unsaved changes");
            builder.setMessage("Do you want to cancel?");
            builder.setNegativeButton("Revert", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }
}
