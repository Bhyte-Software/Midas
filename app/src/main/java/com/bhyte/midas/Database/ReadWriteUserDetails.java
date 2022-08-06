package com.bhyte.midas.Database;

public class ReadWriteUserDetails {
    public String phone, name, mail;

    public ReadWriteUserDetails(String userPhoneNumber, String userFullName, String userEmail){
        this.phone = userPhoneNumber;
        this.name = userFullName;
        this.mail = userEmail;
    }
}
