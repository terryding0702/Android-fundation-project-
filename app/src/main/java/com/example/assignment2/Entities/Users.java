package com.example.assignment2.Entities;

public class Users {
    private int userid;
    private String name;
    private String surname;
    private String email;
    private String dob;
    private Double height;
    private Double weight;
    private String gender;
    private String address;
    private String postcode;
    private int levelofactivity;
    private Double stepspermile;

    public Users() {
    }

    public Users(int userid, String name, String surname, String email, String dob, Double height, Double weight, String gender, String address, String postcode, int levelofactivity, Double stepspermile) {
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.levelofactivity = levelofactivity;
        this.stepspermile = stepspermile;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setLevelofactivity(int levelofactivity) {
        this.levelofactivity = levelofactivity;
    }

    public void setStepspermile(Double stepspermile) {
        this.stepspermile = stepspermile;
    }

    public int getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public int getLevelofactivity() {
        return levelofactivity;
    }

    public Double getStepspermile() {
        return stepspermile;
    }
}
