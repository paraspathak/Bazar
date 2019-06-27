package com.example.bazar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

public class CreateUser extends AppCompatActivity {

    EditText username, password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        username = (EditText) findViewById(R.id.new_user_username_entry);
        password = (EditText) findViewById(R.id.new_user_password_entry);

        Button cancel = (Button) findViewById(R.id.new_user_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");
            }
        });

        mAuth = FirebaseAuth.getInstance();

        Button create = (Button) findViewById(R.id.new_user_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"New User Created",Toast.LENGTH_SHORT);
                toast.show();
                String user = username.getText().toString();
                String pword = password.getText().toString();

                //Create a new User in database
                mAuth.createUserWithEmailAndPassword(user,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                        }
                        else {

                        }
                    }
                });



                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("pword",pword);

                v.getContext().startActivity(intent);


            }
        });


    }
}
