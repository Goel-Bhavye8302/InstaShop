package com.ai.game.instashop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserChattingActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chatting);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String chatUserId = getIntent().getStringExtra("userId");

        TextView username = findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("name"));
        ImageView profile = findViewById(R.id.profile_image);
        Picasso.get().load(getIntent().getStringExtra("profilePic")).placeholder(R.drawable.user2).into(profile);

        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}