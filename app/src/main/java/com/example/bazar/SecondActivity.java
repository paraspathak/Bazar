package com.example.bazar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {
    private TextView price_dislpay ;
    private Button buy_button, new_payment_method;
    private EditText address_field;
    private RecyclerView cards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        address_field = (EditText) findViewById(R.id.address_field);
        price_dislpay = (TextView) findViewById(R.id.credit_card_price);
        cards = (RecyclerView) findViewById(R.id.stored_credit_card);
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
                Button add = (Button) v.findViewById(R.id.add_credit_card);
                add.setVisibility(View.VISIBLE);
                Button cancel = (Button) v.findViewById(R.id.cancel_card);
                cancel.setVisibility(View.VISIBLE);


                //Make other items invisible
                TextView info = (TextView) v.findViewById(R.id.info);
                info.setVisibility(View.INVISIBLE);
                price_dislpay.setVisibility(View.INVISIBLE);
                buy_button.setVisibility(View.INVISIBLE);
                new_payment_method.setVisibility(View.INVISIBLE);
                cards.setVisibility(View.INVISIBLE);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Get text on accept click
                        EditText card_number = (EditText) v.findViewById(R.id.credit_card_number);
                        EditText expiry = (EditText) v.findViewById(R.id.date_expiry);
                        EditText ccv = (EditText) v.findViewById(R.id.ccv_number);
                        if(!(card_number.getText().equals("") || expiry.getText().equals("") || ccv.getText().equals("") )){
                            //<TODO> Add the items to the database which links in the recycler view of payment screen
                            Toast toast = Toast.makeText(getApplicationContext(), "Payment Field added", Toast.LENGTH_SHORT);
                            toast.show();

                            //Make them invisible
                            card_number.setVisibility(View.INVISIBLE);
                            expiry.setVisibility(View.INVISIBLE);
                            ccv.setVisibility(View.INVISIBLE);

                            //Make every other thing visible
                            TextView info = (TextView) v.findViewById(R.id.info);
                            info.setVisibility(View.VISIBLE);
                            price_dislpay.setVisibility(View.VISIBLE);
                            buy_button.setVisibility(View.VISIBLE);
                            new_payment_method.setVisibility(View.VISIBLE);
                            cards.setVisibility(View.VISIBLE);


                        }
                        else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Empty Fields!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText card_number = (EditText) v.findViewById(R.id.credit_card_number);
                        EditText expiry = (EditText) v.findViewById(R.id.date_expiry);
                        EditText ccv = (EditText) v.findViewById(R.id.ccv_number);

                        //Make them invisible
                        card_number.setVisibility(View.INVISIBLE);
                        expiry.setVisibility(View.INVISIBLE);
                        ccv.setVisibility(View.INVISIBLE);

                        //Make every other thing visible
                        TextView info = (TextView) v.findViewById(R.id.info);
                        info.setVisibility(View.VISIBLE);
                        price_dislpay.setVisibility(View.VISIBLE);
                        buy_button.setVisibility(View.VISIBLE);
                        new_payment_method.setVisibility(View.VISIBLE);
                        cards.setVisibility(View.VISIBLE);


                    }
                });
            }
        });


    }
}
