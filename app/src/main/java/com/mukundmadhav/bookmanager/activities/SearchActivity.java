package com.mukundmadhav.bookmanager.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukundmadhav.bookmanager.Models.Book;
import com.mukundmadhav.bookmanager.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

public class SearchActivity extends BaseActivity {
    ListView mListView;
    ArrayList<String> mArrayList1 = new ArrayList<>();
    ArrayList<Book> mBookArrayList = new ArrayList<>();
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mListView = findViewById(R.id.listView);

        downloadBooksNames();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mBookArrayList.get(position);
                Intent intent = new Intent(SearchActivity.this, BookDetails.class);
                intent.putExtra("picture", book.getPicture());
                intent.putExtra("postKey", book.getPostKey());
                intent.putExtra("price", book.getPrice());
//                intent.putExtra("timeStamp", book.getTimeStamp().toString());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("userId", book.getUserId());
                intent.putExtra("userPic", book.getUserPic());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);

        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> mArrayList2 = new ArrayList<>();

                for (int i = 0; i < mArrayList1.size(); i++) {
                    String Name = mArrayList1.get(i).toLowerCase();
                    String q = mArrayList1.get(i);
                    int size = Name.length();
                    int qSize = newText.length();
                    if (qSize <= size && qSize > 0) {
                        if (Name.substring(0, qSize).equals(newText)) {
                            mArrayList2.add(q);
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, mArrayList2);
                mListView.setAdapter(arrayAdapter);

                return true;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });

        return true;
    }

    private void downloadBooksNames() {
        FirebaseDatabase.getInstance().getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book mBook = new Book();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mBook.setPicture(ds.child("picture").getValue(String.class));
                    mBook.setPostKey(ds.child("postKey").getValue(String.class));
                    mBook.setPrice(ds.child("price").getValue(String.class));
//                    mBook.setTimeStamp(ds.child("timeStamp").getValue(String.class));
                    mBook.setTitle(ds.child("title").getValue(String.class));
                    mBook.setUserId(ds.child("userId").getValue(String.class));
                    mBook.setUserPic(ds.child("userPic").getValue(String.class));

                    mBookArrayList.add(mBook);
                    mArrayList1.add(ds.child("title").getValue(String.class));
                }
                activateToolbar(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
