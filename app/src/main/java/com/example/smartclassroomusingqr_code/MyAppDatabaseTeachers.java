package com.example.smartclassroomusingqr_code;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Teacher.class},version = 1,exportSchema = false)
public abstract class MyAppDatabaseTeachers extends RoomDatabase {

    public abstract MyDaoTeachers myDaoTeachers();
}
