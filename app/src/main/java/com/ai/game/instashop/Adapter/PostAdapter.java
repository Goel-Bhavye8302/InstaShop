package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

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
        PostModel storyModel = postModelList.get(position);
        holder.profile.setImageResource(storyModel.getProfile());
        holder.post.setImageResource(storyModel.getPost());
        holder.save.setImageResource(storyModel.getSave());
        holder.name.setText(storyModel.getName());
        holder.bio.setText(storyModel.getBio());
        holder.like.setText(storyModel.getLike());
        holder.comment.setText(storyModel.getComment());
        holder.share.setText(storyModel.getShare());
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile, post, save;
        public TextView name, bio, like, comment, share;

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
        }
    }
}
