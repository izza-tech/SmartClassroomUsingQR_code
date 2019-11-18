package com.example.smartclassroomusingqr_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;

public class teacherActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static MyAppDatabaseTeachers myAppDatabaseTeachers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        fragmentManager=getSupportFragmentManager();

        myAppDatabaseTeachers= Room.databaseBuilder(getApplicationContext(),MyAppDatabaseTeachers.class,"teacherdb").allowMainThreadQueries().build();

        if (findViewById(R.id.fragment_container2)!=null){
            if (savedInstanceState!=null){
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container2,new ManagetyeachersFragment()).commit();
        }
    }
}
