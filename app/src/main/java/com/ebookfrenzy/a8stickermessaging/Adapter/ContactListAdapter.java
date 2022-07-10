package com.ebookfrenzy.a8stickermessaging.Adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ebookfrenzy.a8stickermessaging.DashboardActivity;
import com.ebookfrenzy.a8stickermessaging.Model.User;
import com.ebookfrenzy.a8stickermessaging.R;
import com.ebookfrenzy.a8stickermessaging.ReceiveNotificationActivity;
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
    private String CHANNEL_ID = "NUMAD_22SU_Team11";

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
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
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
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

    /**
     * Send sticker feature with update on firebase
     * @param senderId
     * @param senderName
     * @param receiverId
     * @param receiverName
     * @param stickerid
     */

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
                    addStickerCount(senderId, stickerid);
                    createNotificationChannel();
                    sendNotification();
                    context.startActivity(new Intent(context, DashboardActivity.class));
                } else {
                    Toast.makeText(context, "Unable to send!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Add one to sender's sticker hashmap associated with stickerid
     * @param senderId
     * @param stickerid
     */
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
    }

    /** newly Added */
    public void sendNotification(){
        Intent intent = new Intent(context, ReceiveNotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
        PendingIntent callIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(),
                new Intent(context, ReceiveNotificationActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New mail from")
                .setContentText("Subject")
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher_foreground, "Call", callIntent)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
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
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
