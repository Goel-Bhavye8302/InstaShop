package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.game.instashop.Adapter.ProfileFriendsAdapter;
import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.Model.ProfileFriendsModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    RecyclerView friendsRV;
    ArrayList<ProfileFriendsModel> friendsModelList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        friendsRV = view.findViewById(R.id.profile_friendsRV);

        friendsModelList = new ArrayList<>();
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));
        friendsModelList.add(new ProfileFriendsModel(R.drawable.gradient));

        ProfileFriendsAdapter adapter = new ProfileFriendsAdapter(friendsModelList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        friendsRV.setLayoutManager(linearLayoutManager);
        friendsRV.setNestedScrollingEnabled(false);
        friendsRV.setAdapter(adapter);

        return view;
    }
}