package com.example.bazar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Buy_Screen extends AppCompatActivity {
    private TextView  price_total, success_screen;
    double price_of_item, current_total;
    private Product product;
    private String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__screen);

        //Get items by their id
        TextView test = (TextView) findViewById(R.id.title);
        TextView short_description = (TextView) findViewById(R.id.short_description_cart);
        price_total = (TextView) findViewById(R.id.price_update);


        success_screen = (TextView) findViewById(R.id.Success_banner);

        TextView number_of_item = (TextView) findViewById(R.id.number_to_buy);
        number_of_item.addTextChangedListener(new TextWatcher() {       //Add a Listener to watch and modify changes
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    current_total = price_of_item *  Integer.parseInt(s.toString());
                    price_total.setText(String.valueOf(current_total));
                }
                else {
                    //If 0 is selected put items price as 1
                    price_total.setText(String.valueOf(price_of_item));
                    current_total = price_of_item;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            //Get key passed from previous intent
            this.product_id = extras.getString("id");

            //get the product Object from singleton class
            this.product = ProductsDatabase.Get_product_with_key(product_id);

            //Modify the screen with items
            test.setText(product.getTitle());
            short_description.setText(product.getShort_description());
            price_of_item = Double.valueOf(product.getPrice());
            price_total.setText((product.getPrice()));
            TextView long_des = (TextView) findViewById(R.id.long_description_cart);
            long_des.setText(product.getLong_description());
            current_total=price_of_item;
            ImageView img = (ImageView) findViewById(R.id.activity_buy_image);
            img.setImageBitmap(StringToBitMap(product.getImage_uri()));
        }
    }
    public void add_to_cart(View view){
        //Delete the image file
        Double quantity = this.current_total/this.price_of_item;
        ProductsDatabase.add_to_cart(this.product,quantity);
        Toast toast = Toast.makeText(getApplicationContext(),"Item Added to Cart",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void buy_click(View view){
        //Calculate number of items
        Double quantity = this.current_total/this.price_of_item;

        //Create a new intent and put ID of product in it and quantity
        Intent intent = new Intent(view.getContext(), Cart.class);
        intent.putExtra("id",this.product_id);
        intent.putExtra("quantity",quantity);

        ProductsDatabase.add_to_cart(this.product,quantity);
        ProductsDatabase.Add_product_with_key(this.product,this.product_id);
        view.getContext().startActivity(intent);

    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
