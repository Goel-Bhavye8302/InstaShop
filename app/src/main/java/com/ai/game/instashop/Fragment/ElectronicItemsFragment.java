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

public class ElectronicItemsFragment extends Fragment {

    RecyclerView prodItemRecycler;
    ProductAdapter productAdapter;

    public ElectronicItemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electronic_items, container, false);

        ArrayList<ProductModel> productsList = new ArrayList<>();
        productsList.add(new ProductModel(1, "Electric Kettle","Size","1.5 ltr", "Rs 799", R.drawable.electronic1));
        productsList.add(new ProductModel(2, "Mi Smart Watch","Size","2.8 cm", "Rs 1999", R.drawable.electronic2));
        productsList.add(new ProductModel(1, "Coffee Maker","Size","300 ml", "Rs 4799", R.drawable.electronic3));

        prodItemRecycler = view.findViewById(R.id.electric_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        prodItemRecycler.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(getContext(), productsList);
        prodItemRecycler.setAdapter(productAdapter);

        return view;
    }
}