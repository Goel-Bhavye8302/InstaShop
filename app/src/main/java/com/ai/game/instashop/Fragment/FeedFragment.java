package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.game.instashop.Adapter.PostAdapter;
import com.ai.game.instashop.Adapter.StoryAdapter;
import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    RecyclerView storyRV, postRV;
    ArrayList<StoryModel> storyModelList;
    ArrayList<PostModel> postModelList;

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        storyRV = view.findViewById(R.id.story_rv);
        postRV = view.findViewById(R.id.post_rv);

        storyModelList = new ArrayList<>();
        postModelList = new ArrayList<>();

        StoryAdapter storyAdapter = new StoryAdapter(storyModelList, getContext());
        PostAdapter postAdapter = new PostAdapter(postModelList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);

        postRV.setLayoutManager(linearLayoutManager1);
        postRV.setNestedScrollingEnabled(false);



        postRV.setAdapter(postAdapter);

        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyRV.setAdapter(storyAdapter);

        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        PostModel model = data.getValue(PostModel.class);
                        model.setPostId(data.getKey());
                        postModelList.add(model);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}