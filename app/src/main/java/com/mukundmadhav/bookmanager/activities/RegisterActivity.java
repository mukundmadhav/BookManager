package com.mukundmadhav.bookmanager.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mukundmadhav.bookmanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RegisterActivity extends AppCompatActivity {

    private static final int PReqCode = 1;
    private static final int REQUESCODE = 1;
    EditText editName, editEmail, editPass, editRepass;
    ImageView imgChoose;
    Button btnReg;
    ProgressBar pbarReg;
    Uri pickedImageUri = null;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init Views
        editEmail = findViewById(R.id.reg_email);
        editName = findViewById(R.id.reg_name);
        editPass = findViewById(R.id.reg_pass);
        editRepass = findViewById(R.id.reg_repass);
        imgChoose = findViewById(R.id.reg_chooseImg);
        btnReg = findViewById(R.id.reg_regBtn);
        pbarReg = findViewById(R.id.reg_progressbar);
        mAuth = FirebaseAuth.getInstance();

        pbarReg.setVisibility(View.INVISIBLE);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbarReg.setVisibility(View.VISIBLE);
                btnReg.setVisibility(View.INVISIBLE);

                final String email = editEmail.getText().toString();
                final String pass = editPass.getText().toString();
                final String pass2 = editRepass.getText().toString();
                final String name = editName.getText().toString();

                if(email.isEmpty() || pass.isEmpty() || name.isEmpty() || !pass2.equals(pass) || pickedImageUri==null) {
                    showMessage("Please fill in all fields including the image");
                    pbarReg.setVisibility(View.INVISIBLE);
                    btnReg.setVisibility(View.VISIBLE);
                }
                else {
                    createUserAccount(name,email,pass);
                }

            }
        });

        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPermisssions();
            }
        });


    }

    private void checkForPermisssions() {

        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessage("Please accept for the required persmissions");
            }
            else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }

        }

        else {
            openGallery();
        }

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void createUserAccount(final String name, String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    showMessage("Account Creation Sucessful. Updating image...");
                    //update name & pic
                    updateNamePic(name,pickedImageUri,mAuth.getCurrentUser());
                }
                else {
                    showMessage("Account Creation Failed" + task.getException().getMessage());
                    pbarReg.setVisibility(View.INVISIBLE);
                    btnReg.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void updateNamePic(final String name, Uri pickedImageUri, final FirebaseUser currentUser) {

        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference userfilePath = firebaseStorage.child(pickedImageUri.getLastPathSegment());

        userfilePath.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //File uploaded. Get its download link

                userfilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //create a change request
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                
                                if(task.isSuccessful()) {
                                    //pic and names linked 
                                    showMessage("User Registration Successful");
                                    updateUI();
                                }
                                
                            }
                        });


                    }
                });

            }
        });

    }

    private void updateUI() {
        Intent homeActivity = new Intent(RegisterActivity.this,HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data !=null ) {
            pickedImageUri = data.getData();
            imgChoose.setImageURI(pickedImageUri);
        }
    }
}
