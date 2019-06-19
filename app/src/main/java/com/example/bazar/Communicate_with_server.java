package com.example.bazar;

import java.util.ArrayList;

public class Communicate_with_server {
    static ArrayList<Items_on_sale> products_in_bazar;
    static ArrayList<Items_on_sale> my_products_in_bazar;
    public Communicate_with_server() {
        products_in_bazar = new ArrayList<>();
        //Connect with server here
        products_in_bazar.add(new Items_on_sale(0,"Mouse",99.99,"Apple Mouse", "Awesome","/src/logo.png"));
        products_in_bazar.add(new Items_on_sale(1,"Keyboard",199.99,"Apple Keyboard","Awesome","/src/logo.png"));
        products_in_bazar.add(new Items_on_sale(2,"Laptop",999.99, "Bona fide laptop","Awesome","/res/drawable/logo.png"));
        products_in_bazar.add(new Items_on_sale(3,"Phone",799.99, "Bona fide phone","Awesome","/res/drawable/logo.png"));
        products_in_bazar.add(new Items_on_sale(4,"Tablet",499.99, "Bona fide tablet","Awesome","/res/drawable/logo.png"));
        products_in_bazar.add(new Items_on_sale(5,"Camera",599.99, "Bona fide camera","Awesome","/res/drawable/logo.png"));
    }

    static ArrayList<Items_on_sale> get_my_items_on_sale(){
        ArrayList<Items_on_sale> my_products_in_bazar= new ArrayList<>();
        //Demo items currently
        //Need to load items from database here
        my_products_in_bazar.add(new Items_on_sale(0,"Mouse",99.99,"Apple Mouse","Awesome","/src/logo.png"));
        my_products_in_bazar.add(new Items_on_sale(1,"Keyboard",199.99,"Apple Keyboard","Awesome","/src/logo.png"));
        return my_products_in_bazar;
    }
    static ArrayList<Items_on_sale> get_marketplace_items_on_sale(){
        ArrayList<Items_on_sale> products_on_bazar= new ArrayList<>();
        //Demo items currently
        //Need to load items from database here
        products_on_bazar.add(new Items_on_sale(0,"Mouse",99.99,"Apple Mouse", "Awesome","/src/logo.png"));
        products_on_bazar.add(new Items_on_sale(1,"Keyboard",199.99,"Apple Keyboard","Awesome","/src/logo.png"));
        products_on_bazar.add(new Items_on_sale(2,"Laptop",999.99, "Bona fide laptop","Awesome","/res/drawable/logo.png"));
        products_on_bazar.add(new Items_on_sale(3,"Phone",799.99, "Bona fide phone","Awesome","/res/drawable/logo.png"));
        products_on_bazar.add(new Items_on_sale(4,"Tablet",499.99, "Bona fide tablet","Awesome","/res/drawable/logo.png"));
        products_on_bazar.add(new Items_on_sale(5,"Camera",599.99, "Bona fide camera","Awesome","/res/drawable/logo.png"));

        return products_on_bazar;
    }
    static Items_on_sale return_marketplace_at_id(int i){
    //Connect to server here to get an id
        for( Items_on_sale items : products_in_bazar){
            if(items.getProduct_id()==i){
                return products_in_bazar.get(i);
            }
        }
        return null;
    }
}
