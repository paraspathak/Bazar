package com.example.bazar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.bazar.ProductsDatabase.items_in_cart;

public class Cart extends AppCompatActivity  {

    String id_number;
    Product product;
    TextView grand_total;

    RecyclerView display_items;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        grand_total = (TextView) findViewById(R.id.cart_gt_entry);
        display_items = (RecyclerView) findViewById(R.id.recyclerView_cart);

        //May need to change this to scale application
        //Done solely to increase performance
        display_items.setHasFixedSize(true);

        //Add a layout manager
        layoutManager = new LinearLayoutManager(this);

        Button buy_all = (Button) findViewById(R.id.cart_buy_all);
        buy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Buy all items
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_number = extras.getString("id");
            this.product = ProductsDatabase.get_product();

            if(product==null){
                Toast toast = Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"This Works!!",Toast.LENGTH_LONG);
                toast.show();
                load_items_into_view();
            }
        }
    }

    private void load_items_into_view() {
        ArrayList<Product> cart =  ProductsDatabase.items_in_cart();
        ArrayList<Double> count = ProductsDatabase.quantity_of_cart();
        ArrayList<CartItem> items_in_cart = new ArrayList<>();
        Double grand_ttl = 0.0;
        for(int i =0; i<cart.size(); i++){
            double price = Double.valueOf(cart.get(i).getPrice()) * count.get(i);
            grand_ttl+=price;
            items_in_cart.add(new CartItem(cart.get(i),count.get(i)));
        }
        grand_total.setText(String.valueOf(grand_ttl)); //Update the total amount       //Need to add a listener to listen to change in values

        display_items.swapAdapter(new CartAdapter(this, items_in_cart),true);
    }

}
