package com.ai.game.instashop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.game.instashop.Adapter.CommentAdapter;
import com.ai.game.instashop.Model.CommentModel;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.NotificationModel;
import com.ai.game.instashop.Model.PostModel;
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

import kr.co.prnd.readmore.ReadMoreTextView;

public class CommentActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Intent intent;
    String postId, postedById;
    ImageView postImage, profileImage;
    TextView username;
    ReadMoreTextView description;
    EditText commentBox;

    ArrayList<CommentModel> list;
    RecyclerView commentRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        intent = getIntent();
        postId = intent.getStringExtra("postId");
        postedById = intent.getStringExtra("postedById");
        postImage = findViewById(R.id.comment_post);
        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.comment_name);
        description = findViewById(R.id.postDescription);
        commentBox = findViewById(R.id.comment_messageBox);
        commentRv = findViewById(R.id.comment_rv);

        list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        database.getReference().child("Posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PostModel model = snapshot.getValue(PostModel.class);
                    if(!model.getPostDescription().isEmpty()){
                        description.setText(model.getPostDescription());
                        description.setVisibility(View.VISIBLE);
                    }
                    Picasso.get().load(model.getPostImage()).placeholder(R.drawable.ic_placeholder_post).into(postImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(postedById).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(profileImage);
                    username.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.send_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!commentBox.getText().toString().isEmpty()){
                    CommentModel comment = new CommentModel(new Date().getTime(), commentBox.getText().toString(), mAuth.getUid());
                    commentBox.setText("");
                    database.getReference().child("Posts")
                            .child(postId)
                            .child("Comments")
                            .push()
                            .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Posts")
                                            .child(postId)
                                            .child("commentCount").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    int count = 0;
                                                    if(snapshot.exists()) {
                                                        count = snapshot.getValue(Integer.class);
                                                    }
                                                    database.getReference().child("Posts")
                                                            .child(postId)
                                                            .child("commentCount")
                                                            .setValue(count + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    NotificationModel notification = new NotificationModel();
                                                                    notification.setNotificationById(mAuth.getUid());
                                                                    notification.setNotificationAtTime(new Date().getTime());
                                                                    notification.setPostedBy(postedById);
                                                                    notification.setPostId(postId);
                                                                    notification.setNotificationType("comment");

                                                                    database.getReference().child("Notifications").child(postedById)
                                                                            .push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {

                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                }
                            });
                }
            }
        });

        findViewById(R.id.back_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CommentAdapter adapter = new CommentAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        commentRv.setLayoutManager(linearLayoutManager);
        commentRv.setAdapter(adapter);

        database.getReference().child("Posts").child(postId).child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    CommentModel model = data.getValue(CommentModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}