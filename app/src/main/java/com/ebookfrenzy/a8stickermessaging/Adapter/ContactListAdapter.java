package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ContactListAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindThisData(userList.get(position));
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
            userimage.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
