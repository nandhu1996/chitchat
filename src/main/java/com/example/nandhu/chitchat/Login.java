package com.example.nandhu.chitchat;

/**
 * Created by NANDHU on 22-12-2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextView registerUser;
    EditText username, password;
    Button loginButton;
    String mob, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Previous versions of Firebase
        Firebase.setAndroidContext(this);

        getSupportActionBar().setTitle("CHitChAt");

        registerUser = (TextView) findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mob = username.getText().toString();
                pass = password.getText().toString();

                if(mob.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{

                    String url = "https://chitchat-2fd05.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(mob)){
                                        Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(mob).getString("password").equals(pass)){

                                        UserDetails.username = mob;
                                        UserDetails.password = pass;
                                        SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                        editor.putString("login", mob);
                                        editor.apply();
                                        Firebase firebase = new Firebase("https://chitchat-2fd05.firebaseio.com/users/");
                                        Firebase objRef = firebase.child(UserDetails.username);
                                        objRef.child("status").setValue("online");
                                        startActivity(new Intent(Login.this, Users.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                    rQueue.add(request);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this, StartActivity.class));
        finish();
    }
}