package com.example.week15;

public class Contact {

    String id ;
    String idUser;
    String name;
    String phoneNumber;

    public Contact (String id, String idUser, String name, String phoneNumber) {
        this.idUser = idUser;
        this.id=id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(){

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
