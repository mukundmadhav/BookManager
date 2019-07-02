package com.mukundmadhav.bookmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukundmadhav.bookmanager.Adapters.UserAdapter;
import com.mukundmadhav.bookmanager.Models.Chat;
import com.mukundmadhav.bookmanager.Models.User;
import com.mukundmadhav.bookmanager.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BuyingFragment extends androidx.fragment.app.Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view=inflater.inflate(R.layout.fragment_buying, container, false);

     recyclerView = view.findViewById(R.id.recycler_buying);
     recyclerView.setHasFixedSize(true);
     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

     fuser= FirebaseAuth.getInstance().getCurrentUser();

     usersList=new ArrayList<>();

     reference= FirebaseDatabase.getInstance().getReference("Chats");
     reference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             usersList.clear();
             for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                 Chat chat = snapshot.getValue(Chat.class);

                 if(chat.getSender().equals(fuser.getUid())){
                     usersList.add(chat.getReceiver());
                 }
                 if(chat.getReceiver().equals(fuser.getUid())){
                     usersList.add(chat.getSender());
                 }
             }

             readchats();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
        return view;
    }

    private void readchats(){
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                     //display 1 user from chats
                    for(String id:usersList){
                        if(user.getUserId().equals(id))
                        {
                            if(mUsers.size()!=0)
                            {
                                for(User user1:mUsers){
                                    if(!user.getUserId().equals(user1.getUserId())){
                                        mUsers.add(user);
                                    }
                                }
                            }
                         /* else{
                                mUsers.add(user);
                            }*/

                        }
                    }
                }

                userAdapter=new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
