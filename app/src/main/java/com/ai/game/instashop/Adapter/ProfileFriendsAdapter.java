package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.ProfileFriendsModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;

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
        holder.profile.setImageResource(friendsModel.getProfile_image());
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
