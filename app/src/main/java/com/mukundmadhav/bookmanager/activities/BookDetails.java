package com.mukundmadhav.bookmanager.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.mukundmadhav.bookmanager.R;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetails extends AppCompatActivity {
    TextView mTextView;
    String Data;
    String Picture;
    String PostKey;
    String Price;
    String TimeStamp;
    String Title;
    String UserId;
    String UserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        mTextView = findViewById(R.id.textView);

        if (getIntent().getExtras() != null) {
            Picture = getIntent().getExtras().getString("picture");
            PostKey = getIntent().getExtras().getString("postKey");
            Price = getIntent().getExtras().getString("price");
//            TimeStamp = getIntent().getExtras().getString("timeStamp");
            Title = getIntent().getExtras().getString("title");
            UserId = getIntent().getExtras().getString("userId");
            UserPic = getIntent().getExtras().getString("userPic");
        }

        Data = Picture + "\n\n" +
                PostKey + "\n\n" +
                Picture + "\n\n" +
//                TimeStamp + "\n\n" +
                Title + "\n\n" +
                UserId + "\n\n" +
                UserPic;

        mTextView.setText(Data);
    }
}
