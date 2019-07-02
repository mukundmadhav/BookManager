package com.mukundmadhav.bookmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mukundmadhav.bookmanager.Models.User;
import com.mukundmadhav.bookmanager.R;
import com.mukundmadhav.bookmanager.activities.MessageActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    Context mContext;
    List<User> mUser;

    public UserAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.user_single_item, parent, false);

        return new UserAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder myViewHolder, int i) {

        final User user = mUser.get(i);
        myViewHolder.username.setText(user.getUserId());
        if(user.getUserPic().equals("default")){
            myViewHolder.Profile.setImageResource(R.mipmap.ic_launcher);

        }
        else{
            Glide.with(mContext).load(user.getUserPic()).into(myViewHolder.Profile);
        }
        myViewHolder.title.setText(user.getTitle());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(mContext, MessageActivity.class);
                 intent.putExtra("userId",user.getUserId());
                 intent.putExtra("userpic",user.getUserPic());
                 mContext.startActivity(intent);



            }
        });


    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView Profile;
        public TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Profile = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.item_username);
            title=itemView.findViewById(R.id.item_bookname);

        }
    }
}

