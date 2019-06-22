package com.example.bazar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class SecondActivity extends AppCompatActivity {
    ImageView image;
    TextView name, price, description, description_short;
    Button buy;
    double price_of_item;
    String image_location;
    int id_product;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       //Somehow pass the values

        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
            image_location=extras.getString("image");
            price_of_item= extras.getDouble("price");
            this.id_product = extras.getInt("id_number");
            name.setText(extras.getString("title"));
            price.setText(String.valueOf(price_of_item));
            description.setText(extras.getString("description_long"));
            description_short.setText(extras.getString("description"));
        }
    }

    public void load_buy_view(View view){
        Intent intent = new Intent(SecondActivity.this,Buy_Screen.class);
        intent.putExtra("id",this.id_product);
        intent.putExtra("title",name.getText());
        intent.putExtra("price",price_of_item);
        intent.putExtra("description",description_short.getText());
        intent.putExtra("description_long",description.getText());
        intent.putExtra("image",image_location);
        startActivity(intent);

    }
}
