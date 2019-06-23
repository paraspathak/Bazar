package com.example.bazar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cart extends AppCompatActivity  {

    String id_number;
    double quantity;
    TextView grand_total;
    RecyclerView display_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        grand_total = (TextView) findViewById(R.id.cart_gt_entry);
        display_items = (RecyclerView) findViewById(R.id.recyclerView_cart);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_number = extras.getString("id");
            Product product = ProductsDatabase.get_product();

            if(product==null){
                Toast toast = Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"This Works!!",Toast.LENGTH_LONG);

                grand_total.setText(product.getPrice());
                toast.show();
            }
        }
    }

}
