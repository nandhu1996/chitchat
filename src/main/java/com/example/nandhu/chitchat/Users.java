package com.example.nandhu.chitchat;

/**
 * Created by NANDHU on 22-12-2017.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Users extends AppCompatActivity {
    ListView usersList;
    RecyclerviewAdapter adapter;
    Userslist users;
    TextView noUsersText;
    RecyclerView recyclerView;
    ArrayList<String> al = new ArrayList<>();
    List<Userslist> list = new ArrayList<Userslist>();
    int totalUsers = 0;
    ProgressDialog pd;
    SharedPreferences sharedpreferences;
    boolean doubleBackToExitPressedOnce = false;
    boolean statuschanged=false;
    Firebase ref;
    public String status;
    DatabaseReference scoresRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Previous versions of Firebase
        Firebase.setAndroidContext(this);
        /*if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }*/
        getSupportActionBar().setTitle("CHitChAt");
        /*FirebaseDatabase.getInstance().getReference("users");
        scoresRef.keepSynced(true);*/
        ref=new Firebase("https://chitchat-2fd05.firebaseio.com/users");
        adapter = new RecyclerviewAdapter(this, list);
        //usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                String url = "https://chitchat-2fd05.firebaseio.com/users.json";

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {
                        list.clear();
                        adapter.notifyDataSetChanged();

                        doOnSuccess(s);
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("" + volleyError);
                    }
                });
                RequestQueue rQueue = Volley.newRequestQueue(Users.this);
                rQueue.add(request);
            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query filterQuery = databaseReference.orderByChild("status").equalTo("online");
        filterQuery.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Toast.makeText(Users.this, "changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

       /* ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);

                *//*String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String time = map.get("time").toString();
                String status=map.get("status").toString();*//*


            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                Map map1 = dataSnapshot.getValue(Map.class);
            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
                recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        UserDetails.chatWith = list.get(position).getmob();
                        UserDetails.chatWithName = list.get(position).getname();
                        startActivity(new Intent(Users.this,Chat.class));
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                })
        );
        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://chitchat-2fd05.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

        /*usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
                finish();
            }
        });*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplication()).inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.phone:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure want to Logout?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                SharedPreferences preferences = getSharedPreferences("login", 0);
                                preferences.edit().remove("login").commit();
                                startActivity(new Intent(Users.this,Login.class));
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Firebase firebase = new Firebase("https://chitchat-2fd05.firebaseio.com/users/");
            Firebase objRef = firebase.child(UserDetails.username);
            objRef.child("status").setValue("offline");
            finish();
        }else{
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            String mob="";
            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    mob=obj.getJSONObject(key).getString("name");
                    status=obj.getJSONObject(key).getString("status");
                    users = new Userslist(mob,key,status);
                    list.add(users);
                    //al.add(key);


                }

                totalUsers++;
            }

        } catch (JSONException e) {
            System.out.println("errorjson"+e.getMessage());
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
           /* String url = "https://chitchat-2fd05.firebaseio.com/users.json";

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    list.clear();
                    doOnSuccess(s);
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(Users.this);
            rQueue.add(request);*/
            //usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }
}