package com.example.bazar;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {
    private TextView price_dislpay, info_card ;
    private Button buy_button, new_payment_method;
    private EditText address_field;
    private RecyclerView cards;

    private ArrayList<String> card_number, card_expiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        address_field = (EditText) findViewById(R.id.address_field);
        price_dislpay = (TextView) findViewById(R.id.credit_card_price);
        cards = (RecyclerView) findViewById(R.id.stored_credit_card);
        buy_button = (Button) findViewById(R.id.payment_buy);
        info_card = (TextView) findViewById(R.id.info_cards_display);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
         Double price = extras.getDouble("total");
         price_dislpay.setText(String.valueOf(price));
        }

        card_number = new ArrayList<>();
        card_expiry = new ArrayList<>();

        FirebaseAuth curent_user = FirebaseAuth.getInstance();
        final String id = curent_user.getUid();

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address_field.getText()!=null){


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference my_cart = database.getReference("users").child(id).child("cart");
                    my_cart.removeValue();  //Remove cart element in it
                    ProductsDatabase.empty_cart();  //Remove local data too
                    Toast toast = Toast.makeText(v.getContext(),"Items bought!!",Toast.LENGTH_SHORT);
                    toast.show();
                    buy_button.setEnabled(false);   //Disable the button

                    Intent intent = new Intent(v.getContext(),Slidermenu.class);
                    v.getContext().startActivity(intent);
                }

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference payment = database.getReference("users").child(id).child("payment");

        payment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> data = (Map<String, Object>)dataSnapshot.getValue();
                if(data!=null){
                    update_layout(data);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Can't connect to database",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        cards.setLayoutManager(layoutManager);
        cards.setHasFixedSize(true);


        new_payment_method = (Button) findViewById(R.id.add_credit_card);
        new_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the screen to add credit card information
                Intent intent = new Intent(v.getContext(), add_credit_card.class);
                v.getContext().startActivity(intent);
            }
        });
    }


    public void update_layout(Map<String,Object> data){
        //loop through each and every item and then add it to the arraylist
        for(Map.Entry<String,Object> entry:data.entrySet()) {
            Map single_item = (Map)entry.getValue();


            card_number.add((String)single_item.get("number"));
            card_expiry.add((String)single_item.get("expiry"));
        }
        cards.setAdapter(new CreditCardAdapter(card_number,card_expiry,info_card));
    }
}
