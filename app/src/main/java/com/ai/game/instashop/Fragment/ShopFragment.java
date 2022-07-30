package com.ai.game.instashop.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShopFragment extends Fragment {

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        view.findViewById(R.id.cat1).animate().rotation(270).setDuration(0);
        view.findViewById(R.id.cat2).animate().rotation(270).setDuration(0);
        view.findViewById(R.id.cat3).animate().rotation(270).setDuration(0);
        view.findViewById(R.id.cat4).animate().rotation(270).setDuration(0);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Firebase_User user = snapshot.getValue(Firebase_User.class);
                    Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user2).into((ImageView)view.findViewById(R.id.profile_image));
                    ((TextView)view.findViewById(R.id.textView5)).setText("Hello, " + user.getName() + "!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView cat1 = view.findViewById(R.id.cat1);
        TextView cat2 = view.findViewById(R.id.cat2);
        TextView cat3 = view.findViewById(R.id.cat3);
        TextView cat4 = view.findViewById(R.id.cat4);

        Typeface selected = ResourcesCompat.getFont(getContext(), R.font.rubik_bold_italic);

        loadFragments(new ElectronicItemsFragment());

        view.findViewById(R.id.cat1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllUnselected(view);
                cat1.setTextColor(Color.parseColor("#005B1B"));
                cat1.setTypeface(selected);
                loadFragments(new ElectronicItemsFragment());
            }
        });
        view.findViewById(R.id.cat2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllUnselected(view);
                cat2.setTextColor(Color.parseColor("#005B1B"));
                cat2.setTypeface(selected);
                loadFragments(new SportsItemsFragment());
            }
        });
        view.findViewById(R.id.cat3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllUnselected(view);
                cat3.setTextColor(Color.parseColor("#005B1B"));
                cat3.setTypeface(selected);
                loadFragments(new HomeItemsFragment());
            }
        });
        view.findViewById(R.id.cat4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllUnselected(view);
                cat4.setTextColor(Color.parseColor("#005B1B"));
                cat4.setTypeface(selected);
                loadFragments(new ClothesItemsFragment());
            }
        });

        return view;
    }

    public void loadFragments(Fragment fragment) {
        if(fragment != null){
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
        else{
            Toast.makeText(getContext(), "Fragment Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAllUnselected(View view){
        TextView cat1 = view.findViewById(R.id.cat1);
        TextView cat2 = view.findViewById(R.id.cat2);
        TextView cat3 = view.findViewById(R.id.cat3);
        TextView cat4 = view.findViewById(R.id.cat4);
        Typeface notSelected = ResourcesCompat.getFont(getContext(), R.font.rubik_medium);

        cat1.setTextColor(Color.parseColor("#373131"));
        cat1.setTypeface(notSelected);

        cat2.setTextColor(Color.parseColor("#373131"));
        cat2.setTypeface(notSelected);

        cat3.setTextColor(Color.parseColor("#373131"));
        cat3.setTypeface(notSelected);

        cat4.setTextColor(Color.parseColor("#373131"));
        cat4.setTypeface(notSelected);
    }

}