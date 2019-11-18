package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;

public class fragmentActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static MyAppDatabase myAppDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        fragmentManager=getSupportFragmentManager();

        myAppDatabase= Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"studentdb").allowMainThreadQueries().build();

        if (findViewById(R.id.fragment_container)!=null){
            if (savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
        }
    }
}
