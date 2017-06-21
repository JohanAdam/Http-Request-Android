package com.nyan.johan.json;

/**
 * Created by johan on 21/6/2017.
 */

public class Contacts {
    public String id;
    public String name;
    public String email;
    public String address;
    public String gender;

    //public empty constructor
    public Contacts(){

    }

    public Contacts(String i_id, String i_name, String i_email, String i_address, String i_gender){
        this.id = i_id;
        this.name = i_name;
        this.email = i_email;
        this.address = i_address;
        this.gender = i_gender;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
