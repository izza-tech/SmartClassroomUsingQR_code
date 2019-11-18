package com.example.smartclassroomusingqr_code;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class Issue_Disscusion extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE=1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton,submitButton;
    EmojIconActions emojIconActions;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activity_main,"you have been signed out ",Snackbar.LENGTH_SHORT).show();
                    finish();

                }
            });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SIGN_IN_REQUEST_CODE){
            if (resultCode==RESULT_OK)
            {
                Snackbar.make(activity_main,"successfully signed in welcome ",Snackbar.LENGTH_SHORT).show();
                displaychatmessage();

            }else {
                Snackbar.make(activity_main,"we couldn't sign you in please try again later",Snackbar.LENGTH_SHORT).show();
                finish();

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue__disscusion);
        getSupportActionBar().setTitle("Issue Disscusion");

        activity_main=(RelativeLayout) findViewById(R.id.activity_main);
        emojiButton=(ImageView)findViewById(R.id.emoji_button);
        submitButton=(ImageView)findViewById(R.id.submit_button);
        emojiconEditText=(EmojiconEditText)findViewById(R.id.emojicon_edit_text);
        emojIconActions=new EmojIconActions(getApplicationContext(),activity_main,emojiButton,emojiconEditText);
        emojIconActions.ShowEmojicon();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("My_chats").push().setValue(new ChatMessage(emojiconEditText.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                emojiconEditText.setText("");

            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);

        }else {
            Snackbar.make(activity_main,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            displaychatmessage();
        }

    }
    private void displaychatmessage() {
        ListView listofMessage= (ListView)findViewById(R.id.list_of_message);

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("My_chats"), ChatMessage.class).setLayout(R.layout.list_item).build();

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                TextView messageText,messageUser,messageTime;
                messageText=(BubbleTextView)v.findViewById(R.id.message_text);
                messageUser=(TextView)v.findViewById(R.id.message_user);
                messageTime=(TextView)v.findViewById(R.id.message_time);
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));

            }
        };
        listofMessage.setAdapter(adapter);
        adapter.startListening();

    }
}


