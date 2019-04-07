package com.mukundmadhav.bookmanager.activities;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukundmadhav.bookmanager.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

public class SearchActivity extends BaseActivity {
    ListView mListView;
    ArrayList<String> mArrayList1 = new ArrayList<>();
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mListView = findViewById(R.id.listView);

        downloadBooksNames();
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
                    int size = Name.length();
                    int qSize = newText.length();
                    if (qSize <= size && qSize > 0) {
                        if (Name.contains(newText.toLowerCase())) {
                            mArrayList2.add(Name);
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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
