package com.example.bazar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> products_list;   //to store items
    private Context context;

    public Product return_at_index(int i){
        return products_list.get(i);
    }

    //Constructor
    public ProductAdapter(Context ctx, ArrayList<Product> items) {
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
        Product item = products_list.get(i);
        productViewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return products_list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_product_image;
        CardView cardView;
        TextView textview_product_title,textview_short_product_description, textview_product_description, textview_product_price;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_product_image=itemView.findViewById(R.id.image_id);
            textview_product_description=itemView.findViewById(R.id.description_id);
            textview_product_price=itemView.findViewById(R.id.price_id);
            textview_product_title=itemView.findViewById(R.id.title_id);
            cardView= (CardView) itemView.findViewById(R.id.cardView);
        }
        public void bind(final Product items){
            textview_product_description.setText(items.getShort_description());
            textview_product_title.setText(items.getTitle());
            textview_product_price.setText(items.getPrice());
            imageview_product_image.setImageBitmap(StringToBitMap(items.getImage_uri()));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Buy_Screen.class);
                    intent.putExtra("title", items.getTitle());
                    intent.putExtra("id_number",items.getUser_id());
                    ProductsDatabase.Add_product_with_key(items,items.getUser_id());
                    context.startActivity(intent);
                }
            });
        }
        public Bitmap StringToBitMap(String encodedString){
            try {
                byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch(Exception e) {
                e.getMessage();
                return null;
            }
        }
    }
}
