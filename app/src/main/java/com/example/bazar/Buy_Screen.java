package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Buy_Screen extends AppCompatActivity {
    Communicate_with_server server;
    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy__screen);
        test = (TextView) findViewById(R.id.test);
        server = new Communicate_with_server();
        Bundle extras = getIntent().getExtras();
        int id;

        if(extras!=null){
            id = extras.getInt("id");
            test.setText(String.valueOf(id));
            if(server==null){
                test.setText("Server cannot be initialized");
            }
            else {
                Items_on_sale items = server.return_marketplace_at_id(id);
                if(items!=null){
                  test.setText(items.getProduct_title());
                }
                else {
                //Handle error case here
                  test.setText("Null Item");
                }
            }
            //


        }


    }
}
