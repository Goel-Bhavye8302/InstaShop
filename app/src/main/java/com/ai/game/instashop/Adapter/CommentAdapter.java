package com.ai.game.instashop.Adapter;

import static android.text.format.DateUtils.getRelativeTimeSpanString;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.CommentModel;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    ArrayList<CommentModel> list;
    Context context;

    public CommentAdapter(Context context, ArrayList<CommentModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_rv_layout, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModel model = list.get(position);

        holder.time.setText(TimeAgo.using(model.getCommentTime()));
//        holder.time.setText(getRelativeTimeSpanString(model.getCommentTime()));

        FirebaseDatabase.getInstance().getReference().child("Users").child(model.getCommentedById()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
                    holder.comment.setText(Html.fromHtml("<b><font color=#2B2D42>" + user.getName() + "</font></b> " + model.getComment()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView comment, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            comment = itemView.findViewById(R.id.commentMessage);
            time = itemView.findViewById(R.id.commentTime);
        }
    }
}
