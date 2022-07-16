package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.game.instashop.Adapter.NotificationAdapter;
import com.ai.game.instashop.Model.NotificationModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<NotificationModel> list;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.notification_rv);
        list = new ArrayList<>();
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story! Send a Message?", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story! Send a Message?", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story! Send a Message?", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story! Send a Message?", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story! Send a Message?", "just now"));
        list.add(new NotificationModel(R.drawable.gradient, "<b>Mukul</b> mentioned you in a story!", "just now"));

        NotificationAdapter adapter = new NotificationAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}