package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.ebookfrenzy.a8stickermessaging.R;

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
        User contact = userList.get(position);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView contactName;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            this.contactName = itemView.findViewById(R.id.username);
        }

        public void bindThisData(User userToBind){
            contactName.setText(userToBind.getUsername());
        }
    }
}
