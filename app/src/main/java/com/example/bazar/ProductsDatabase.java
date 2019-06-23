package com.example.bazar;

import com.example.bazar.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsDatabase {
    private static final ProductsDatabase ourInstance = new ProductsDatabase();
    private static ArrayList<Product> products_in_cart;
    private static Product selected_product;
    private static HashMap<String,Product> products_by_id;

    public static ProductsDatabase getInstance() {
        return ourInstance;
    }
    private ArrayList<Product> products_in_sale;

    private ProductsDatabase() {
        products_in_cart = new ArrayList<>();
        products_in_sale = new ArrayList<>();
        products_by_id = new HashMap<String, Product>();
    }

    static  public void add_to_cart(Product product){
        products_in_cart.add(product);
    }

    static public Product get_product(){
        return products_in_cart.get(0);
    }

    static public void selected_product(Product product){
        selected_product = product;
    }

    static public Product get_selected_product(){
        return selected_product;
    }

    static public void Add_product_with_key(Product product,String key){
        products_by_id.put(key,product);
    }

    static public Product Get_product_with_key(String key){
        Product temp = products_by_id.get(key);
        products_by_id.remove(key);
        return temp;
    }

}
