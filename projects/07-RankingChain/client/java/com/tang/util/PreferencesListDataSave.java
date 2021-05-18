package com.tang.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tang.data_entity.FilmEvaluateDataToSave;

import java.util.ArrayList;
import java.util.List;

public class PreferencesListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferencesListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public void setDataList(String tag, List<FilmEvaluateDataToSave> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public  ArrayList<FilmEvaluateDataToSave> getDataList(String tag) {
        ArrayList<FilmEvaluateDataToSave> datalist=new ArrayList<FilmEvaluateDataToSave>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<FilmEvaluateDataToSave>>() {
        }.getType());
        return datalist;

    }

}
