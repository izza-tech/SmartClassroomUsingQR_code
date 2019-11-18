package com.example.smartclassroomusingqr_code;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MyDaoTeachers {

    @Insert
    public  void  addteachers(Teacher teacher);


    @Query("select * from teacher")
    public List<Teacher> getteachers();

    @Delete
    public void deleteteacher(Teacher teacher);


    @Update
    public void updateteacher(Teacher teacher);

}
