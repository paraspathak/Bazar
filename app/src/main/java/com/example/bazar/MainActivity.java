package com.example.bazar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Contents of the login page
    private EditText Username;
    private EditText Password;
    private Button login_button;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username=(EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.loginbutton);
        username = Username.getText().toString();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate_user(Username.getText().toString(),Password.getText().toString());
            }
        });
    }

    private void authenticate_user (String username, String password){
        //Needs to query database here for password and username
        //Currently only implemented for admin admin
        if(username.equals("admin") && password.equals("admin")){
            Intent intent = new Intent(MainActivity.this, Slidermenu.class);
            startActivity(intent);
            intent.putExtra("username",this.username);
        }
        else{
            Username.setText("Incorrect Password");
        }
    }
    public String return_username(){
        return username;
    }

}
