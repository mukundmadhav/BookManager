package com.mukundmadhav.bookmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mukundmadhav.bookmanager.Models.Chat;
import com.mukundmadhav.bookmanager.Models.User;
import com.mukundmadhav.bookmanager.R;
import com.mukundmadhav.bookmanager.activities.MessageActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    private static final int MSG_TYPE_LEFT=0;
    private static final int MSG_TYPE_RIGHT=1;
    private Context mContext;
    private List<Chat> mChat;

    private FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.MyViewHolder(view);
        } else
        {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        return new MessageAdapter.MyViewHolder(view);
    }
    }



    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int i) {

        final Chat chat = mChat.get(i);

        holder.show_message.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;

        }
        else
            return MSG_TYPE_LEFT;
    }
}

