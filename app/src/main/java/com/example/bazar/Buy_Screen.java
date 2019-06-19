package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class Buy_Screen extends AppCompatActivity {
    TextView test, short_description, number_of_item, price_total;
    double price_of_item;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__screen);
        test = (TextView) findViewById(R.id.title);
        short_description = (TextView) findViewById(R.id.short_description_cart);
        number_of_item = (TextView) findViewById(R.id.number_to_buy);
        price_total = (TextView) findViewById(R.id.price_update);
        Bundle extras = getIntent().getExtras();
        number_of_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    double temp = price_of_item *  Integer.parseInt(s.toString());
                    price_total.setText(String.valueOf(temp));
                }
                else {
                    //If 0 is selected put items price as 1
                    price_total.setText(String.valueOf(price_of_item));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(extras!=null){
            test.setText(extras.getString("title"));
            short_description.setText(extras.getString("description_short"));
            price_of_item=extras.getDouble("price");
            price_total.setText(String.valueOf(price_of_item));
        }


    }
}
