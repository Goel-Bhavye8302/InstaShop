package com.ai.game.instashop.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Adapter.PostAdapter;
import com.ai.game.instashop.Adapter.StoryAdapter;
import com.ai.game.instashop.Model.PostModel;
import com.ai.game.instashop.Model.StoryModel;
import com.ai.game.instashop.Model.UserStoriesModel;
import com.ai.game.instashop.R;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;

public class FeedFragment extends Fragment {

    ShimmerRecyclerView postRV, storyRV;
    ArrayList<StoryModel> storyModelList;
    ArrayList<PostModel> postModelList;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    ProgressDialog dialog;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Uploading Story");
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        storyRV = view.findViewById(R.id.story_rv);
        postRV = view.findViewById(R.id.post_rv);

        postRV.showShimmerAdapter();
        storyRV.showShimmerAdapter();

        storyModelList = new ArrayList<>();
        postModelList = new ArrayList<>();

        StoryAdapter storyAdapter = new StoryAdapter(storyModelList, getContext());
        PostAdapter postAdapter = new PostAdapter(postModelList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        storyRV.setLayoutManager(linearLayoutManager);
        storyRV.setNestedScrollingEnabled(false);

        postRV.setLayoutManager(linearLayoutManager1);
        postRV.setNestedScrollingEnabled(false);

        database.getReference().child("Stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    storyModelList.clear();
                    for(DataSnapshot data : snapshot.getChildren()){
                        StoryModel model = new StoryModel();
                        model.setStoryById(data.getKey());
                        model.setStoryAtTime(data.child("storyAtTime").getValue(Long.class));

                        ArrayList<UserStoriesModel> list = new ArrayList<>();

                        for(DataSnapshot data1 : data.child("storyList").getChildren()){
                            list.add(data1.getValue(UserStoriesModel.class));
                        }

                        model.setStoryList(list);
                        storyModelList.add(model);
                    }
                }
                storyRV.setAdapter(storyAdapter);
                storyRV.hideShimmerAdapter();
                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        PostModel model = data.getValue(PostModel.class);
                        model.setPostId(data.getKey());
                        postModelList.add(model);
                    }
                }
                postRV.setAdapter(postAdapter);
                postRV.hideShimmerAdapter();
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        view.findViewById(R.id.view3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(FeedFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            Uri result = data.getData();

            dialog.show();
            StorageReference reference = storage.getReference().child("stories").child(mAuth.getUid()).child(Long.toString(new Date().getTime()));

            reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            StoryModel model = new StoryModel();
                            model.setStoryAtTime(new Date().getTime());
                            model.setStoryById(mAuth.getUid());
                            database.getReference().child("Stories").child(mAuth.getUid()).child("storyAtTime").setValue(model.getStoryAtTime()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    UserStoriesModel model1 = new UserStoriesModel(uri.toString(), model.getStoryAtTime());
                                    database.getReference().child("Stories")
                                            .child(mAuth.getUid())
                                            .child("storyList")
                                            .push()
                                            .setValue(model1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dialog.dismiss();
                                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}