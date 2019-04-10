package com.mukundmadhav.bookmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mukundmadhav.bookmanager.R;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgPost, imgUserAvatar;
    TextView textPostTitle, textDate, textPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgPost = findViewById(R.id.details_imgPost);
        textPostTitle = findViewById(R.id.details_textTitle);
        textPrice = findViewById(R.id.details_price);
        imgUserAvatar = findViewById(R.id.detaisl_userAvatar);
        textDate = findViewById(R.id.details_date);

        Intent intent = getIntent();

        String postImg = intent.getStringExtra("IntentPic");
        Glide.with(this).load(postImg).into(imgPost);

        String postTitle = intent.getStringExtra("IntentTitle");
        textPostTitle.setText(postTitle);

        String price = "Price                                :  ₹" + intent.getStringExtra("Intentprice") + "\nIs the Item Negotiable? No.";
        textPrice.setText(price);


        String userImg = intent.getStringExtra("IntentUserPic");
        Glide.with(this).load(userImg).into(imgUserAvatar);





    }


    String timeStampString(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }
}
