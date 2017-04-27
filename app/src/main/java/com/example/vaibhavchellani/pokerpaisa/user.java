package com.example.vaibhavchellani.pokerpaisa;

/**
 * Created by vaibhavchellani on 4/25/17.
 */

public class user {
    String userName;
    Integer userCoins;

    public user() {
    }

    public user(String userName, Integer userCoins) {
        this.userName = userName;
        this.userCoins = userCoins;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserCoins() {
        return userCoins;
    }
}
