package com.example.bazar;

public class CartItem {
    private Product product;
    private Double quantity;
    private String backup_title;
    public CartItem(Product p, Double d, String title){
        this.product = p;
        this.quantity = d;
        this.backup_title = title;
    }

    public String getBackup_title() {
        return backup_title;
    }

    public Product getProduct() {
        return product;
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
