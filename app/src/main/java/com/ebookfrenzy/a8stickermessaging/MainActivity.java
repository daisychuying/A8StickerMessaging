package com.ebookfrenzy.a8stickermessaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    EditText et_username;
    Button btn_signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.editTextUserName);

        btn_signIn = findViewById(R.id.buttonSignIn);

        // Connect with Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Redirect to Dashboard Activity after button click
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(MainActivity.this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("username", username);
                    mDatabase.child("Users").push().setValue(hashMap);
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                }
            }
        });

    }

}