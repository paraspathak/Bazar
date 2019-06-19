package com.example.bazar;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    ImageView image;
    TextView name, price, description;
    int id_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       //Somehow pass the values

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        image = (ImageView) findViewById(R.id.ProductImage);
        name = (TextView) findViewById(R.id.ProductName);
        price = (TextView) findViewById(R.id.PriceItem);
        description = (TextView) findViewById(R.id.Description);

        Bundle extras = getIntent().getExtras();
        int id;
        if(extras!=null){
            this.id_product = extras.getInt("id_number");
            name.setText(extras.getString("title"));
            price.setText(String.valueOf(extras.getDouble("price")));
            description.setText(extras.getString("description_long"));
        }
    }

    public void initialize_objects(Items_on_sale items){
        description.setText(items.getProduct_description());
        name.setText(items.getProduct_title());
        price.setText(String.valueOf(items.getProduct_price()));
        image.setImageBitmap(BitmapFactory.decodeFile(items.getProduct_image_location()));
    }
}
