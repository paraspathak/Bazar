package com.example.bazar;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Items_on_sale> products_list;   //to store items
    private Context context;


    //Constructor
    public ProductAdapter(Context ctx, ArrayList<Items_on_sale> items) {
        this.products_list=items;
        this.context=ctx;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.marketplace_layout,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Items_on_sale item = products_list.get(i);
        productViewHolder.textview_product_title.setText(item.getProduct_title());
        productViewHolder.textview_product_price.setText(String.valueOf(item.getProduct_price()));
        productViewHolder.textview_product_description.setText(item.getProduct_description());
        productViewHolder.imageview_product_image.setImageBitmap(BitmapFactory.decodeFile(item.getProduct_image_location()));

    }

    @Override
    public int getItemCount() {
        return products_list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_product_image;
        TextView textview_product_title, textview_product_description, textview_product_price;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_product_image=itemView.findViewById(R.id.image_id);
            textview_product_description=itemView.findViewById(R.id.description_id);
            textview_product_price=itemView.findViewById(R.id.price_id);
            textview_product_title=itemView.findViewById(R.id.price_id);
        }
    }
}
