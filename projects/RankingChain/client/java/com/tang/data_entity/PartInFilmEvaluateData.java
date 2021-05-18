package com.tang.data_entity;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//参与评定弹窗界面数据
public class PartInFilmEvaluateData {
    String filmId;
    Bitmap filmImage;
    String filmName;
    String subjectivityScore;//主观评分。
    String boxOfficeNum;//票房。
    String partInTickNum; //投票数
    String fightSponsor;//争夺发起方
    //可能还需要用户Id


    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public Bitmap getFilmImage() {
        return filmImage;
    }

    public void setFilmImage(Bitmap filmImage) {
        this.filmImage = filmImage;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getSubjectivityScore() {
        return subjectivityScore;
    }

    public void setSubjectivityScore(String subjectivityScore) {
        this.subjectivityScore = subjectivityScore;
    }

    public String getBoxOfficeNum() {
        return boxOfficeNum;
    }

    public void setBoxOfficeNum(String boxOfficeNum) {
        this.boxOfficeNum = boxOfficeNum;
    }

    public String getPartInTickNum() {
        return partInTickNum;
    }

    public void setPartInTickNum(String partInTickNum) {
        this.partInTickNum = partInTickNum;
    }

    public String getFightSponsor() {
        return fightSponsor;
    }

    public void setFightSponsor(String fightSponsor) {
        this.fightSponsor = fightSponsor;
    }
}
