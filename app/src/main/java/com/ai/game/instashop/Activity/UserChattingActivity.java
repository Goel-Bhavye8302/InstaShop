package com.ai.game.instashop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.game.instashop.Adapter.ChatMessagesAdapter;
import com.ai.game.instashop.Model.MessagesModel;
import com.ai.game.instashop.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserChattingActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ArrayList<MessagesModel> messagesList;
    RecyclerView messageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chatting);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        final String receiverId = getIntent().getStringExtra("userId");
        final String senderId = mAuth.getUid();

        messageRecyclerView = findViewById(R.id.chat_message_rv);

        TextView username = findViewById(R.id.username);
        username.setText(getIntent().getStringExtra("name"));
        ImageView profile = findViewById(R.id.profile_image);
        Picasso.get().load(getIntent().getStringExtra("profilePic")).placeholder(R.drawable.user2).into(profile);

        messagesList = new ArrayList<>();
        ChatMessagesAdapter adapter = new ChatMessagesAdapter(this, messagesList);
        messageRecyclerView.setAdapter(adapter);

        database.getReference().child("Chats").child(senderId + receiverId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    if(data.exists()){
                        MessagesModel model = data.getValue(MessagesModel.class);
                        messagesList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
                messageRecyclerView.scrollToPosition(messagesList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ((EditText)findViewById(R.id.messageBox)).getText().toString();
                MessagesModel model = new MessagesModel(mAuth.getUid(), message.trim(), new Date().getTime());
                ((EditText)findViewById(R.id.messageBox)).setText("");

                database.getReference().child("Chats").child(senderId + receiverId).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(receiverId + senderId).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        messageRecyclerView.setLayoutManager(layoutManager);


        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}