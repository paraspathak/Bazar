package com.example.bazar;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

    public Items_on_sale return_at_index(int i){
        return products_list.get(i);
    }

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
        productViewHolder.bind(item);
        /*
        productViewHolder.textview_product_title.setText(item.getProduct_title());
        productViewHolder.textview_product_price.setText(String.valueOf(item.getProduct_price()));
        productViewHolder.textview_product_description.setText(item.getProduct_description());
        productViewHolder.imageview_product_image.setImageBitmap(BitmapFactory.decodeFile(item.getProduct_image_location()));
        */
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
        public void bind(final Items_on_sale items){
            textview_product_description.setText(items.getProduct_description());
            textview_product_title.setText(items.getProduct_title());
            textview_product_price.setText(String.valueOf(items.getProduct_price()));
            imageview_product_image.setImageBitmap(BitmapFactory.decodeFile(items.getProduct_image_location()));
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Listener is Working
                    //textview_product_title.setText("Clicked");
                    Intent intent = new Intent(context, SecondActivity.class);
                    intent.putExtra("id_number",items.getProduct_id());
                    intent.putExtra("title",items.getProduct_title());
                    intent.putExtra("price",items.getProduct_price());
                    intent.putExtra("description",items.getProduct_description());
                    intent.putExtra("description_long",items.getProduct_description_long());
                    intent.putExtra("image",items.getProduct_image_location());
                    context.startActivity(intent);
                }
            });
        }
    }
}
