package com.ebookfrenzy.a8stickermessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceiveNotificationActivity extends AppCompatActivity {

    TextView tv_name_notification;
    TextView tv_time_notification;
    ImageView iv_sticker_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);
        tv_name_notification = findViewById(R.id.tv_name_notif);
        tv_time_notification = findViewById(R.id.tv_time_notif);
        iv_sticker_notification = findViewById(R.id.iv_sticker_notification);
    }
}