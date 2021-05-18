package com.tang.data_entity;

import android.graphics.Bitmap;

import java.io.Serializable;

//这个数据结构主要是因为FilmData中bitmap无法转换为序列串，所以需要去除
public class FilmDataPartInFromServerEntity implements Serializable {   //这里要继承序列这个类才能在Intent及网络传输中把整个类传过去，不然只能传值
    String FilmId; //电影Id,在创建合约时,这个电影ID的产生根据标准来的
    String FilmName; //电影名称
    String FilmPicFileName; //对应电影的图片文件路径
    String PartInNum; //参与人数
    String HotNum;  //热度
    String Bonus; //奖金
    String StartTime;//开始时间
    String EndTime;//结束时间
    String SponsorAccountId;//发起时间

    public String getFilmId() {
        return FilmId;
    }

    public void setFilmId(String filmId) {
        FilmId = filmId;
    }

    public void setFilmName(String filmName) {
        FilmName = filmName;
    }

    public String getFilmName() {
        return FilmName;
    }

    public String getFilmPicFileName() {
        return FilmPicFileName;
    }

    public void setFilmPicFileName(String filmPicFileName) {
        FilmPicFileName = filmPicFileName;
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
