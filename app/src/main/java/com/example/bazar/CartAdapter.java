package com.example.bazar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private Context context;

    //Constructor
    public CartAdapter(Context ctx, ArrayList<CartItem> cart_items){
        this.cartItems = cart_items;
        this.context = ctx;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.cart_layout,null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder cartViewHolder, final int i) {
        CartItem c = cartItems.get(i);
        if(c.getBackup_title()==null){
            cartViewHolder.product_title.setText("Hello World");
        }
        else {
            cartViewHolder.product_title.setText(c.getBackup_title());
        }
        cartViewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItems.remove(i);
                notifyItemRemoved(i);
            }
        });
        //cartViewHolder.product_quantity.setText(String.valueOf(c.get_price()));
        cartViewHolder.bind(c);     //Delegate to sub class
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView product_image;
        public TextView product_title, product_price, product_quantity;
        public Double price_of_item, quantity_item, total_price;
        public Button delete_button;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = (ImageView) itemView.findViewById(R.id.cart_image);
            product_title = (TextView) itemView.findViewById(R.id.cart_title_entry);
            product_price = (TextView) itemView.findViewById(R.id.cart_price_of_item);
            product_quantity = (TextView) itemView.findViewById(R.id.cart_number_item);
            delete_button = (Button) itemView.findViewById(R.id.cart_delete_button);

        }

        public void bind(final CartItem c){
            this.price_of_item = c.get_price();
            this.quantity_item = c.getQuantity();
            total_price = price_of_item * quantity_item;
            //Set the image
            product_image.setImageBitmap(ProductsDatabase.StringToBitMap(c.get_image_uri()));
            //Set Text Views
            //product_title.setText(c.get_title());
            //product_title.setText(c.getBackup_title());


            //Set Total Price
            product_price.setText(String.valueOf(total_price));
            //Set Quantity
            product_quantity.setText(String.valueOf(quantity_item));


            //Add Event Listeners for the buttons here and implement logic also to the changes
        }
    }
}
