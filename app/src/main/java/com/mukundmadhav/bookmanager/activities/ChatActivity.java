package com.mukundmadhav.bookmanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.mukundmadhav.bookmanager.Adapters.ViewPagerAdapter;
import com.mukundmadhav.bookmanager.Fragments.BuyingFragment;
import com.mukundmadhav.bookmanager.Fragments.SellingFragment;
import com.mukundmadhav.bookmanager.R;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        TabLayout tabLayout=findViewById(R.id.tabs);
        ViewPager viewPager=findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BuyingFragment(),"Buying");
        viewPagerAdapter.addFragment(new SellingFragment(),"Selling");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
