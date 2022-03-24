package com.bhyte.midas.Database;

public class ReadWriteUserDetails {
    public String name, mail;

    public ReadWriteUserDetails(String userFullName, String userEmail){
        this.name = userFullName;
        this.mail = userEmail;
    }
}
