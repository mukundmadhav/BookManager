package com.mukundmadhav.bookmanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mukundmadhav.bookmanager.Fragments.BottomNavSheet;
import com.mukundmadhav.bookmanager.R;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

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
