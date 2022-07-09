package com.ebookfrenzy.a8stickermessaging.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.a8stickermessaging.Adapter.HistoryAdapter;
import com.ebookfrenzy.a8stickermessaging.Model.History;
import com.ebookfrenzy.a8stickermessaging.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<History> historyList;

    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        historyList = new ArrayList<>();

        getHistory();

        historyAdapter = new HistoryAdapter(getContext(), historyList);
        recyclerView.setAdapter(historyAdapter);

        return view;
    }

    private void getHistory() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("History");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    History history = dataSnapshot.getValue(History.class);

                    if (history.getSender().equals(firebaseUser.getUid()) || history.getReceiver().equals(firebaseUser.getUid())) {
                        historyList.add(history);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}