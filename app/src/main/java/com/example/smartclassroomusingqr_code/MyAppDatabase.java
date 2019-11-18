package com.example.smartclassroomusingqr_code;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Studentss.class},version = 1,exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();
}
