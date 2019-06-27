package com.example.bazar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Contents of the login page
    private EditText Username;
    private EditText Password;
    private Button login_button;
    private String username, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Username=(EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.loginbutton);



        mAuth = FirebaseAuth.getInstance();


        //Check if login credentials match or not
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate_user(Username.getText().toString(),Password.getText().toString());
            }
        });


        //Create a new Account
        TextView new_account = (TextView) findViewById(R.id.create_new_account);
        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(intent);
            }
        });


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("user");
            if(username!=null){
                Username.setText(username);
            }
            password = extras.getString("pword");
            if(password!=null){
                Password.setText(password);
            }
        }

        FirebaseUser current_user = mAuth.getCurrentUser();
        updateUI(current_user);


    }

    private void authenticate_user (String username, String password){
        //Needs to query database here for password and username

        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Toast.makeText(getApplicationContext(), "Authentication succeeded.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Slidermenu.class);
                    //intent.putExtra("username",username);
                    startActivity(intent);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    Username.setText("Incorrect Password");
                }
            }
        });
    }
    public String return_username(){
        return username;
    }
    private void updateUI(FirebaseUser user) {
        if(user!=null){
            if(user.isEmailVerified()){
                Username.setText((user.getEmail()));
                Password.setText(user.getUid());
                Intent intent = new Intent(MainActivity.this, Slidermenu.class);
                intent.putExtra("username",this.username);
                startActivity(intent);
            }

        }
    }

}
