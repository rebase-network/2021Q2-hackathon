package com.tang.data_entity;

import android.graphics.Bitmap;

public class SelectFilmItemEntity {

    private String filmId;//电影Id
    private Bitmap filmPic; //这个估计不需要，太麻烦了
    private String filmName;

    public SelectFilmItemEntity() {
    }

    public SelectFilmItemEntity(String filmId ,Bitmap filmPic, String filmName) {
        this.filmId=filmId;
        this.filmPic = filmPic;
        this.filmName = filmName;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public Bitmap getFilmPic() {
        return filmPic;
    }

    public void setFilmPic(Bitmap filmPic) {
        this.filmPic = filmPic;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
}
