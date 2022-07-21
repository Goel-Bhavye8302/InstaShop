package com.ai.game.instashop.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.Model.PostModel;
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

import java.util.Date;

public class PostFragment extends Fragment {

    Button postButton;
    ImageView addPhoto;
    ImageView postImage;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseStorage storage;

    ProgressDialog progressDialog, progressDialog2;

    Uri postImageUri;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Uploading Post");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog2 = new ProgressDialog(getContext());
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog2.setMessage("Please Wait...");
        progressDialog2.setTitle("Uploading Image");
        progressDialog2.setCancelable(false);
        progressDialog2.setCanceledOnTouchOutside(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        postButton = view.findViewById(R.id.postButton);
        addPhoto = view.findViewById(R.id.addPhoto);
        postImage = view.findViewById(R.id.postImage);

        ((EditText)view.findViewById(R.id.postImageDescription)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 && postImage.getVisibility() == View.GONE ){
                    enablePostButton(false);
                }
                else {
                    enablePostButton(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        database.getReference().child("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    if(user != null){
                        Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into((ImageView)view.findViewById(R.id.profile_image));
                        ((TextView)view.findViewById(R.id.postProfession)).setText(user.getProfession());
                        ((TextView)view.findViewById(R.id.postName)).setText(user.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(PostFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                StorageReference reference = storage.getReference().child("posts").child(mAuth.getUid()).child(Long.toString(new Date().getTime()));
                        reference.putFile(postImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        PostModel model = new PostModel();
                                        model.setPostImage(uri.toString());
                                        model.setPostedById(mAuth.getUid());
                                        model.setPostDescription( ((EditText)view.findViewById(R.id.postImageDescription)).getText().toString());
                                        model.setPostedAtTime(new Date().getTime());

                                        database.getReference().child("Posts").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });

                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            progressDialog2.show();
            postImageUri = data.getData();
            postImage.setImageURI(postImageUri);
            postImage.setVisibility(View.VISIBLE);
            enablePostButton(true);
            progressDialog2.dismiss();
        }
    }

    void enablePostButton(boolean enable){
        if(enable){
            postButton.setTextColor(getResources().getColor(R.color.white1));
            postButton.setBackgroundResource(R.drawable.button_background);
            postButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red1)));
        }
        else{
            postButton.setTextColor(getResources().getColor(R.color.gray2));
            postButton.setBackgroundResource(R.drawable.edittext_background);
            postButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray2)));
        }
        postButton.setEnabled(enable);
    }
}