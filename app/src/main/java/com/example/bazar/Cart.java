package com.example.bazar;

import android.content.Intent;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import static com.example.bazar.ProductsDatabase.items_in_cart;

public class Cart extends AppCompatActivity  {

    private String id_number;
    private Product product;
    private TextView grand_total;
    private Double grand_ttl;

    private RecyclerView display_items;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> cart;
    private ArrayList<Double> count;
    private ArrayList<CartItem> items_in_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        grand_ttl = 0.0;
        grand_total = (TextView) findViewById(R.id.cart_gt_entry);
        display_items = (RecyclerView) findViewById(R.id.recyclerView_cart);
        items_in_cart = new ArrayList<>();
        //May need to change this to scale application
        //Done solely to increase performance
        display_items.setHasFixedSize(true);

        //Add a layout manager
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        display_items.setLayoutManager(layoutManager);


        Button buy_all = (Button) findViewById(R.id.cart_buy_all);
        buy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SecondActivity.class);
                intent.putExtra("total",grand_ttl);
                v.getContext().startActivity(intent);           //Go to the payment screen
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id_number = extras.getString("id_number");
            this.product =  ProductsDatabase.Get_product_with_key(id_number);

            if(product==null){
                Toast toast = Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        cart = new ArrayList<>();
        count = new ArrayList<>();
        display_items.setAdapter(new CartAdapter(this, items_in_cart));

        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth user = FirebaseAuth.getInstance();

        String user_id = user.getUid();


        DatabaseReference cart = database.getReference(user_id).child("cart");

        cart.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map<String, String> data_from_server = (Map<String, String>)dataSnapshot.getValue();
                if(data_from_server!=null){
                    //<TODO> Update recycler view
                    for (Map.Entry<String,String> item: data_from_server.entrySet()){
                        String id = item.getKey();
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */


        load_items_into_view();
    }

    private void load_items_into_view() {
        cart =  ProductsDatabase.items_in_cart();
        count = ProductsDatabase.quantity_of_cart();


        if(cart.size()==0 || count.size()==0){
            Toast toast = Toast.makeText(this, "Null",Toast.LENGTH_SHORT);
            toast.show();
        }
        for(int i =0; i<cart.size(); i++){
            double price = Double.valueOf(cart.get(i).getPrice()) * count.get(i);
            if(cart.get(i).getTitle()==null){
                Toast toast = Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT);
            }
            items_in_cart.add(new CartItem(cart.get(i),count.get(i), cart.get(i).getTitle()));
        }



        display_items.swapAdapter(new CartAdapter(this, items_in_cart),true);
        display_items.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {       //Working fine
                //Update the grand total
                TextView p = (TextView) view.findViewById(R.id.cart_price_of_item);
                String pr = p.getText().toString();
                grand_ttl+=Double.valueOf(pr);
                grand_total.setText(String.valueOf(grand_ttl));
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {     //Working fine
                //deduct the grand total
                TextView p = (TextView) view.findViewById(R.id.cart_price_of_item);
                String pr = p.getText().toString();
                grand_ttl-=Double.valueOf(pr);
                grand_total.setText(String.valueOf(grand_ttl));
            }
        });
    }

}
