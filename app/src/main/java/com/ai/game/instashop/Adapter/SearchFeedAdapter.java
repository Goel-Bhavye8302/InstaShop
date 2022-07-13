package com.ai.game.instashop.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ai.game.instashop.Fragment.FeedFragment;
import com.ai.game.instashop.Fragment.HomeFragment;
import com.ai.game.instashop.Fragment.SearchFragment;

public class SearchFeedAdapter extends FragmentPagerAdapter {
    public SearchFeedAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FeedFragment();
            case 1 : return new SearchFragment();
            default : return new FeedFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
