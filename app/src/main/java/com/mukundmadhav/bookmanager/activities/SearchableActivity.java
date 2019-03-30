package com.mukundmadhav.bookmanager.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukundmadhav.bookmanager.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SearchableActivity extends AppCompatActivity {
    ArrayList<String> mArrayList1 = new ArrayList<>();
    ListView searchList;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchList = findViewById(R.id._searchList);
        search = findViewById(R.id._search);

        downloadBooksNames();

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> mArrayList2 = new ArrayList<>();

                for (int i = 0; i < mArrayList1.size(); i++) {
                    String Name = mArrayList1.get(i);
                    int size = Name.length();
                    String NameQuery = s.toString();
                    int qSize = NameQuery.length();
                    if (qSize <= size && qSize > 0) {
                        if (Name.contains(NameQuery)) {
                            mArrayList2.add(Name);
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchableActivity.this, android.R.layout.simple_list_item_1, mArrayList2);
                searchList.setAdapter(arrayAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void downloadBooksNames() {
        FirebaseDatabase.getInstance().getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mArrayList1.add(ds.child("title").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
