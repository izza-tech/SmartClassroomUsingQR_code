package com.example.smartclassroomusingqr_code;

public class Student {

    private String Reg_no;
    private String Name;
    private String Roll;
    private String Semester;
    private String Contact;
    private String Address;
    private String Department;

    public Student() {
    }

    public Student(String reg_no, String name, String roll, String semester, String contact, String address, String department) {
        Reg_no = reg_no;
        Name = name;
        Roll = roll;
        Semester = semester;
        Contact = contact;
        Address = address;
        Department = department;
    }

    public String getReg_no() {
        return Reg_no;
    }

    public void setReg_no(String reg_no) {
        Reg_no = reg_no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }
}
