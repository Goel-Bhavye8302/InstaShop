package com.ai.game.instashop.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Adapter.SearchUsersAdapter;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Firebase_User> list;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        EditText search = view.findViewById(R.id.editTextSearchUser);

        recyclerView = view.findViewById(R.id.search_rv);
        list = new ArrayList<>();

        SearchUsersAdapter adapter = new SearchUsersAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                    for(DataSnapshot data : snapshot.getChildren()){
                        Firebase_User user = data.getValue(Firebase_User.class);
                        if(user != null) user.setUid(data.getKey());
                        if(!mAuth.getUid().toString().equals(user.getUid())) list.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() == 0){
                    database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                list.clear();
                                for(DataSnapshot data : snapshot.getChildren()){
                                    Firebase_User user = data.getValue(Firebase_User.class);
                                    if(user != null) user.setUid(data.getKey());
                                    if(!mAuth.getUid().toString().equals(user.getUid())) list.add(user);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                list.clear();
                                for(DataSnapshot data : snapshot.getChildren()){
                                    Firebase_User user = data.getValue(Firebase_User.class);
                                    if(user != null) user.setUid(data.getKey());
                                    if(!mAuth.getUid().toString().equals(user.getUid())){
                                        if(s.length() <= user.getName().length()
                                                && user.getName().toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT)))list.add(user);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}