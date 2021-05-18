package com.tang.data_entity;

import java.util.ArrayList;

public class MyEvaluatedFilmDataFromServerEntity {
//    String UserId;
    String filmId; //只需电影Id就可以在已经获得的数据中获得相应数据。
    String TreasuerGrade;//矿藏等级 1表示铁矿 2表示 铜矿 3表示银矿 4表示金矿 5表示砖石矿
    String Income;//收益
    String IncomeRate;//收益率

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getTreasuerGrade() {
        return TreasuerGrade;
    }

    public void setTreasuerGrade(String treasuerGrade) {
        TreasuerGrade = treasuerGrade;
    }

    public String getIncome() {
        return Income;
    }

    public void setIncome(String income) {
        Income = income;
    }

    public String getIncomeRate() {
        return IncomeRate;
    }

    public void setIncomeRate(String incomeRate) {
        IncomeRate = incomeRate;
    }
}
