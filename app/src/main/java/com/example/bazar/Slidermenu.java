package com.example.bazar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Slidermenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    ArrayList<Product> products_in_sale;
    ArrayList<Product> my_products_in_sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.products_in_sale= new ArrayList<>();
        this.my_products_in_sale= new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidermenu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sell a Product", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String username ;
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
            final String username_final = username;
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),AddProductforSale.class);
                    intent.putExtra("username",username_final);
                    v.getContext().startActivity(intent);
                }
            });
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AddProductforSale.class);
                v.getContext().startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference products = database.getReference("products");


        products.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>)dataSnapshot.getValue();
                if(data!=null){
                    update_recycler_view(data);     //Call if theres anything changed in the database
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"No Items Currently on Sale",Toast.LENGTH_LONG);
                    toast.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.slidermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),AccountSettings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cart) {
            //Load Cart page

            Intent intent = new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);

        } else if (id == R.id.AccountSettings) {
            Intent intent = new Intent(getApplicationContext(),AccountSettings.class);
            startActivity(intent);

        } else if (id == R.id.Finances) {

        } else if (id == R.id.Marketplace) {
            load_items_on_sale();

        } else if (id == R.id.MySale) {
            //Connect with server and get all items currently on sale sold by me
            //recyclerView.swapAdapter(new ProductAdapter(this, Communicate_with_server.get_my_items_on_sale()),true);

            //Set the id of product in user's database too
            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
            String user_id = current_user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference products = database.getReference("users").child(user_id).child("myproducts");

            products.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, Object> data = (Map<String, Object>)dataSnapshot.getValue();
                    if(data !=null){
                        update_my_items(data);
                    }
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(),"No Current Items on Sale by You",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (id == R.id.SellItems) {
            Intent intent = new Intent(getApplicationContext(), AddProductforSale.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void load_items_on_sale(){
        recyclerView.swapAdapter(new ProductAdapter(this, products_in_sale),true);
    }

    public void update_recycler_view(Map<String, Object> data){

        for(Map.Entry<String,Object> entry:data.entrySet()){
            Map singleProduct = (Map) entry.getValue();
            this.products_in_sale.add(new Product(
                    (String) singleProduct.get("id"),
                    (String) singleProduct.get("titile"),
                    (String) singleProduct.get("price"),
                    (String) singleProduct.get("short"),
                    (String) singleProduct.get("long"),
                    (String) singleProduct.get("image")
            ));
        }

        recyclerView.swapAdapter(new ProductAdapter(this, products_in_sale),true);

    }

    public void update_my_items(Map<String,Object> data){
        this.my_products_in_sale.clear();
        for(Map.Entry<String,Object> entry:data.entrySet()){
            Map singleProduct = (Map) entry.getValue();
            this.my_products_in_sale.add(new Product(
                    (String) singleProduct.get("id"),   //Items received from the database
                    (String) singleProduct.get("titile"),
                    (String) singleProduct.get("price"),
                    (String) singleProduct.get("short"),
                    (String) singleProduct.get("long"),
                    (String) singleProduct.get("image")
            ));
        }
        recyclerView.swapAdapter(new ProductAdapter(this, my_products_in_sale),true);
    }

}
