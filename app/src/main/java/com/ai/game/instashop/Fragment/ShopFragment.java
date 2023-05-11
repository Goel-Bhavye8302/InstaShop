package com.ai.game.instashop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        ProgressBar bar = view.findViewById(R.id.progressBar);
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

        EditText item = view.findViewById(R.id.editTextSearchUser);
        TextView searched_items = view.findViewById(R.id.searched_items);
        OkHttpClient client = new OkHttpClient();

        item.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    String url = "https://pricer.p.rapidapi.com/str?q=" + item.getText().toString();
                    Log.i("URLLLLLLLL : ", url);

                    bar.setVisibility(View.VISIBLE);
                    requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Request request = new Request.Builder()
                            .url(url)
                            .get()
                            .addHeader("X-RapidAPI-Key", "aca811539amshfa686881bbeb359p10d63ajsn90c9a206d2ba")
                            .addHeader("X-RapidAPI-Host", "pricer.p.rapidapi.com")
                            .build();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                Response response = client.newCall(request).execute();
                                if(response.isSuccessful()){
                                    searched_items.setText(response.body().string());
                                    Log.i("respose", response.body().toString());
                                    bar.setVisibility(View.GONE);
                                    requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                                else throw new IOException("Unexpected code " + response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    return true;
                }
                return false;
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
}