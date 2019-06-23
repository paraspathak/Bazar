package com.example.bazar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Buy_Screen extends AppCompatActivity {
    TextView test, short_description, number_of_item, price_total, success_screen;
    double price_of_item, current_total;
    Product product;
    private String image_location, title, short_des, long_des, price_item, product_id;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__screen);
        test = (TextView) findViewById(R.id.title);
        short_description = (TextView) findViewById(R.id.short_description_cart);
        price_total = (TextView) findViewById(R.id.price_update);
        success_screen = (TextView) findViewById(R.id.Success_banner);

        number_of_item = (TextView) findViewById(R.id.number_to_buy);
        number_of_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
            public void afterTextChanged(Editable s) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            //Get key passed from previous intent
            this.product_id = extras.getString("id");

            //get the product Object
            this.product = ProductsDatabase.Get_product_with_key(product_id);
            test.setText(product.getTitle());
            short_description.setText(short_des);
            price_of_item = Double.valueOf(product.getPrice());
            price_total.setText((price_item));
            current_total=price_of_item;
            ImageView img = (ImageView) findViewById(R.id.activity_buy_image);
            img.setImageBitmap(StringToBitMap(product.getImage_uri()));



            /*
            test.setText(extras.getString("title"));
            short_des = extras.getString("description");
            long_des = extras.getString("description_long");
            short_description.setText(short_des);
            price_item=extras.getString("price");
            price_of_item = Double.valueOf(price_item);
            price_total.setText((price_item));
            current_total=price_of_item;
            ImageView img = (ImageView) findViewById(R.id.activity_buy_image);
            image_location = extras.getString("image");
            File directory = getFilesDir();
            StringBuilder total = new StringBuilder();
            try {
                File secondInputFile = new File(directory, image_location);
                InputStream secondInputStream = new BufferedInputStream(new FileInputStream(secondInputFile));
                BufferedReader r = new BufferedReader(new InputStreamReader(secondInputStream));
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                r.close();
                secondInputStream.close();
                Log.d("File", "File contents: " + total);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String image_location_disk = total.toString();


            img.setImageBitmap(StringToBitMap(image_location_disk));
            */
        }




    }
    public void add_to_cart(View view){
        //Delete the image file
        success_screen.setText("Item successfully added");
    }

    public void buy_click(View view){
        //Delete the image file
        Intent intent = new Intent(view.getContext(), Cart.class);
        intent.putExtra("id",this.product_id);

        /*
        intent.putExtra("image",image_location);
        intent.putExtra("title",title);
        intent.putExtra("price",price_item);
        intent.putExtra("description_short",short_des);
        intent.putExtra("description_long",long_des);
        */

        ProductsDatabase.add_to_cart(new Product(" ",title,price_item,short_des,long_des,image_location));
        ProductsDatabase.Add_product_with_key(this.product,this.product_id);
        view.getContext().startActivity(intent);

        success_screen.setText("Start a new intent of buying item");
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
