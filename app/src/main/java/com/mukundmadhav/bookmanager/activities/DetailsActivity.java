package com.mukundmadhav.bookmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukundmadhav.bookmanager.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    ImageView imgPost, imgUserAvatar;
    TextView textPostTitle, textDate, textPrice;
    FirebaseUser fuser;
    DatabaseReference reference;
    EditText message;
    ImageButton send_btn;
    Intent intent;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imgPost = findViewById(R.id.details_imgPost);
        message=findViewById(R.id.editText);
        textPostTitle = findViewById(R.id.details_textTitle);
        textPrice = findViewById(R.id.details_price);
        imgUserAvatar = findViewById(R.id.detaisl_userAvatar);
        textDate = findViewById(R.id.details_date);
        send_btn=findViewById(R.id.button2);

        /*intent=getIntent();
        final String userid=intent.getStringExtra("userId");
        final String userpic=intent.getStringExtra("userpic");*/

        fuser= FirebaseAuth.getInstance().getCurrentUser();
       // reference= FirebaseDatabase.getInstance().getReference("Posts").child(userid);

        Intent intent = getIntent();
         userid = intent.getStringExtra("UserId");
        String postImg = intent.getStringExtra("IntentPic");
        Glide.with(this).load(postImg).into(imgPost);

        String postTitle = intent.getStringExtra("IntentTitle");
        textPostTitle.setText(postTitle);

        String price = "Price                                :  ₹" + intent.getStringExtra("Intentprice") + "\nIs the Item Negotiable? No.";
        textPrice.setText(price);


        String userImg = intent.getStringExtra("IntentUserPic");
        Glide.with(this).load(userImg).into(imgUserAvatar);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=message.getText().toString();
                if(!msg.equals(""))
                {
                    sendmessage(fuser.getUid(),userid,msg);
                }
                else
                    Toast.makeText(DetailsActivity.this, "cant send a empty message", Toast.LENGTH_SHORT).show();
                message.setText("");

            }
        });





    }


    String timeStampString(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", calendar).toString();
        return date;
    }

    private void sendmessage(String sender,String receiver,String message)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashmap=new HashMap<>();
        hashmap.put("sender",sender);
        hashmap.put("receiver",receiver);
        hashmap.put("message",message);
        reference.child("Chats").push().setValue(hashmap);
    }
}
