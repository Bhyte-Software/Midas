package com.bhyte.midas.Recycler;

public class CardsHelperClass {

    int cardImage;
    String cardNumber, date, cardName;

    public CardsHelperClass(int cardImage, String cardName, String cardNumber, String date) {
        this.cardImage = cardImage;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.date = date;
    }

    public int getCardImage() {
        return cardImage;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getDate() {
        return date;
    }
}
