package com.studentlog.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "roll_no")
    private String rollNo;

    @ColumnInfo(name = "standard")
    private String standard;

    @ColumnInfo(name = "mob_no")
    private String mobileNumber;

    @ColumnInfo(name = "email")
    private String email;


    public Student() {}

    public Student(String userName, String password, String mobileNo) {
        this.name = userName;
        this.password = password;
        this.mobileNumber = mobileNo;
    }

    public Student(String name, String rollNo, String std, String mobNo, String email, String pass) {
        this.name = name;
        this.rollNo = rollNo;
        this.standard = std;
        this.mobileNumber = mobNo;
        this.email = email;
        this.password = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
