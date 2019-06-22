package com.example.bazar;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;


public class AddProductforSale extends AppCompatActivity {

    private EditText name, price, short_description, long_description;
    private TextView photo_status;
    public final String APP_TAG = "Bazar";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productfor_sale);
        name = (EditText) findViewById(R.id.name_of_product_entry);
        price = (EditText) findViewById(R.id.price_entry);
        short_description = (EditText) findViewById(R.id.shortdes_entry);
        long_description = (EditText) findViewById(R.id.longdes_entry);
        photo_status = (TextView) findViewById(R.id.photo_status);
    }

    public void takepicture(View view){
      //Take or choose picture from here
    }


    public void cancelclick(View view){

    }
    public void submitclick(View view){

    }
}
