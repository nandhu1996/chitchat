package com.example.nandhu.chitchat;

/**
 * Created by NANDHU on 22-12-2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2,back;
    ImageView sendButton,tick;
    ImageView emojiImageView;
    EmojiconEditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    EmojIconActions emojIcon;
    View rootView;
    String localTime;
    TextView actiontext,actiontext1;
    boolean statuschanged=false;
    private static final String TAG = Chat.class.getSimpleName();
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent chatmain=new Intent(Chat.this,Users.class);
                startActivity(chatmain);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        emojiImageView = (ImageView) findViewById(R.id.emoji_btn);
        rootView = findViewById(R.id.rootview);
        messageArea = (EmojiconEditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        back= (RelativeLayout) findViewById(R.id.backpress);
        actiontext= (TextView) findViewById(R.id.actiontext);
        actiontext1= (TextView) findViewById(R.id.actiontext1);
        getSupportActionBar().hide();
        actiontext.setText(UserDetails.chatWithName);
        actiontext1.setText(UserDetails.chatWith);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent chatmain=new Intent(Chat.this,Users.class);
                startActivity(chatmain);*/
                finish();
            }
        });
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.imageview,null);
        getSupportActionBar().setCustomView(v);
        getSupportActionBar().setTitle(""+UserDetails.chatWith);*/
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chitchat-2fd05.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chitchat-2fd05.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        emojIcon = new EmojIconActions(this, rootView, messageArea, emojiImageView);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                Calendar cal = Calendar.getInstance();

                int millisecond = cal.get(Calendar.MILLISECOND);
                int second = cal.get(Calendar.SECOND);
                int minute = cal.get(Calendar.MINUTE);
                //12 hour format
                int hour = cal.get(Calendar.HOUR);
                localTime =hour+":"+minute+" "+new SimpleDateFormat("a").format(new Date());
                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("time", localTime);
                    map.put("status", "0");
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);

                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String time = map.get("time").toString();
                String status=map.get("status").toString();

                if(userName.equals(UserDetails.username)){
                    //addMessageBox("You:-\n" + message, 1);
                    //Toast.makeText(Chat.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    /*reference2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Firebase firebase = new Firebase("https://chitchat-2fd05.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
                            Firebase objRef = firebase.child(dataSnapshot.getKey());
                            objRef.child("status").setValue("1");

                        }*/
                    addMessageBox(message, 1,time);
                }
                else{

                    //addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    addMessageBox(message, 2,time);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void addMessageBox(String message, int type,String time){
        TextView textView = new TextView(Chat.this);
        TextView textView1 = new TextView(Chat.this);
        textView.setText(message);
        textView.setTextSize(16);
        textView1.setTextSize(12);
        textView1.setText(time);
        tick=new ImageView(Chat.this);
        String imagename = "tick2";
        int res = getResources().getIdentifier(imagename, "drawable", this.getPackageName());


//    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),imgId);

        /*textView.setPadding(15,10,15,10);
        GradientDrawable gd1=new GradientDrawable();
        gd1.setColor(0xFF4081);
        gd1.setCornerRadius(5);
        gd1.setStroke(3,0xFF000000);*/
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            //lp2.setMargins(150,10,0,10);
            textView.setPadding(50,50,50,50);
            textView1.setPadding(20,0,0,0);
            //tick.setImageDrawable(getDrawable(R.drawable.tick2));
            tick.setImageResource(res);
            textView.setBackgroundResource(R.drawable.bubble_in);
            //textView.setBackground(gd1);

        }
        else{
            lp2.gravity = Gravity.LEFT;
            //lp2.setMargins(0,10,150,10);
            textView.setPadding(50,50,50,50);
            textView.setBackgroundResource(R.drawable.bubble_out);
            //textView.setBackground(gd1);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        textView1.setLayoutParams(lp2);
        layout.addView(textView1);
        tick.setLayoutParams(lp2);
        layout.addView(tick);
        textView.clearFocus();
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        //scrollView.fullScroll(View.FOCUS_DOWN);

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}