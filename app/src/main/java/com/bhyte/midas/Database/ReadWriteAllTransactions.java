package com.bhyte.midas.Database;

public class ReadWriteAllTransactions {
    public String transactionType;
    public String transactionDate;
    public String transactionCurrency;
    public String transactionAmount;

    public ReadWriteAllTransactions(String transactionType, String transactionDate, String transactionCurrency, String transactionAmount){
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionCurrency = transactionCurrency;
        this.transactionAmount = transactionAmount;
    }

}
