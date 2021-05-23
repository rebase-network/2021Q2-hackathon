package com.tang.blockchain;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

//这个用Greeendao保存，无需序列化
@Entity
public class Token {
//    public final TokenInfo tokenInfo;
    @Id(autoincrement = true)
    private Long id;  //本id值自动增加，实际上暂时用不上这个值
    public String walletAddrges;//钱包地址
    public  String address;  //非钱包地址，是Token对应的合约地址。
    public  String name;
    public  String symbol;
    public  int decimals;
    public  String balance;
    public String value;
    private boolean IsDisply;  //表示该token是否在本钱包中显示。


    @Generated(hash = 1503067990)
    public Token(Long id, String walletAddrges, String address, String name, String symbol,
            int decimals, String balance, String value, boolean IsDisply) {
        this.id = id;
        this.walletAddrges = walletAddrges;
        this.address = address;
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
        this.balance = balance;
        this.value = value;
        this.IsDisply = IsDisply;
    }

    @Generated(hash = 79808889)
    public Token() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getWalletAddrges() {
        return this.walletAddrges;
    }

    public void setWalletAddrges(String walletAddrges) {
        this.walletAddrges = walletAddrges;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getDecimals() {
        return this.decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getIsDisply() {
        return this.IsDisply;
    }

    public void setIsDisply(boolean IsDisply) {
        this.IsDisply = IsDisply;
    }




}
