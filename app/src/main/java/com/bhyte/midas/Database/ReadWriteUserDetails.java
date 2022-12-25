package com.bhyte.midas.Database;

public class ReadWriteUserDetails {
    public String phone, name, mail, userMainBalance;

    public ReadWriteUserDetails(String userPhoneNumber, String userFullName, String userEmail, String userMainBalance){
        this.phone = userPhoneNumber;
        this.name = userFullName;
        this.mail = userEmail;
        this.userMainBalance = userMainBalance;
    }
}
