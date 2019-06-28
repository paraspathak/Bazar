package com.example.bazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSettings extends AppCompatActivity {
    private String password;
    TextView pword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        pword = (TextView) findViewById(R.id.settings_password);
        TextView username = (TextView) findViewById(R.id.settings_username);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
                username.setText(user.getEmail());
                password = user.getUid();
        }

        Button button = (Button) findViewById(R.id.settings_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pword.setText(password);
            }
        });
    }
}
