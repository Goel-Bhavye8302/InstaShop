package com.ai.game.instashop.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ai.game.instashop.Fragment.NotificationsFragment;
import com.ai.game.instashop.Fragment.NotifyFragment;
import com.ai.game.instashop.Fragment.RequestsFragment;

public class NotifyViewPagerAdapter extends FragmentPagerAdapter {

    public NotifyViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new NotificationsFragment();
            case 1 : return new RequestsFragment();
            default : return new NotificationsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0){
            title = "NOTIFICATIONS";
        }
        else if(position == 1){
            title = "REQUESTS";
        }
        return title;
    }
}
