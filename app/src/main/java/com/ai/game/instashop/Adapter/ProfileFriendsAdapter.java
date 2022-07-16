package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.ProfileFriendsModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFriendsAdapter extends RecyclerView.Adapter<ProfileFriendsAdapter.ViewHolder>{

    private ArrayList<ProfileFriendsModel> friendsModelList;
    Context context;

    public ProfileFriendsAdapter(ArrayList<ProfileFriendsModel> friendsModelList, Context context) {
        this.friendsModelList = friendsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_rv_layout, parent, false);
        return new ProfileFriendsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProfileFriendsModel friendsModel = friendsModelList.get(position);
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(friendsModel.getFollowedById())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Firebase_User user = snapshot.getValue(Firebase_User.class);
                        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return friendsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
        }
    }
}
