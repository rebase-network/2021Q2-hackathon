package com.tang.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tang.blockchain.Token;


import java.util.ArrayList;
import java.util.List;

//这个类后面可以合并成一个模板类
public class PreferencesTokenListDataSave {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferencesTokenListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public void setDataList(String tag, List<Token> datalist) {
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
    public ArrayList<Token> getDataList(String tag) {
        ArrayList<Token> datalist=new ArrayList<Token>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<Token>>() {
        }.getType());
        return datalist;

    }


}
