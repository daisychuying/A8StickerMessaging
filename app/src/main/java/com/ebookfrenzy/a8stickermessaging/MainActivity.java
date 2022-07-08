package com.ebookfrenzy.a8stickermessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText username;
    Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextUserName);
        btn_signIn = findViewById(R.id.buttonSignIn);

        //Connect with Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Redirect to Dashboard Activity after button click
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }
        });


    }
}