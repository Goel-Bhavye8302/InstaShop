package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.game.instashop.Adapter.StoryAdapter;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView storyRV;
    ArrayList<StoryModel> storyModelList;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        storyRV = view.findViewById(R.id.story_rv);
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
        return view;
    }
}