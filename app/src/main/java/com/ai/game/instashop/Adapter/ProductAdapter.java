package com.ai.game.instashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ai.game.instashop.Activity.ProductDetailsActivity;
import com.ai.game.instashop.Model.ProductModel;
import com.ai.game.instashop.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Context context;
    ArrayList<JSONObject> productsList;

    public ProductAdapter(Context context, ArrayList<JSONObject> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searched_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JSONObject obj = productsList.get(position);
//        holder.prodImage.setImageResource(model.getImageUrl());
        try {
            if(obj.has("thumbnail")) Picasso.get().load((String) obj.get("thumbnail")).placeholder(R.drawable.ic_placeholder_post).into(holder.prodImage);
            if(obj.has("title")) holder.prodName.setText((CharSequence) obj.get("title"));
            else holder.prodName.setText("N/A");

            if(obj.has("rating")) holder.prodRating.setText(obj.get("rating").toString() + "Stars");
            else holder.prodRating.setText("N/A");

            if(obj.has("price")) holder.prodPrice.setText((CharSequence) obj.get("price"));
            else holder.prodPrice.setText("N/A");

            if(obj.has("source")) holder.prodSeller.setText((CharSequence) obj.get("source"));
            else holder.prodSeller.setText("N/A");

            if(obj.has("delivery_options")) holder.prodShip.setText((CharSequence) obj.get("delivery_options"));
            else holder.prodShip.setText("N/A");

            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = null;
                    try {
                        String link = "";
                        if(obj.has("product_href")) link = obj.get("product_href").toString();
                        else link = obj.getString("product_href");
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    context.startActivity(browserIntent);
                }
            });

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prodImage;
        TextView prodName, prodSeller, prodPrice, prodShip, prodRating;

        Button buy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.product_image);
            prodName = itemView.findViewById(R.id.product_title);
            prodRating = itemView.findViewById(R.id.product_rating);
            prodPrice = itemView.findViewById(R.id.product_price);
            prodSeller = itemView.findViewById(R.id.product_seller);
            prodShip = itemView.findViewById(R.id.product_ship);
            buy = itemView.findViewById(R.id.product_buy);
        }
    }
}
