package com.ebookfrenzy.a8stickermessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ebookfrenzy.a8stickermessaging.Adapter.ContactListAdapter;
import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    RecyclerView contactListRecyclerView;
    List<User> contactLists;

    DatabaseReference databaseReferencer;
    ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        //Instantiate the arraylist
        contactLists = new ArrayList<>();

        contactListRecyclerView = findViewById(R.id.rv_contactListRecyclerView);
        contactListRecyclerView.setHasFixedSize(true);
        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactListRecyclerView.setAdapter(new ContactListAdapter(contactLists, this));


        databaseReferencer = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactLists.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        User contactUser = dataSnapshot.getValue(User.class);
                        contactLists.add(contactUser);
                        contactListAdapter = new ContactListAdapter(contactLists, ContactListActivity.this);
                        contactListRecyclerView.setAdapter(contactListAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}