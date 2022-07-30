package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Activity.ProductDetailsActivity;
import com.ai.game.instashop.Model.ProductModel;
import com.ai.game.instashop.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductModel> productsList;

    public ProductAdapter(Context context, ArrayList<ProductModel> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = productsList.get(position);
        holder.prodImage.setImageResource(model.getImageUrl());
        holder.prodName.setText(model.getProductName());
        holder.prodQty.setText(model.getProductQty());
        holder.prodPrice.setText(model.getProductPrice());
        holder.prodMeasure.setText(model.getProductMeasure());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("prodName", model.getProductName());
                intent.putExtra("prodCost", model.getProductPrice());
                intent.putExtra("prodImage", Integer.toString(model.getImageUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prodImage;
        TextView prodName, prodQty, prodPrice, prodMeasure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.prod_image);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.prod_price);
            prodQty = itemView.findViewById(R.id.prod_qty);
            prodMeasure = itemView.findViewById(R.id.prod_measure);
        }
    }
}
