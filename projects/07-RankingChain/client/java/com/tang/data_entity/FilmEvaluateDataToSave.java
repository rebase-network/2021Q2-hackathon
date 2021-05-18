package com.tang.data_entity;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;


//保存对应影评数据，便于二次提交时系统能自动完成
public class FilmEvaluateDataToSave implements Serializable{
    public final static String TAG = FilmEvaluateDataToSave.class.getSimpleName();

    //电影Id作为key值，所以不需要放在这里。并且电影Id可以在创建合约时随机生成。

    private String subjectivityScore; //主观评分
    private String boxOffice;//票房预测
    private boolean isFinished; //该评定数据是否已经使用完，使用这标志后就能不用删除数据了，可以为后面如果要显示保留数据

    public FilmEvaluateDataToSave(String subjectivityScore,String boxOffice){
        this.subjectivityScore=subjectivityScore;
        this.boxOffice=boxOffice;
        isFinished=false;  //初始创建时标志为false.

    }

    public String getSubjectivityScore() {
        return subjectivityScore;
    }

    public void setSubjectivityScore(String subjectivityScore) {
        this.subjectivityScore = subjectivityScore;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }


    public static String toJSON(FilmEvaluateDataToSave li) {
        return new Gson().toJson(li);
    }

    public static FilmEvaluateDataToSave fromJSON(String json) {
        try {
            return new Gson().fromJson(json, FilmEvaluateDataToSave.class);
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return null;
    }


}



