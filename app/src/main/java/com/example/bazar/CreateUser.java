package com.example.bazar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity {
    TextView banner;
    EditText username, password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        username = (EditText) findViewById(R.id.new_user_username_entry);
        password = (EditText) findViewById(R.id.new_user_password_entry);
        banner = (TextView) findViewById(R.id.add_user_progress);

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
                final String user = username.getText().toString();
                String pword = password.getText().toString();

                if(validate_user_input(user,pword)){
                    //Create a new User in database
                    mAuth.createUserWithEmailAndPassword(user,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast toast = Toast.makeText(getApplicationContext(),"New User Created",Toast.LENGTH_SHORT);
                                toast.show();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference users_database = database.getReference("users");
                                Map<String, String> userData = new HashMap<String, String>();
                                FirebaseUser user_current = FirebaseAuth.getInstance().getCurrentUser();
                                if(user_current!=null){
                                    userData.put("email",user);
                                    users_database.child(user_current.getUid()).setValue(userData);
                                }
                                start_activity();
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(),"New User Creation Failed",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });


                }
            }
        });


    }

    private void start_activity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user",username.getText().toString());
        intent.putExtra("pword",password.getText().toString());
        startActivity(intent);
    }

    private boolean validate_user_input(String uname, String pass){
        int count =0;
        if(uname.contains("@") && uname.contains(".")){
            count +=1;
        }
        else {
            banner.setText("Not an email");
        }
        if(pass.length()>6){
            count+=1;
        }
        else {
            banner.setText("password must be at least 6 characters long");
        }
        if(count==2) return true;
        else return false;

    }


}
