package com.ai.game.instashop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ai.game.instashop.Fragment.NotifyFragment;
import com.ai.game.instashop.Fragment.HomeFragment;
import com.ai.game.instashop.Fragment.ProfileFragment;
import com.ai.game.instashop.Fragment.ShopFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class HomePage extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener{

    public ChipNavigationBar navigationBar;
    public Fragment fragment = null;
    public androidx.appcompat.widget.Toolbar toolbar;
    public SlidingUpPanelLayout slidingPane;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile_settings){
            slidingPane.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigationBar = findViewById(R.id.navbar);
        navigationBar.setOnItemSelectedListener(this);
        navigationBar.setItemSelected(R.id.home, true);
        fragment = new HomeFragment();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HomePage.this.setTitle("My Profile");

        slidingPane = findViewById(R.id.sliding_layout);

        slidingPane.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                findViewById(R.id.profile_logout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(HomePage.this, "Logged-Out", Toast.LENGTH_SHORT).show();
                        slidingPane.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        Intent intent = new Intent(HomePage.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                slidingPane.setFadeOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        slidingPane.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    }
                });
            }
        });
    }

    public void loadFragments(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
        else{
            Toast.makeText(this, "Fragment Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(int i) {
        switch (i){
            case R.id.home:
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                fragment = new HomeFragment();
                break;
            case R.id.notify:
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                fragment = new NotifyFragment();
                break;
            case R.id.shop:
                findViewById(R.id.toolbar).setVisibility(View.GONE);
                fragment = new ShopFragment();
                break;
            case R.id.profile:
                findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
                fragment = new ProfileFragment();
                break;
        }
        loadFragments(fragment);
    }

    @Override
    public void onBackPressed() {
        if (slidingPane != null &&
                (slidingPane.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || slidingPane.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingPane.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else if(navigationBar.getSelectedItemId() == R.id.home){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }
}