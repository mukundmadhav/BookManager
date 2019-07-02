package com.mukundmadhav.bookmanager.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mukundmadhav.bookmanager.Fragments.BottomNavSheet;
import com.mukundmadhav.bookmanager.Fragments.PostsFragment;
import com.mukundmadhav.bookmanager.Models.Book;
import com.mukundmadhav.bookmanager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView popupChooseImg, popupDoneBtn;
    Dialog popupAddPost;
    EditText popupName, popupPrice;
    ProgressBar popup_pbar;
    Uri pickedImageUri = null;
    FirebaseUser currentUser;
    FirebaseAuth
            mAuth;

    private static final int PReqCode = 2;
    private static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);


        initPopup();
        setupPopupImgClick();

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupAddPost.show();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new PostsFragment())
                .commit();


    }

    private void setupPopupImgClick() {

        popupChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForPermissions();
            }
        });

    }

    private void checkForPermissions() {


        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please accept for the required persmissions", Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }

        }

        else {
            openGallery();
        }


    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }

    private void initPopup() {

        popupAddPost = new Dialog(this);
        popupAddPost.setContentView(R.layout.popup_addpost);
        popupAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popupAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        popupChooseImg = popupAddPost.findViewById(R.id.popup_img);
        popupName = popupAddPost.findViewById(R.id.popup_title);
        popupDoneBtn = popupAddPost.findViewById(R.id.popup_done);
        popupPrice = popupAddPost.findViewById(R.id.popup_desc);
        popup_pbar = popupAddPost.findViewById(R.id.popup_pbar);

        //Click Listener for Done button

        popupDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                popupDoneBtn.setVisibility(View.INVISIBLE);
                popup_pbar.setVisibility(View.VISIBLE);

                if(!popupName.getText().toString().isEmpty() && !popupPrice.getText().toString().isEmpty()
                        && pickedImageUri!= null) {


                    StorageReference storageBlogImgReg = FirebaseStorage.getInstance().getReference().child("blog_images");
                    final StorageReference imgPath = storageBlogImgReg.child(pickedImageUri.getLastPathSegment());

                    imgPath.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imgPath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageDownloadLin = uri.toString();

                                    Book book = new Book(popupName.getText().toString(),
                                            popupPrice.getText().toString(),
                                            imageDownloadLin,
                                            currentUser.getUid(),
                                            currentUser.getPhotoUrl().toString());

                                    addPostToFirebase(book);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    showMessage(e.getMessage());
                                    popupDoneBtn.setVisibility(View.VISIBLE);
                                    popup_pbar.setVisibility(View.INVISIBLE);

                                }
                            });


                        }
                    });
                }
                else {
                    showMessage("Please input in all the fields");
                    popupDoneBtn.setVisibility(View.VISIBLE);
                    popup_pbar.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    private void addPostToFirebase(Book book) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        //Get key
        String key = myRef.getKey();
        book.setPostKey(key);

        //now add to Firebase database
        myRef.setValue(book).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Added Successfully");


                popupDoneBtn.setVisibility(View.VISIBLE);
                popup_pbar.setVisibility(View.INVISIBLE);

                popupAddPost.dismiss();


            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottomappbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.app_bar_search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        else if(item.getItemId() == android.R.id.home) {
            BottomNavSheet bottomNavigationDrawerFrag = new BottomNavSheet();
            bottomNavigationDrawerFrag.show(getSupportFragmentManager(),bottomNavigationDrawerFrag.getTag());
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            pickedImageUri = data.getData();
            popupChooseImg.setImageURI(pickedImageUri);
        }
    }
}
