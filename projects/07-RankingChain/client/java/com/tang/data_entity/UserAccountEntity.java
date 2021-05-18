package com.tang.data_entity;

import java.io.Serializable;
import java.util.ArrayList;

/*
//账户实现方案：
    用户只需注册一个唯一的账号就即可，无需密码。为了安全后面可以通过微信或者手机来确定账号。目前就用最简单的注册即可
    并且这个账号可以和一个钱包关联，账号忘记了可以根据钱包找到账号，并且钱包账号可以替换

*/

public class UserAccountEntity implements Serializable {

    public String userId;  //这个账号之所以需要并且比较重要，主要
    public String walletAccount;
    //正在参与的信息直接从区块链中读取即可，无需数据库保存
    //对应参与过的影视信息
    private ArrayList<MyEvaluatedFilmDataFromServerEntity> filmDataEvaluatedDatas =new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWalletAccount() {
        return walletAccount;
    }

    public void setWalletAccount(String walletAccount) {
        this.walletAccount = walletAccount;
    }

    public ArrayList<MyEvaluatedFilmDataFromServerEntity> getFilmDataEvaluatedDatas() {
        return filmDataEvaluatedDatas;
    }

    public void setFilmDataEvaluatedDatas(ArrayList<MyEvaluatedFilmDataFromServerEntity> filmDataEvaluatedDatas) {
        this.filmDataEvaluatedDatas = filmDataEvaluatedDatas;
    }
}
