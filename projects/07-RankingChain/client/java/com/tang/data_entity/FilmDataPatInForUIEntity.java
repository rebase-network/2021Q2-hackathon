package com.tang.data_entity;

import android.graphics.Bitmap;

import java.io.Serializable;


//正在参评的影视数据
public class FilmDataPatInForUIEntity implements Serializable {   //这里要继承序列这个类才能在Intent及网络传输中把整个类传过去，不然只能传值
    //这里应该还需要一个随机产生的电影Id;
    String FilmId; //电影Id,在创建合约时
    Bitmap FilmPic;//电影图片；    //图片不能作为作为序列串在Activity中进行传输
    String FilmName; //电影名称
    String PartInNum; //参与人数
    String HotNum;  //热度
    String Bonus; //奖金
    String StartTime;//开始时间
    String EndTime;//结束时间
    String SponsorAccountId;//发起人Id,账号地址

    public String getFilmId() {
        return FilmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public void setFilmPic(Bitmap filmPic) {
        FilmPic = filmPic;
    }

    public Bitmap getFilmPic() {
        return FilmPic;
    }

    public void setFilmName(String filmName) {
        FilmName = filmName;
    }

    public String getFilmName() {
        return FilmName;
    }

    public void setPartInNum(String partInNum) {
        PartInNum = partInNum;
    }

    public String getPartInNum() {
        return PartInNum;
    }

    public void setHotNum(String hotNum) {
        HotNum = hotNum;
    }

    public String getHotNum() {
        return HotNum;
    }

    public void setBonus(String bonus) {
        Bonus = bonus;
    }

    public String getBonus() {
        return Bonus;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSponsorAccountId() {
        return SponsorAccountId;
    }

    public void setSponsorAccountId(String sponsorAccountId) {
        SponsorAccountId = sponsorAccountId;
    }
}
