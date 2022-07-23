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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.Model.UserStoriesModel;
import com.ai.game.instashop.R;
import com.devlomi.circularstatusview.CircularStatusView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

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

        if(!storyModel.getStoryList().isEmpty()){
            Picasso.get().load(storyModel.getStoryList().get(storyModel.getStoryList().size() - 1).getStory()).placeholder(R.drawable.ic_placeholder_post).into(holder.storyImage);
            holder.statusView.setPortionsCount(storyModel.getStoryList().size());
        }

        FirebaseDatabase.getInstance().getReference().child("Users").child(storyModel.getStoryById()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
                    holder.name.setText(user.getName());

                    holder.storyImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<MyStory> list = new ArrayList<>();
                            for(UserStoriesModel data : storyModel.getStoryList()){
                                list.add(new MyStory(data.getStory(), new Date(data.getStoryAtTime())));
                            }

                            new StoryView.Builder(((AppCompatActivity)context).getSupportFragmentManager())
                                    .setStoriesList(list) // Required
                                    .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                                    .setTitleText(user.getName()) // Default is Hidden
                                    .setSubtitleText(user.getProfession()) // Default is Hidden
                                    .setTitleLogoUrl(user.getProfilePhoto()) // Default is Hidden
                                    .setStoryClickListeners(new StoryClickListeners() {
                                        @Override
                                        public void onDescriptionClickListener(int position) {
                                            //your action
                                        }

                                        @Override
                                        public void onTitleIconClickListener(int position) {
                                            //your action
                                        }
                                    }) // Optional Listeners
                                    .build() // Must be called before calling show method
                                    .show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() { return storyModelList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView storyImage, profile;
        public TextView name;
        public CircularStatusView statusView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storyImage = itemView.findViewById(R.id.storyImage);
            profile = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.storyName);
            statusView = itemView.findViewById(R.id.statusCount);
        }
    }
}
