package com.bhyte.midas.DataModels;

public class SearchedUsersModel {
    private String id;
    private String name;
    private String email;

    public SearchedUsersModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Students.class)
    }

    public SearchedUsersModel(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
}

