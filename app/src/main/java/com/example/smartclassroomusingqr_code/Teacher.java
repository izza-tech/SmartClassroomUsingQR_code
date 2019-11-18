package com.example.smartclassroomusingqr_code;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teacher")
public class Teacher {

    @PrimaryKey
    private  int password;

    @ColumnInfo(name = "teachers_name")
    private String tname;

    @ColumnInfo(name = "teachers_contact")
    private String tcontact;


    @ColumnInfo(name = "Teachers_adress")
    private String taddress;

    @ColumnInfo(name = "teachers_department")
    private String tdepartment;


    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTcontact() {
        return tcontact;
    }

    public void setTcontact(String tcontact) {
        this.tcontact = tcontact;
    }

    public String getTaddress() {
        return taddress;
    }

    public void setTaddress(String taddress) {
        this.taddress = taddress;
    }

    public String getTdepartment() {
        return tdepartment;
    }

    public void setTdepartment(String tdepartment) {
        this.tdepartment = tdepartment;
    }
}
