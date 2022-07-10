package com.ebookfrenzy.a8stickermessaging.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.ebookfrenzy.a8stickermessaging.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StickerFragment extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);

        TextView sticker1Freq = view.findViewById(R.id.TVEmoji_1_Freq);
        TextView sticker2Freq = view.findViewById(R.id.TVEmoji_2_Freq);
        TextView sticker3Freq = view.findViewById(R.id.TVEmoji_3_Freq);
        TextView sticker4Freq = view.findViewById(R.id.TVEmoji_4_Freq);
        TextView sticker5Freq = view.findViewById(R.id.TVEmoji_5_Freq);
        TextView sticker6Freq = view.findViewById(R.id.TVEmoji_6_Freq);

        ArrayList<TextView> stickersToUpdate = new ArrayList<>(Arrays.asList(sticker1Freq, sticker2Freq, sticker3Freq, sticker4Freq, sticker5Freq, sticker6Freq));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                HashMap<String, Integer> stickerCount = user.getStickerCount();
                for (int i = 0; i < 6; i++) {
                    int count = stickerCount.get(i + "_key");
                    stickersToUpdate.get(i).setText(String.valueOf(count));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}