package com.ai.game.instashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ai.game.instashop.Adapter.ProductAdapter;
import com.ai.game.instashop.Model.ProductModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class HomeItemsFragment extends Fragment {
    ProductAdapter productAdapter;
    public HomeItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_items, container, false);

        ArrayList<ProductModel> productsList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.homeItems_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(getContext(), productsList);
        recyclerView.setAdapter(productAdapter);

        return view;
    }
}