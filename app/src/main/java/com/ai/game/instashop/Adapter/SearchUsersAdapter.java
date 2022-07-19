package com.ai.game.instashop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.ProfileFriendsModel;
import com.ai.game.instashop.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class SearchUsersAdapter extends RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>{

    ArrayList<Firebase_User> users;
    Context context;
    Activity activity;

    public SearchUsersAdapter(ArrayList<Firebase_User> users, Context context) {
        this.users = users;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_rv_layout, parent, false);
        return new SearchUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Firebase_User user = users.get(position);
        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(holder.profile);
        holder.name.setText(user.getName());
        holder.profession.setText(user.getProfession());

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(user.getUid())
                .child("Followers")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            holder.follow.setBackgroundResource(R.drawable.edittext_background);
                            holder.follow.setTextColor(ContextCompat.getColor(context, R.color.gray2));
                            holder.follow.setText("Following");
                            holder.follow.setEnabled(false);
                        }
                        else{
                            holder.follow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    activity.findViewById(R.id.search_progressBar).setVisibility(View.VISIBLE);

                                    ProfileFriendsModel model = new ProfileFriendsModel();
                                    model.setFollowedById(FirebaseAuth.getInstance().getUid());
                                    model.setFollowedAtTime(new Date().getTime());

                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(user.getUid())
                                            .child("Followers")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                                            .child(user.getUid())
                                                            .child("FollowersCount").setValue(user.getFollowersCount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(context, "Following!", Toast.LENGTH_SHORT).show();
                                                                    activity.findViewById(R.id.search_progressBar).setVisibility(View.GONE);
                                                                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile;
        public TextView name, profession;
        public Button follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.SearchName);
            profession = itemView.findViewById(R.id.searchProfession);
            follow = itemView.findViewById(R.id.follow);
        }
    }

}
