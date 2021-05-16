package com.tang.data_entity;

import android.graphics.Bitmap;

//本人参与过的影视评定数据。这个数据无需直接从数据库中获得，只要从参评及已评定中获得即可。
public class MyEvaluatedFilmDataForUIEntity {
    String MyPartInFilmId;
    String MyPartInFilmName; //电影名称
    Bitmap MyPartInFilmPic;//电影图片；
    String MyPartInNum; //参与人数
    String MyPartInprofit; //收益
    String MyPartInIncomRate;//收益率
    String MyPartInboxOffice;  //票房
    String MyPartIntreasureGrade;//矿藏等级。1表示铁矿；2表示铜矿；3表示银矿；4表示金矿；5表示砖石矿。
    // 最简单的是根据票房定，麻烦一点就是根据每个人的收入来定。最好根据个的收益来定等级
    //对于上面的数据有的可以直接


    public Bitmap getMyPartInFilmPic() {
        return MyPartInFilmPic;
    }

    public void setMyPartInFilmPic(Bitmap myPartInFilmPic) {
        MyPartInFilmPic = myPartInFilmPic;
    }

    public String getMyPartInFilmName() {
        return MyPartInFilmName;
    }

    public void setMyPartInFilmName(String myPartInFilmName) {
        MyPartInFilmName = myPartInFilmName;
    }

    public String getMyPartInNum() {
        return MyPartInNum;
    }

    public void setMyPartInNum(String myPartInNum) {
        MyPartInNum = myPartInNum;
    }

    public String getMyPartInprofit() {
        return MyPartInprofit;
    }

    public void setMyPartInprofit(String myPartInprofit) {
        MyPartInprofit = myPartInprofit;
    }

    public String getMyPartInIncomRate() {
        return MyPartInIncomRate;
    }

    public void setMyPartInIncomRate(String myPartInIncomRate) {
        MyPartInIncomRate = myPartInIncomRate;
    }

    public String getMyPartInboxOffice() {
        return MyPartInboxOffice;
    }

    public void setMyPartInboxOffice(String myPartInboxOffice) {
        MyPartInboxOffice = myPartInboxOffice;
    }

    public String getMyPartIntreasureGrade() {
        return MyPartIntreasureGrade;
    }

    public void setMyPartIntreasureGrade(String myPartIntreasureGrade) {
        MyPartIntreasureGrade = myPartIntreasureGrade;
    }
}
