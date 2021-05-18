package com.tang.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tang.EvaluatedRecycleViewAdapter;
import com.tang.R;
import com.tang.data_entity.FilmDataEvaluatedForUIEntity;

import java.util.ArrayList;
import java.util.List;

//历史评定榜
public class EvaluatedBoardActivity extends ActivityRoot {


    public List<FilmDataEvaluatedForUIEntity> mFilmDatas=new ArrayList<FilmDataEvaluatedForUIEntity>();
    private Context mContext;
    private View view;
    private RecyclerView mFilmRecycleView;
    EvaluatedRecycleViewAdapter mRecyclerAdapt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果有数据加载需要先加载数据
        getFilmInfromDB();

        initViews();

    }


    private void initViews() {

        setContentView(R.layout.chain_evaluated_activity);

        initeRecyclerView();


    }


    public void initeRecyclerView( ){
        mFilmRecycleView = findViewById(R.id.Evaluated_Film_RecycleView);

        //动态网里面添加Item,通过右上角的添加按钮添加Item
        //动态添加表项是从这个类来实现的。
//        mContext=getBaseContext();
        mFilmRecycleView.setLayoutManager(new GridLayoutManager(EvaluatedBoardActivity.this,2));
        mRecyclerAdapt = new EvaluatedRecycleViewAdapter(EvaluatedBoardActivity.this,mFilmDatas);
        //  mRecyclerAdapt.getTeamInfActivity(this);
        mRecyclerAdapt.setRecyclerView(mFilmRecycleView);
        mFilmRecycleView.setAdapter(mRecyclerAdapt);



    }


    public void getFilmInfromDB(){

        //定义一个测试数据

//        List<FilmDataPatInForUIEntity> testFilms=new  ArrayList<>();

        FilmDataEvaluatedForUIEntity filmDataEvaluatedForUIEntity =new FilmDataEvaluatedForUIEntity();
        filmDataEvaluatedForUIEntity.setFilmPic(BitmapFactory.decodeResource(getResources(), R.drawable.cat));
        filmDataEvaluatedForUIEntity.setPartInNum("20000");
        filmDataEvaluatedForUIEntity.setFilmName("加菲猫");
        filmDataEvaluatedForUIEntity.setScore("6.5");
        filmDataEvaluatedForUIEntity.setTotalBonus("50000");
        filmDataEvaluatedForUIEntity.setHeiMaScore("1");

        FilmDataEvaluatedForUIEntity filmDataEvaluatedForUIEntity1 =new FilmDataEvaluatedForUIEntity();
        filmDataEvaluatedForUIEntity1.setFilmPic(BitmapFactory.decodeResource(getResources(), R.drawable.jtz));
        filmDataEvaluatedForUIEntity1.setPartInNum("300000");
        filmDataEvaluatedForUIEntity1.setFilmName("金刚大战哥斯拉");
        filmDataEvaluatedForUIEntity1.setScore("8");
        filmDataEvaluatedForUIEntity1.setTotalBonus("50000");
        filmDataEvaluatedForUIEntity1.setHeiMaScore("4");

        mFilmDatas.add(filmDataEvaluatedForUIEntity);
        mFilmDatas.add(filmDataEvaluatedForUIEntity);
        mFilmDatas.add(filmDataEvaluatedForUIEntity1);
        mFilmDatas.add(filmDataEvaluatedForUIEntity1);
        mFilmDatas.add(filmDataEvaluatedForUIEntity);
        mFilmDatas.add(filmDataEvaluatedForUIEntity);
        mFilmDatas.add(filmDataEvaluatedForUIEntity1);
        mFilmDatas.add(filmDataEvaluatedForUIEntity1);

        //实际上这里需要从数据库读取数据！！！！！



    }




}
