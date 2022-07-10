package com.ebookfrenzy.a8stickermessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ebookfrenzy.a8stickermessaging.Model.StickerMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText et_username;
    Button btn_signIn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.editTextUserName);

        btn_signIn = findViewById(R.id.buttonSignIn);

        // Connect with Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Redirect to Dashboard Activity after button click
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String useremail = username + "@madCourse.edu";

                if (username.equals("")) {
                    Toast.makeText(MainActivity.this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(useremail, "000000")
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        register(username, useremail);
                                    }
                                }
                            });
                }
            }
        });

    }

    /**
     * register account on firebase if username doesnt exist
     * @param username
     * @param useremail
     */

    private void register(String username, String useremail) {
        firebaseAuth.createUserWithEmailAndPassword(useremail, "000000")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            // sticker sent Hashmap
                            HashMap<String, Integer> stickerCount = new HashMap<>();
                            for (int i = 0; i < new StickerMap().size(); i++){
                                stickerCount.put(String.valueOf(i) + "_key", 0);
                            }

                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("stickerCount", stickerCount);

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to login with this username!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // user auto login
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        }
    }
}