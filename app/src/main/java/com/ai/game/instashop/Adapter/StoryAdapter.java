package com.ai.game.instashop.Adapter;

// this class contains main code responsible for the working of the recycler view
// it binds the data from data source to recycler view

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder>{
    private ArrayList<StoryModel> storyModelList;
    Context context;

    public StoryAdapter(ArrayList<StoryModel> storyModelList, Context context) {
        this.storyModelList = storyModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {         // this is basically to use the design of the layout
        View view = LayoutInflater.from(context).inflate(R.layout.stories_rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoryModel storyModel = storyModelList.get(position);
        holder.storyImage.setImageResource(storyModel.getStory());
        holder.profile.setImageResource(storyModel.getProfile());
        holder.name.setText(storyModel.getName());
    }

    @Override
    public int getItemCount() { return storyModelList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView storyImage, profile;
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storyImage = itemView.findViewById(R.id.storyImage);
            profile = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.storyName);
        }
    }
}
