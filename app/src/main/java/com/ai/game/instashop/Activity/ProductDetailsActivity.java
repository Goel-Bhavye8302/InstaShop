package com.ai.game.instashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ai.game.instashop.R;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();

//        Log.i("adasdasda", intent.getStringExtra("prodImage"));

        ((ImageView)findViewById(R.id.prod_image)).setImageResource(Integer.parseInt(intent.getStringExtra("prodImage")));
        ((TextView)findViewById(R.id.prod_name)).setText(intent.getStringExtra("prodName"));
        ((TextView)findViewById(R.id.prod_price)).setText(intent.getStringExtra("prodCost"));
    }
}