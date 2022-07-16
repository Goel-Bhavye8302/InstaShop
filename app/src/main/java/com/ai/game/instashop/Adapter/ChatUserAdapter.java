package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.ai.game.instashop.Activity.UserChattingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder>{

    Context context;
    ArrayList<Firebase_User> list;

    public ChatUserAdapter(Context context, ArrayList<Firebase_User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_user_rv_layout, parent, false);
        return new ChatUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Firebase_User user = list.get(position);
        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
        holder.name.setText(user.getName());

        FirebaseDatabase.getInstance().getReference().child("Chats")
                .child(FirebaseAuth.getInstance().getUid() + user.getUid())
                .orderByChild("timeStamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot data : snapshot.getChildren()){
                                holder.message.setText(data.child("message").getValue(String.class));

                                DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
                                String dateString = dateFormat.format(new Date(data.child("timeStamp").getValue(Long.class))).toString();

                                holder.time.setText(dateString.toLowerCase(Locale.ROOT));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserChattingActivity.class);
                intent.putExtra("userId", user.getUid());
                intent.putExtra("profilePic", user.getProfilePhoto());
                intent.putExtra("name", user.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile;
        public TextView name, message, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            message = itemView.findViewById(R.id.chatMessage);
            time = itemView.findViewById(R.id.chatTime);
            name = itemView.findViewById(R.id.chatName);
        }
    }
}
