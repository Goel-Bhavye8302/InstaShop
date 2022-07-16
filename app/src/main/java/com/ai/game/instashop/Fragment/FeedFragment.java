package com.ai.game.instashop.Fragment;

import android.os.Bundle;

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

import java.util.ArrayList;

public class FeedFragment extends Fragment {

    RecyclerView storyRV, postRV;
    ArrayList<StoryModel> storyModelList;
    ArrayList<PostModel> postModelList;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        storyRV = view.findViewById(R.id.story_rv);
        postRV = view.findViewById(R.id.post_rv);

        storyModelList = new ArrayList<>();
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));
        storyModelList.add(new StoryModel(R.drawable.gradient, R.drawable.user2, "Rahul"));

        StoryAdapter adapter = new StoryAdapter(storyModelList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);
        storyRV.setAdapter(adapter);

        postModelList = new ArrayList<>();
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));
        postModelList.add(new PostModel(R.drawable.user2, R.drawable.gradient, R.drawable.ic_save_post,"Rahul", "Jai Hind", "3122", "200", "94"));

        PostAdapter adapter1 = new PostAdapter(postModelList, getContext());
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        postRV.setLayoutManager(linearLayoutManager1);
        postRV.setNestedScrollingEnabled(false);
        postRV.setAdapter(adapter1);

        return view;
    }
}