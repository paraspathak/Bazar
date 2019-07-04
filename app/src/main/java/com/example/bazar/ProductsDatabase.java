package com.example.bazar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.bazar.Product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductsDatabase {
    private static final ProductsDatabase ourInstance = new ProductsDatabase();

    private static ArrayList<Double> quantity_products_in_cart;
    private static ArrayList<Product> products_in_cart;
    private static Product selected_product;
    private static HashMap<String,Product> products_by_id;
    private static String last_name, last_id;
    private ArrayList<Product> products_in_sale;

    public static ProductsDatabase getInstance() {
        return ourInstance;
    }

    private ProductsDatabase() {
        products_in_cart = new ArrayList<>();
        products_in_sale = new ArrayList<>();
        products_by_id = new HashMap<String, Product>();
        quantity_products_in_cart = new ArrayList<>();
    }

    static public void add_to_cart(Product product, Double quantity){
        products_in_cart.add(product);
        quantity_products_in_cart.add(quantity);
    }

    static public String getLast_name(){
        return last_name;
    }

    public static void setLast_name(String last_name) {
        ProductsDatabase.last_name = last_name;
    }

    public static void setLast_id(String last_id) {
        ProductsDatabase.last_id = last_id;
    }

    public static String getLast_id() {
        return last_id;
    }

    static public ArrayList<Product> items_in_cart(){
        return products_in_cart;
    }

    static public ArrayList<Double> quantity_of_cart(){
        return quantity_products_in_cart;
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

    static public void empty_cart(){
        products_in_cart.clear();
    }

    static public Product Get_product_with_key(String key){
        Product temp = products_by_id.get(key);
        products_by_id.remove(key);
        return temp;
    }

    static public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    static public String BitMapToString(Bitmap bit){
        //Reduce the size to 200x200
        Bitmap bitmap = Bitmap.createScaledBitmap(bit,200,200, false);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        //Compress the bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG,50, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
