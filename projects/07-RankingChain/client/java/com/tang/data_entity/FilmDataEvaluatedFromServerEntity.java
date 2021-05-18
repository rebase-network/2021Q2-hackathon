package com.tang.data_entity;

import android.graphics.Bitmap;

public class FilmDataEvaluatedFromServerEntity {

    String FilmId;
    String FilmName; //电影名称
    String FilmPicFileName;//电影图片文件路径
    String PartInNum; //参与人数
    //    String HotNum;  //热度
//    String DeadLine; //截止时间
    String TotalBonus; //奖金
    String Score;  //影评分
    String HeiMaScore; //黑马等级

    //是否加上一些中奖率相关的信息。
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

    public void setTotalBonus(String totalBonus) {
        TotalBonus = totalBonus;
    }

    public String getTotalBonus() {
        return TotalBonus;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getScore() {
        return Score;
    }

    public void setHeiMaScore(String heiMaScore) {
        HeiMaScore = heiMaScore;
    }

    public String getHeiMaScore() {
        return HeiMaScore;
    }

}
