package com.ai.game.instashop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Adapter.ProductAdapter;
import com.ai.game.instashop.Model.Firebase_User;
import com.ai.game.instashop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;

public class ShopFragment extends Fragment {

    RecyclerView itemsrv;
    ArrayList<JSONObject> items;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ProgressBar bar = view.findViewById(R.id.progressBar);
        itemsrv = view.findViewById(R.id.searched_items);

        items = new ArrayList<>();

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

        ProductAdapter adapter = new ProductAdapter(getContext(), items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        itemsrv.setLayoutManager(linearLayoutManager);
        itemsrv.setNestedScrollingEnabled(false);

        EditText item = view.findViewById(R.id.editTextSearchUser);

        item.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    bar.setVisibility(View.VISIBLE);
//                    requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String encodedQueryString = "";
                                try {
                                    encodedQueryString = URLEncoder.encode(item.getText().toString(),"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    throw new RuntimeException(e);
                                }
                                String url = "https://api.scraperapi.com/?autoparse=true&api_key=66c064153e8478f11a6cb42c1e25d20d&url=https://www.google.com/search?tbm=shop&q=" + encodedQueryString + "&country_code=in";
                                Log.i("URLLLLLLLL : ", url);

                                URL uri = new URL(url);
                                HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                                urlConnection.setRequestMethod("GET");

                                int responseCode = urlConnection.getResponseCode();

                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    requireActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            bar.setVisibility(View.GONE);
//                                          requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            items.clear();
                                        }
                                    });

                                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                                    String inputLine;
                                    StringBuffer response = new StringBuffer();

                                    while ((inputLine = in.readLine()) != null) {
                                        response.append(inputLine);
                                    }
                                    in.close();


                                    JSONObject jsonObject = new JSONObject(response.toString());
                                    JSONArray jsonArray = (JSONArray) jsonObject.get("shopping_results");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        items.add(object);
                                    }

                                    requireActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            itemsrv.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

                                    //Print the response to the console
                                    Log.i("HTTPRESPONSE", response.toString());
                                }
                                else{
                                    Log.i("HTTPRESPONSE", "errorrrr");
                                }
                            } catch (IOException | JSONException e) {
                                throw new RuntimeException(e);
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