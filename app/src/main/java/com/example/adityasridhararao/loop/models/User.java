package com.example.adityasridhararao.loop.models;

import android.graphics.Bitmap;

/**
 * Created by aditya.sridhara.rao on 6/5/2016.
 */
public class User {
    public String username;
    public String email;
    public String phNum;
    public String password;
    public Bitmap compId;
    public String carRegNum;
    public Bitmap DL;
    public Bitmap carRegSnap;
    public String isDriver;
    public String company;
    public Bitmap photo;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }



    public User(String username, String email, String phNum, String password, Bitmap compId, String carRegNum, Bitmap DL, Bitmap carRegSnap, String isDriver, String company, Bitmap photo) {
        this.username = username;
        this.email = email;
        this.phNum = phNum;
        this.password = password;
        this.compId = compId;
        this.carRegNum = carRegNum;
        this.DL = DL;
        this.carRegSnap = carRegSnap;
        this.isDriver = isDriver;
        this.company = company;
        this.photo = photo;
    }
}
