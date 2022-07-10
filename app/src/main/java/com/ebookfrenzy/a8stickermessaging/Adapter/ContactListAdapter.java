package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ebookfrenzy.a8stickermessaging.DashboardActivity;
import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.ebookfrenzy.a8stickermessaging.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>{
    private final List<User> userList;
    private final Context context;
    private final int stickerid;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    public ContactListAdapter(List<User> user, Context context, int stickerid) {
        this.userList = user;
        this.context = context;
        this.stickerid = stickerid;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, null);
        return new ContactListAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final User contact = userList.get(position);
        holder.bindThisData(userList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiverId = contact.getId();
                String receiverName = contact.getUsername();

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String senderId = firebaseUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(senderId);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        String senderName = user.getUsername();
                        sendSticker(senderId, senderName, receiverId, receiverName, stickerid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void sendSticker(String senderId, String senderName, String receiverId, String receiverName, int stickerid) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderId", senderId);
        hashMap.put("senderName", senderName);
        hashMap.put("receiverId", receiverId);
        hashMap.put("receiverName", receiverName);
        hashMap.put("sticker", stickerid);
        hashMap.put("timeStamp", System.currentTimeMillis());

        databaseReference.child("History").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Send successfully!", Toast.LENGTH_SHORT).show();
                    /**
                     * unresolve issue continuing add one to sticker without stopping
                     * Jiayi Nie
                     */
                    //addStickerCount(senderId, stickerid);
                    context.startActivity(new Intent(context, DashboardActivity.class));
                } else {
                    Toast.makeText(context, "Unable to send!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addStickerCount(String senderId, int stickerid) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(senderId)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        User user = currentData.getValue(User.class);

                        if (user == null) {
                            return Transaction.success(currentData);
                        }

                        user.addStickerCount(stickerid + "_key");
                        currentData.setValue(user);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });

//        databaseReference.child("Users").child(senderId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        User user = snapshot.getValue(User.class);
//                        user.addStickerCount(stickerid + "_key");
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("stickerCount", user.getStickerCount());
//                        snapshot.getRef().updateChildren(hashMap);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView contactName;
        public ImageView userimage;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userimage = itemView.findViewById(R.id.user_image);
            this.contactName = itemView.findViewById(R.id.username);
        }

        public void bindThisData (User contactToBind) {
            contactName.setText(contactToBind.getUsername());
        }
    }
}
