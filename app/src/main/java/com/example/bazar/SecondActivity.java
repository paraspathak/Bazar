package com.example.bazar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SecondActivity extends AppCompatActivity {
    ImageView image;
    TextView name, price, description, description_short;
    Button buy;
    String image_location,price_of_item,id_product;
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
            price_of_item= extras.getString("price");
            this.id_product = extras.getString("id_number");
            name.setText(extras.getString("title"));
            price.setText((price_of_item));
            description.setText(extras.getString("description_long"));
            description_short.setText(extras.getString("description"));
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


            image.setImageBitmap(StringToBitMap(image_location_disk));

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
        intent.putExtra("title",name.getText());
        intent.putExtra("price",price_of_item);
        intent.putExtra("description",description_short.getText());
        intent.putExtra("description_long",description.getText());
        intent.putExtra("image",image_location);
        startActivity(intent);

    }
}
