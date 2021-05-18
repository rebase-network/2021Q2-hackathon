package com.tang.data_entity;

import android.graphics.Bitmap;



public class TokenData {
    Bitmap coinSymbol; //数字货币
    String name;   //
    String balance;  //对应余额，这里没有用float型，用了另一种方式实现，看一下是如何实现的。
    String vakue; //折算为现价

//        public TokenData(String balance,String vakue){
//
//
//        }


    public void setCoinSymbol(Bitmap coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public Bitmap getCoinSymbol() {
        return coinSymbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBalance() {
        return balance;
    }

    public void setVakue(String vakue) {
        this.vakue = vakue;
    }

    public String getVakue() {
        return vakue;
    }


}
