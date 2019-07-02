package com.example.bazar;

public class  Communicate_with_server{

    private  static String title, id;

    public Communicate_with_server(String name, String id_no) {
        id = id_no;
        title = name;
    }

    public static String getTitle() {
        return title;
    }

    public static String getId() {
        return id;
    }
}