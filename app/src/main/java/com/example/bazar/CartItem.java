package com.example.bazar;

public class CartItem {
    private Product product;
    private Double quantity;

    public CartItem(Product p, Double d){
        this.product = p;
        this.quantity = d;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String get_image_uri(){
        return product.getImage_uri();
    }

    public Double get_price(){
        return Double.valueOf(product.getPrice()) ;
    }

    public String get_title(){
        return product.getTitle();
    }
}
