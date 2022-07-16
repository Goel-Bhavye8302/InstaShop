package com.ai.game.instashop.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.game.instashop.Adapter.ProfileFriendsAdapter;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.ProfileFriendsModel;
import com.ai.game.instashop.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    RecyclerView friendsRV;
    ArrayList<ProfileFriendsModel> friendsModelList;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;

    private ImageView cover;
    private ImageView profile;

    private ProgressBar progressBar;

    View view;

    public ProfileFragment() {
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
        firebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        friendsRV = view.findViewById(R.id.profile_friendsRV);

        cover = view.findViewById(R.id.profileBackgroundImage);
        profile = view.findViewById(R.id.profile_image);
        progressBar = view.findViewById(R.id.profile_progressBar);

        TextView name = view.findViewById(R.id.profile_name);
        TextView profession = view.findViewById(R.id.profile_profession);
        TextView followers = view.findViewById(R.id.followerCount);

        view.findViewById(R.id.profile_changeBackGroundImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });
        view.findViewById(R.id.profile_changeProfileImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ProfileFragment.this)
                        .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(20);
            }
        });

        database.getReference().child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    if(user != null){
                        Picasso.get().load(user.getCoverPhoto()).placeholder(R.drawable.gradient).into(cover);
                        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into(profile);
                        name.setText(user.getName());
                        profession.setText(user.getProfession());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    if(user != null) followers.setText(Integer.toString(user.getFollowersCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        friendsModelList = new ArrayList<>();

        ProfileFriendsAdapter adapter = new ProfileFriendsAdapter(friendsModelList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        friendsRV.setLayoutManager(linearLayoutManager);
        friendsRV.setNestedScrollingEnabled(false);
        friendsRV.setAdapter(adapter);

        database.getReference().child("Users")
                .child(mAuth.getUid())
                .child("Followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            friendsModelList.clear();
                            for(DataSnapshot data : snapshot.getChildren()){
                                ProfileFriendsModel model = data.getValue(ProfileFriendsModel.class);
                                friendsModelList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            Uri uri = data.getData();     // Retrieving image

            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.VISIBLE);

            if(requestCode == 10){
                cover.setImageURI(uri);
                StorageReference reference = firebaseStorage.getReference().child("profile background image").child(mAuth.getUid());

                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(mAuth.getUid()).child("coverPhoto").setValue(uri.toString());
                                Toast.makeText(getContext(), "Cover Photo updated!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        });
                    }
                });
            }
            else{
                profile.setImageURI(uri);
                StorageReference reference = firebaseStorage.getReference().child("profile image").child(mAuth.getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(mAuth.getUid()).child("profilePhoto").setValue(uri.toString());
                                Toast.makeText(getContext(), "Profile Photo updated!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        });
                    }
                });
            }
        }
    }
}