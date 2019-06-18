package com.example.bazar;


//Class for putting products on sale
public class Items_on_sale {
    private int product_id;
    private String product_title;
    private double product_price;
    private String product_description;
    private String product_description_long;
    private String product_image_location;

    public Items_on_sale(int product_id, String product_title, double product_price, String product_description, String product_description_long, String product_image_location) {
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_description_long = product_description_long;
        this.product_image_location = product_image_location;
    }

    public String getProduct_description_long() {
        return product_description_long;
    }

    public void setProduct_description_long(String product_description_long) {
        this.product_description_long = product_description_long;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public double getProduct_price() {
        return product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_image_location() {
        return product_image_location;
    }

    //Cannot change the product id!!
    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_image_location(String product_image_location) {
        this.product_image_location = product_image_location;
    }
}
