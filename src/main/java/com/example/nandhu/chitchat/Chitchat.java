package com.example.nandhu.chitchat;

import android.app.Application;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by Nandhu on 10-02-2018.
 */

public class Chitchat extends Application {
    Firebase ref;
    private DatabaseReference mUserDatabase;
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
