package com.mukundmadhav.bookmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukundmadhav.bookmanager.Adapters.BookAdapter;
import com.mukundmadhav.bookmanager.Models.Book;
import com.mukundmadhav.bookmanager.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PostsFragment extends androidx.fragment.app.Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<Book> bookList;

    private String mParam1;
    private String mParam2;


    RecyclerView bookRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BookAdapter bookAdapter;

    private OnFragmentInteractionListener mListener;

    public PostsFragment() {
        // Required empty public constructor
    }

    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        bookRecyclerView = view.findViewById(R.id.posts_RView);
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookRecyclerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookList = new ArrayList<>();

                for(DataSnapshot bookSanp : dataSnapshot.getChildren()) {

                    Book book = bookSanp.getValue(Book.class);
                    bookList.add(book);

                }

                bookAdapter = new BookAdapter(getActivity(), bookList);
                bookRecyclerView.setAdapter(bookAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
