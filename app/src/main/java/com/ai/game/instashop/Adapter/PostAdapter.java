package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Activity.CommentActivity;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.NotificationModel;
import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.Model.StoryModel;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private ArrayList<PostModel> postModelList;
    Context context;

    public PostAdapter(ArrayList<PostModel> postModelList, Context context) {
        this.postModelList = postModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel model = postModelList.get(position);

        Picasso.get().load(model.getPostImage()).placeholder(R.drawable.ic_placeholder_post).into(holder.post);
        if(!model.getPostDescription().isEmpty()) {
            holder.description.setText(model.getPostDescription());
            holder.description.setVisibility(View.VISIBLE);
        }

        holder.like.setText(model.getLikeCount() + "");
        holder.comment.setText(model.getCommentCount() + "");

        FirebaseDatabase.getInstance().getReference().child("Posts")
                .child(model.getPostId())
                .child("Likes")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_filled, 0, 0, 0);
                        }
                        else{
                            holder.like.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseDatabase.getInstance().getReference().child("Posts")
                                            .child(model.getPostId())
                                            .child("Likes")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("Posts")
                                                            .child(model.getPostId())
                                                            .child("likeCount")
                                                            .setValue(model.getLikeCount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    NotificationModel notification = new NotificationModel();
                                                                    notification.setNotificationById(FirebaseAuth.getInstance().getUid());
                                                                    notification.setNotificationAtTime(new Date().getTime());
                                                                    notification.setNotificationType("like");
                                                                    notification.setPostId(model.getPostId());
                                                                    notification.setPostedBy(model.getPostedById());

                                                                    FirebaseDatabase.getInstance().getReference().child("Notifications")
                                                                            .child(model.getPostedById())
                                                                            .push().setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void unused) {
                                                                                    holder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_filled, 0, 0, 0);
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getPostedById()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    holder.name.setText(user.getName());
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
                    holder.bio.setText(user.getProfession());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId", model.getPostId());
                intent.putExtra("postedById", model.getPostedById());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile, post, save;
        public TextView name, bio, like, comment, share;
        public ReadMoreTextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            post = itemView.findViewById(R.id.post_image);
            save = itemView.findViewById(R.id.savePost);
            name = itemView.findViewById(R.id.userName);
            bio = itemView.findViewById(R.id.userBio);
            like = itemView.findViewById(R.id.post_like);
            comment = itemView.findViewById(R.id.post_comment);
            share = itemView.findViewById(R.id.post_share);
            description = itemView.findViewById(R.id.postDescription);
        }
    }
}
