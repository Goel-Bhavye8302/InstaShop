package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Activity.CommentActivity;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.NotificationModel;
import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationModel> notificationModelList;
    Context context;

    public NotificationAdapter(ArrayList<NotificationModel> notificationModelList, Context context) {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notify_rv_layout, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel model = notificationModelList.get(position);

        Intent intent = new Intent(context, CommentActivity.class);
        if(model.isNotificationOpened()){
            holder.layout.setBackgroundColor(context.getColor(R.color.transparent));
        }

        holder.time.setText(TimeAgo.using(model.getNotificationAtTime()));

        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getNotificationById()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
                    if(model.getNotificationType().equals("follow")){
                        holder.notification.setText(Html.fromHtml("<b><font color=#2B2D42>" + user.getName() + "</font></b> " + "started following you!"));
                    }
                    else if(model.getNotificationType().equals("comment")){
                        holder.notification.setText(Html.fromHtml("<b><font color=#2B2D42>" + user.getName() + "</font></b> " + "commented on your post!"));
                    }
                    else if(model.getNotificationType().equals("like")){
                        holder.notification.setText(Html.fromHtml("<b><font color=#2B2D42>" + user.getName() + "</font></b> " + "liked your post"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!model.getNotificationType().equals("follow")){
                    holder.layout.setBackgroundColor(context.getColor(R.color.transparent));

                    FirebaseDatabase.getInstance().getReference().child("Notifications").child(model.getPostedBy()).child(model.getNotificationId()).child("notificationOpened").setValue(true);

                    intent.putExtra("postId", model.getPostId());
                    intent.putExtra("postedById", model.getPostedBy());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile;
        public TextView notification, time;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            notification = itemView.findViewById(R.id.notification_message);
            time = itemView.findViewById(R.id.notification_time);
            layout = itemView.findViewById(R.id.notification_open);
        }
    }
}
