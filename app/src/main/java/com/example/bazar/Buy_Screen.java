package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class Buy_Screen extends AppCompatActivity {
    TextView test, short_description, number_of_item, price_total, success_screen;
    double price_of_item, current_total;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__screen);
        test = (TextView) findViewById(R.id.title);
        short_description = (TextView) findViewById(R.id.short_description_cart);
        number_of_item = (TextView) findViewById(R.id.number_to_buy);
        price_total = (TextView) findViewById(R.id.price_update);
        success_screen = (TextView) findViewById(R.id.Success_banner);
        Bundle extras = getIntent().getExtras();
        number_of_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    current_total = price_of_item *  Integer.parseInt(s.toString());
                    price_total.setText(String.valueOf(current_total));
                }
                else {
                    //If 0 is selected put items price as 1
                    price_total.setText(String.valueOf(price_of_item));
                    current_total = price_of_item;
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
            current_total=price_of_item;
        }


    }
    public void add_to_cart(View view){
        success_screen.setText("Item successfully added");
    }

    public void buy_click(View view){
        success_screen.setText("Start a new intent of buying item");
    }
}
