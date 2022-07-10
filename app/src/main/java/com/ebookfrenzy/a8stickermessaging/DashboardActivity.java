package com.ebookfrenzy.a8stickermessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebookfrenzy.a8stickermessaging.Adapter.ContactListAdapter;
import com.ebookfrenzy.a8stickermessaging.Fragments.HistoryFragment;
import com.ebookfrenzy.a8stickermessaging.Fragments.StickerFragment;
import com.ebookfrenzy.a8stickermessaging.Model.History;
import com.ebookfrenzy.a8stickermessaging.Model.StickerMap;
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
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView username;
    ImageView logout;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceNotification;
    private String CHANNEL_ID = "NUMAD_22SU_Team11";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        username = findViewById(R.id.username);

        // user log out feature
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

        /** Newly Added */
        databaseReferenceNotification = FirebaseDatabase.getInstance().getReference("History");
        databaseReferenceNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userid = firebaseUser.getUid();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    History history = dataSnapshot.getValue(History.class);

                    if (history.getReceiverId().equals(userid)){
                        if (!history.isNotified()) {
                            Integer stickerid = history.getSticker();
                            String senderName = history.getSenderName();
                            createNotificationChannel();
                            sendNotification(stickerid, senderName);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("notified", true);
                            dataSnapshot.getRef().updateChildren(hashMap);
                        }
                    }
                }

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

    /** newly Added */
    public void sendNotification(Integer stickerid, String senderName){
        Intent intent = new Intent(this, ReceiveNotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent receiveIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                new Intent(this, ReceiveNotificationActivity.class), PendingIntent.FLAG_IMMUTABLE);
//        Notification.Action action = new Notification.Action.Builder(R.drawable.ic_launcher_foreground, "Check", receiveIntent).build();

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), StickerMap.getStickerId(stickerid)))
                .setContentTitle(senderName)
                .setContentText("Sent you a new sticker!")
                .setAutoCancel(true)
//                .addAction(action)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());

    }

    /** Newly Added */
    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "Notification Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void onClick(View view){
        Intent intent = new Intent(this, ContactListActivity.class);
        int stickerid;
        switch (view.getId()){
            case R.id.emoji1:
                stickerid = 0;
                break;
            case R.id.emoji2:
                stickerid = 1;
                break;
            case R.id.emoji3:
                stickerid = 2;
                break;
            case R.id.emoji4:
                stickerid = 3;
                break;
            case R.id.emoji5:
                stickerid = 4;
                break;
            case R.id.emoji6:
                stickerid = 5;
                break;
            default:
                stickerid = -1;
                break;
        }
        intent.putExtra("id", stickerid);
        startActivity(intent);
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