package com.bhyte.midas.DataModels;

public class SearchedUsersModel {
    private String id;
    private String name;
    private String mail;

    public SearchedUsersModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Students.class)
    }

    public SearchedUsersModel(String id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

