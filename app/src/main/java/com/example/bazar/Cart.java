package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cart extends AppCompatActivity  {

    static private ArrayList<Product> cart_items;
    RecyclerView display_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cart_items = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            //Set the values received
            String image_location = extras.getString("image");
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
            display_items = (RecyclerView) findViewById(R.id.recyclerView_cart);

            //Set the imageview to the image
            //img.setImageBitmap(StringToBitMap(image_location_disk));
            Product product = (ProductsDatabase.get_product());
            if(product==null){
                Toast toast = Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"This Works!!",Toast.LENGTH_LONG);
                TextView t = (TextView) findViewById(R.id.cart_gt_entry);
                t.setText(product.getPrice());
                toast.show();
            }
        }
    }

}
