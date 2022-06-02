package com.ai.game.instashop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.ai.game.instashop.Fragment.FeedFragment;
import com.ai.game.instashop.Fragment.HomeFragment;
import com.ai.game.instashop.Fragment.ProfileFragment;
import com.ai.game.instashop.Fragment.ShopFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomePage extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener{

    public ChipNavigationBar navigationBar;
    public Fragment fragment = null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navigationBar = findViewById(R.id.navbar);
        navigationBar.setOnItemSelectedListener(this);
        navigationBar.setItemSelected(R.id.home, true);
        fragment = new HomeFragment();
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
                fragment = new HomeFragment();
                break;
            case R.id.feed:
                fragment = new FeedFragment();
                break;
            case R.id.shop:
                fragment = new ShopFragment();
                break;
            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }
        loadFragments(fragment);
    }

    @Override
    public void onBackPressed() {
        if(navigationBar.getSelectedItemId() == R.id.home){
            finish();
        }
    }

}