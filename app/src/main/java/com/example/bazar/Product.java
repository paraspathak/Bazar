package com.example.bazar;

import android.net.Uri;

public class Product {
    private String username, title, price, short_description, long_description, user_id, image_uri;
    public Product(String username, String title, String price, String short_description, String long_description, String image_url) {
        super();
        this.username = username;
        this.title = title;
        this.image_uri = image_url;
        this.price = price;
        this.short_description = short_description;
        this.long_description = long_description;
    }
    public void setUser_id(String user_id){
        this.user_id=user_id;
    }
}
