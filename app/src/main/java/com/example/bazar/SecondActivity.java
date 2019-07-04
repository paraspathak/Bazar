package com.example.bazar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {
    TextView price_dislpay ;
    Button buy_button, new_payment_method;
    EditText address_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        address_field = (EditText) findViewById(R.id.address_field);
        price_dislpay = (TextView) findViewById(R.id.credit_card_price);

        buy_button = (Button) findViewById(R.id.payment_buy);
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address_field.getText()!=null){
                    FirebaseAuth curent_user = FirebaseAuth.getInstance();
                    String id = curent_user.getUid();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference my_cart = database.getReference("users").child(id).child("cart");
                    my_cart.removeValue();  //Remove cart element in it
                    ProductsDatabase.empty_cart();  //Remove local data too

                    Toast toast = Toast.makeText(v.getContext(),"Items bought!!",Toast.LENGTH_SHORT);
                    toast.show();
                    buy_button.setEnabled(false);   //Disable the button
                }

            }
        });

        new_payment_method = (Button) findViewById(R.id.add_credit_card);
        new_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Make fields visible
                EditText card_number = (EditText) v.findViewById(R.id.credit_card_number);
                card_number.setVisibility(View.VISIBLE);
                EditText expiry = (EditText) v.findViewById(R.id.date_expiry);
                expiry.setVisibility(View.VISIBLE);
                EditText ccv = (EditText) v.findViewById(R.id.ccv_number);
                ccv.setVisibility(View.VISIBLE);

                //Make other items invisible



                //Get text on accept click
                if(!(card_number.getText().equals("") || expiry.getText().equals("") || ccv.getText().equals("") )){
                    //<TODO> Add the items to the database which links in the recycler view of payment screen
                    Toast toast = Toast.makeText(getApplicationContext(), "Payment Field added", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Empty Fields!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }
}
