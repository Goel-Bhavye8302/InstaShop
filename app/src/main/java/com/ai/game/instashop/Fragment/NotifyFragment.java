package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.ai.game.instashop.Adapter.NotifyViewPagerAdapter;
import com.ai.game.instashop.R;
import com.google.android.material.tabs.TabLayout;


public class NotifyFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new NotifyViewPagerAdapter(getChildFragmentManager()));
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}