package com.example.smartclassroomusingqr_code;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Studentss {

    @PrimaryKey
    private  int rollNo;

    @ColumnInfo(name = "student_name")
    private String name;

    @ColumnInfo(name = "student_registeration")
    private int registeration;

    @ColumnInfo(name = "student_semester")
    private int semester;

    @ColumnInfo(name = "student_contact")
    private int contact;

    @ColumnInfo(name = "student_adress")
    private String address;

    @ColumnInfo(name = "student_department")
    private String department;

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegisteration() {
        return registeration;
    }

    public void setRegisteration(int registeration) {
        this.registeration = registeration;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
