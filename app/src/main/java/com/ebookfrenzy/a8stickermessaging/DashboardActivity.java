package com.ebookfrenzy.a8stickermessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebookfrenzy.a8stickermessaging.Fragments.HistoryFragment;
import com.ebookfrenzy.a8stickermessaging.Fragments.StickerFragment;
import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView username;
    ImageView logout;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        username = findViewById(R.id.username);

        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                finish();
            }
        });


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // set Dashboard current username
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fragment View
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new StickerFragment(), "Stickers");
        viewPagerAdapter.addFragment(new HistoryFragment(), "History");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.emoji1:
                Intent intent1 = new Intent(this, ContactListActivity.class);
                intent1.putExtra("id", 0);
                startActivity(intent1);
                break;
            case R.id.emoji2:
                Intent intent2 = new Intent(this, ContactListActivity.class);
                intent2.putExtra("id", 1);
                startActivity(intent2);
                break;
            case R.id.emoji3:
                Intent intent3 = new Intent(this, ContactListActivity.class);
                intent3.putExtra("id", 2);
                startActivity(intent3);
                break;
            case R.id.emoji4:
                Intent intent4 = new Intent(this, ContactListActivity.class);
                intent4.putExtra("id", 3);
                startActivity(intent4);
                break;
            case R.id.emoji5:
                Intent intent5 = new Intent(this, ContactListActivity.class);
                intent5.putExtra("id", 4);
                startActivity(intent5);
                break;
            case R.id.emoji6:
                Intent intent6 = new Intent(this, ContactListActivity.class);
                intent6.putExtra("id", 5);
                startActivity(intent6);
                break;

        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }
}