package com.ai.game.instashop.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Adapter.ChatUserAdapter;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    ArrayList<Firebase_User> list;

    ImageView searchIcon;
    EditText searchUser;

    ArrayList<String> my_followers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        searchIcon = findViewById(R.id.search);
        searchUser = findViewById(R.id.chatSeachUser);
        recyclerView = findViewById(R.id.chat_rv);

        list = new ArrayList<>();

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchUser.getVisibility() == View.GONE){
                    searchUser.setVisibility(View.VISIBLE);
                    searchUser.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                }
                else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(ChatActivity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    view.clearFocus();

                    searchUser.setVisibility(View.GONE);

                }
            }
        });

        ChatUserAdapter adapter = new ChatUserAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        database.getReference().child("Users").child(mAuth.getUid()).child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                my_followers.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    my_followers.add(data.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    list.clear();
                    for(DataSnapshot data : snapshot.getChildren()){
                        Firebase_User user = data.getValue(Firebase_User.class);
                        if(user != null) user.setUid(data.getKey());
                        if(user.getUid().equals(mAuth.getUid())) continue;

                        for(DataSnapshot temp : data.child("Followers").getChildren()){
                            if(temp.exists() && temp.getKey().equals(mAuth.getUid())){
                                if(my_followers.contains(user.getUid())){
                                    list.add(user);
                                }
                            }
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchUser.addTextChangedListener(new TextWatcher() {
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
                                    if(user.getUid().equals(mAuth.getUid())) continue;

                                    for(DataSnapshot temp : data.child("Followers").getChildren()){
                                        if(temp.exists() && temp.getKey().equals(mAuth.getUid())){
                                            if(my_followers.contains(user.getUid())){
                                                list.add(user);
                                            }
                                        }
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
                else{
                    database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                list.clear();
                                for(DataSnapshot data : snapshot.getChildren()){
                                    Firebase_User user = data.getValue(Firebase_User.class);
                                    if(user != null) user.setUid(data.getKey());
                                    if(user.getUid().equals(mAuth.getUid())) continue;

                                    for(DataSnapshot temp : data.child("Followers").getChildren()){
                                        if(temp.exists() && temp.getKey().equals(mAuth.getUid())){
                                            if(my_followers.contains(user.getUid())){
                                                if(s.length() <= user.getName().length()
                                                        && user.getName().toString().toLowerCase(Locale.ROOT).contains(s.toString().toLowerCase(Locale.ROOT)))list.add(user);
                                            }
                                        }
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

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void hideKeyboard(Activity activity) {

    }
}