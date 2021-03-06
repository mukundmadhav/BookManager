package com.mukundmadhav.bookmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mukundmadhav.bookmanager.Models.Book;
import com.mukundmadhav.bookmanager.R;
import com.mukundmadhav.bookmanager.activities.DetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> mData;

    public BookAdapter(Context mContext, List<Book> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View row = LayoutInflater.from(mContext).inflate(R.layout.row_post_item, viewGroup, false);

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.textTitle.setText(mData.get(i).getTitle());
        Glide.with(mContext).load(mData.get(i).getPicture()).into(myViewHolder.imgPost);
        Glide.with(mContext).load(mData.get(i).getUserPic()).into(myViewHolder.imgPostProfile);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        ImageView imgPost, imgPostProfile;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            imgPost = itemView.findViewById(R.id.row_post_img);
            imgPostProfile = itemView.findViewById(R.id.row_postProfilePic);
            textTitle = itemView.findViewById(R.id.row_postTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent detailIntent = new Intent(mContext, DetailsActivity.class);
                    int pos = getAdapterPosition();
                    detailIntent.putExtra("IntentTitle", mData.get(pos).getTitle());
                    detailIntent.putExtra("IntentPic", mData.get(pos).getPicture());
                    detailIntent.putExtra("Intentprice", mData.get(pos).getPrice());
                    detailIntent.putExtra("IntentUserPic", mData.get(pos).getUserPic());
                    detailIntent.putExtra("UserId", mData.get(pos).getUserId());
                    long timestamp = (long) mData.get(pos).getTimeStamp();
                    detailIntent.putExtra("IntentDate", timestamp);
                    mContext.startActivity(detailIntent);
                }
            });

        }
    }

}
