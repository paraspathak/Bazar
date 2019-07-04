package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class add_credit_card extends AppCompatActivity {

    private Button add_card;
    private EditText credit_card_number, ccv, date_expiry, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);

        credit_card_number = (EditText) findViewById(R.id.add_card_number);
        ccv = (EditText) findViewById(R.id.add_card_ccv);
        date_expiry = (EditText) findViewById(R.id.add_card_date);
        address = (EditText) findViewById(R.id.add_card_address);

        add_card = (Button) findViewById(R.id.add_card_button);
        add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String card_number = credit_card_number.getText().toString();
                String ccv_card = ccv.getText().toString();
                String ccv_date = date_expiry.getText().toString();
                String mailing_add = address.getText().toString();

                if(card_number.isEmpty()||ccv_card.isEmpty()||ccv_date.isEmpty()||mailing_add.isEmpty()){
                    Toast toast = Toast.makeText(v.getContext(),"Fields cannot be empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    //Add the retrieved data to the database

                    //get the current user's id
                    FirebaseAuth user = FirebaseAuth.getInstance();
                    String user_id = user.getUid();

                    //Add the card to the user's profile
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference credit_card = database.getReference("users").child(user_id).child("payment");

                    //Create a new object
                    HashMap<String, String> card_obj = new HashMap<>();
                    card_obj.put("number",card_number);
                    card_obj.put("ccv",ccv_card);
                    card_obj.put("expiry",ccv_date);
                    card_obj.put("address",mailing_add);

                    //Add the hash map to the database
                    credit_card.child(card_number).setValue(card_obj);


                    Toast toast = Toast.makeText(v.getContext(),"New credit card added!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}
