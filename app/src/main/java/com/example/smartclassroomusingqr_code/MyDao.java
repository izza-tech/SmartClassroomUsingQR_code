package com.example.smartclassroomusingqr_code;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public  void  addStudent(Studentss studentss);

    @Query("select * from students")
    public List<Studentss> getstudentss();

    @Delete
    public void deletestudent(Studentss studentss);


    @Update
    public void updatestudent(Studentss studentss);
}
