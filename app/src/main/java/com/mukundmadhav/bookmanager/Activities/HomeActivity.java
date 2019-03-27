package com.mukundmadhav.bookmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mukundmadhav.bookmanager.Fragments.BottomNavSheet;
import com.mukundmadhav.bookmanager.R;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView popupChooseImg, popupDoneBtn;
    Dialog popupAddPost;
    EditText popupName, popupPrice;
    ProgressBar popup_pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        initPopup();


    }

    private void initPopup() {

        popupAddPost = new Dialog(this);
        popupAddPost.setContentView(R.layout.popup_addpost);
        popupAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popupAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

        popupChooseImg = findViewById(R.id.popup_uploadImg);
        popupDoneBtn = findViewById(R.id.popup_doneBtn);
        popupName = findViewById(R.id.popup_nameBook);
        popupPrice = findViewById(R.id.popup_priceBook);
        popup_pbar = findViewById(R.id.popup_progressBar);

        //Click Listener for Done button




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
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();

        }
        else if(item.getItemId() == android.R.id.home) {
            Log.i("com.mukundmadhav.bookmanager","marked Yes");
            BottomNavSheet bottomNavigationDrawerFrag = new BottomNavSheet();
            bottomNavigationDrawerFrag.show(getSupportFragmentManager(),bottomNavigationDrawerFrag.getTag());
        }


        return true;
    }


}
