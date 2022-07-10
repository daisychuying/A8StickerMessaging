package com.ebookfrenzy.a8stickermessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ebookfrenzy.a8stickermessaging.Adapter.ContactListAdapter;
import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    Toolbar toolbar;

    Intent intent;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReferencer;
    ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);


        // set up contactlist toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        int stickerid = intent.getIntExtra("id", -1);

        //Instantiate the arraylist
        contactLists = new ArrayList<>();

        contactListRecyclerView = findViewById(R.id.rv_contactListRecyclerView);
        contactListRecyclerView.setHasFixedSize(true);
        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReferencer = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferencer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactLists.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        User contactUser = dataSnapshot.getValue(User.class);
                        if (!contactUser.getId().equals(firebaseUser.getUid())){
                            contactLists.add(contactUser);
                        }
                }
                contactListAdapter = new ContactListAdapter(contactLists, ContactListActivity.this, stickerid);
                contactListRecyclerView.setAdapter(contactListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}