package com.example.assignment2.Entities;

public class Credentials {
    private String userName;
    private int userId;
    private String passwordhash;
    private String signupdate;

    public Credentials() {
    }

    public Credentials(String userName, int userId, String passwordhash, String signupdate) {
        this.userName = userName;
        this.userId = userId;
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public String getSignupdate() {
        return signupdate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }
}
