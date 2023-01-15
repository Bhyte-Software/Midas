package com.bhyte.midas.Database;

public class ReadWriteUserDetails {
    public String phone;
    public String name;
    public String mail;
    public String userMainBalance;
    public String transaction;

    public ReadWriteUserDetails(String userPhoneNumber, String userFullName, String userEmail, String userMainBalance, String transaction){
        this.phone = userPhoneNumber;
        this.name = userFullName;
        this.mail = userEmail;
        this.userMainBalance = userMainBalance;
        this.transaction = transaction;
    }
}
