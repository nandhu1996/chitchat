package com.example.nandhu.chitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class StartActivity extends AppCompatActivity {


    private Button mRegBtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegBtn = (Button) findViewById(R.id.start_reg_btn);
        mLoginBtn = (Button) findViewById(R.id.start_login_btn);
        Firebase.setAndroidContext(this);
        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sharedpreferences.contains("login")) {
            UserDetails.username = sharedpreferences.getString("login", "");
            Firebase firebase = new Firebase("https://chitchat-2fd05.firebaseio.com/users/");
            Firebase objRef = firebase.child(UserDetails.username);
            objRef.child("status").setValue("online");
            startActivity(new Intent(StartActivity.this,Users.class));
            finish();
        }
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent reg_intent = new Intent(StartActivity.this, Register.class);
                startActivity(reg_intent);

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent login_intent = new Intent(StartActivity.this, Login.class);
                startActivity(login_intent);

            }
        });

    }
}
