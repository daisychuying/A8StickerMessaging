package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.ebookfrenzy.a8stickermessaging.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>{
    private final List<User> userList;
    private final Context context;

    public ContactListAdapter(List<User> user, Context context) {
        this.userList = user;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, null);
        return new ContactListAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindThisData(userList.get(position));
        holder.contactName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addStickerCount(id);
            }
        });
    }

    public void addStickerCount(DatabaseReference databaseReference, Integer id){
        databaseReference.child("Users")
                .child("StickerCount")
                .child(String.valueOf(id))
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        User user = currentData.getValue(User.class);

                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                    }
                });
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
