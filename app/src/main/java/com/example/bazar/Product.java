package com.example.bazar;


public class Product{
    private String username, title, price, short_description, long_description, user_id, image_uri;
    private int mData;

    public Product(String username, String title_, String price, String short_description, String long_description, String image_url) {
        super();
        this.username = username;
        this.title = title_;
        this.image_uri = image_url;
        this.price = price;
        this.short_description = short_description;
        this.long_description = long_description;
    }
    public void setUser_id(String user_id){
        this.user_id=user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

}
