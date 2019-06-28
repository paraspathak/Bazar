package com.example.bazar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddProductforSale extends AppCompatActivity {

    private EditText name, price, short_description, long_description;

    private ImageView photo_taken;
    private Button take_picture;
    private FirebaseDatabase database;

    private String username;
    private String file_in_string;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int GALLERY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productfor_sale);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            this.username = extras.getString("username");
        }

        name = (EditText) findViewById(R.id.name_of_product_entry);
        price = (EditText) findViewById(R.id.price_entry);
        short_description = (EditText) findViewById(R.id.shortdes_entry);
        long_description = (EditText) findViewById(R.id.longdes_entry);

        photo_taken = (ImageView) findViewById(R.id.taken_pic);
        take_picture= (Button)findViewById(R.id.TakePicture);
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });
        database = FirebaseDatabase.getInstance();
    }

    private void dispatchTakePictureIntent() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Capture photo from camera",
                "Select photo from gallery"
                 };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                        else if(which ==1){
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            if(galleryIntent.resolveActivity(getPackageManager())!=null){
                                startActivityForResult(galleryIntent, GALLERY);
                            }


                        }
                    }
                });
        pictureDialog.show();


    }

    public void takepicture(View view){

      //Take or choose picture from here
        dispatchTakePictureIntent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.file_in_string = BitMapToString(imageBitmap);
            photo_taken.setImageBitmap(imageBitmap);
            photo_taken.setScaleX((float) 1.5);
        }
        else if(requestCode== GALLERY && resultCode == RESULT_OK){
            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                this.file_in_string = BitMapToString(bitmap);
                Toast.makeText(AddProductforSale.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                //get the path of the image
                photo_taken.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(AddProductforSale.this, "Cannot save image",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String BitMapToString(Bitmap bit){
        //Reduce the size to 200x200
        Bitmap bitmap = Bitmap.createScaledBitmap(bit,200,200, false);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //Compress the bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG,50, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void cancelclick(View view){
        //Reset everything
        name.setText("");
        price.setText("");
        short_description.setText("");
        long_description.setText("");
        photo_taken.setImageDrawable(null);

    }

    public void submitclick(View view){
        String title_of_item = name.getText().toString();
        String price_of_item = price.getText().toString();
        String short_description_of_iem = short_description.getText().toString();
        String long_description_of_iem = long_description.getText().toString();


        Product product = new Product(this.username,title_of_item,price_of_item,short_description_of_iem,long_description_of_iem,this.file_in_string);
        DatabaseReference product_database = database.getReference("products");


        //Creating a key by the database should use persons key instead
        String userid = product_database.push().getKey();
        product.setUser_id(userid);

        //Set one copy of item in products database
        Map<String, Object> database_entry = new HashMap<String, Object>();
        database_entry.put("id",userid);
        database_entry.put("title",title_of_item);
        database_entry.put("price",price_of_item);
        database_entry.put("short", short_description_of_iem);
        database_entry.put("long",long_description_of_iem);
        database_entry.put("image",this.file_in_string);
        product_database.child(userid).setValue(database_entry);


        //Set the id of product in user's database too
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = current_user.getUid();


        DatabaseReference user_database = database.getReference("users").child(user_id).child("myproducts");
        user_database.child(userid).setValue(database_entry);



        //product_database.child(userid).setValue(product);
        Intent intent = new Intent(view.getContext(), Slidermenu.class);
        view.getContext().startActivity(intent);
    }
}
