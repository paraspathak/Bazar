package com.example.bazar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    ImageView image;
    TextView name, price, description, description_short;
    Button buy;
    String image_location,price_of_item,id_product;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buy = (Button) findViewById(R.id.Buy_Item_button);
        image = (ImageView) findViewById(R.id.ProductImage);
        name = (TextView) findViewById(R.id.ProductName);
        price = (TextView) findViewById(R.id.PriceItem);
        description = (TextView) findViewById(R.id.Description);
        description_short = (TextView) findViewById(R.id.short_description);

        Bundle extras = getIntent().getExtras();
        int id;
        if(extras!=null){
            id_product = extras.getString("id");
            this.product =  ProductsDatabase.Get_product_with_key(id_product) ;
            name.setText(product.getTitle());
            price.setText(product.getPrice());
            description_short.setText(product.getShort_description());
            description.setText(product.getLong_description());
            image.setImageBitmap(StringToBitMap(product.getImage_uri()));

        }
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

    public void load_buy_view(View view){
        Intent intent = new Intent(SecondActivity.this,Buy_Screen.class);
        intent.putExtra("id",this.id_product);
        ProductsDatabase.Add_product_with_key(this.product,this.id_product);
        startActivity(intent);

    }
}
