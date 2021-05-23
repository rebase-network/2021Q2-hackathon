package com.tang.data_entity;

import java.io.Serializable;
import java.util.ArrayList;

//这个类中的数据为后台发送给所有打开应用的用户。
public class FilmInfoForAll implements Serializable {

    ArrayList<FilmDataPartInFromServerEntity> partInFilmDatas=new ArrayList<>(); //参与评定的电泳数据
    ArrayList<FilmDataEvaluatedFromServerEntity> evaluatedFilmDatas=new ArrayList<>();

    public ArrayList<FilmDataPartInFromServerEntity> getPartInFilmDatas() {
        return partInFilmDatas;
    }

    public void setPartInFilmDatas(ArrayList<FilmDataPartInFromServerEntity> partInFilmDatas) {
        this.partInFilmDatas = partInFilmDatas;
    }

    public ArrayList<FilmDataEvaluatedFromServerEntity> getEvaluatedFilmDatas() {
        return evaluatedFilmDatas;
    }

    public void setEvaluatedFilmDatas(ArrayList<FilmDataEvaluatedFromServerEntity> evaluatedFilmDatas) {
        this.evaluatedFilmDatas = evaluatedFilmDatas;
    }
}
