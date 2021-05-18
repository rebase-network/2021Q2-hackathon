package com.tang.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tang.MyPartInRecycleViewAdapter;
import com.tang.R;
import com.tang.data_entity.MyEvaluatedFilmDataForUIEntity;

import java.util.ArrayList;
import java.util.List;

public class MyPartInActivity extends ActivityRoot {

    public List<MyEvaluatedFilmDataForUIEntity> myEvaluatedFilmDataForUIEntities =new ArrayList<MyEvaluatedFilmDataForUIEntity>();
    private Context mContext;
    private View view;
    private RecyclerView mFilmRecycleView;
    MyPartInRecycleViewAdapter mRecyclerAdapt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果有数据加载需要先加载数据
        getFilmInfromDB();

        initViews();

    }


    private void initViews() {

        setContentView(R.layout.chain_my_partin_activity);

        initeRecyclerView();


    }


    public void initeRecyclerView( ){
        mFilmRecycleView = findViewById(R.id.My_PartIn_RecycleView);

        //动态网里面添加Item,通过右上角的添加按钮添加Item
        //动态添加表项是从这个类来实现的。
        mFilmRecycleView.setLayoutManager(new GridLayoutManager(MyPartInActivity.this,1));
        mRecyclerAdapt = new MyPartInRecycleViewAdapter(MyPartInActivity.this, myEvaluatedFilmDataForUIEntities);
        //  mRecyclerAdapt.getTeamInfActivity(this);
        mRecyclerAdapt.setRecyclerView(mFilmRecycleView);
        mFilmRecycleView.setAdapter(mRecyclerAdapt);



    }


    public void getFilmInfromDB(){

        //定义一个测试数据

        MyEvaluatedFilmDataForUIEntity myEvaluatedFilmDataForUIEntity =new MyEvaluatedFilmDataForUIEntity();
        myEvaluatedFilmDataForUIEntity.setMyPartInFilmPic(BitmapFactory.decodeResource(getResources(), R.drawable.cat));
        myEvaluatedFilmDataForUIEntity.setMyPartInNum("20000");
        myEvaluatedFilmDataForUIEntity.setMyPartInFilmName("加菲猫");
        myEvaluatedFilmDataForUIEntity.setMyPartInprofit("+5000");
        myEvaluatedFilmDataForUIEntity.setMyPartInIncomRate("+300%");
        myEvaluatedFilmDataForUIEntity.setMyPartInboxOffice("500000000");
        myEvaluatedFilmDataForUIEntity.setMyPartIntreasureGrade("3");

        MyEvaluatedFilmDataForUIEntity myEvaluatedFilmDataForUIEntity1 =new MyEvaluatedFilmDataForUIEntity();
        myEvaluatedFilmDataForUIEntity1.setMyPartInFilmPic(BitmapFactory.decodeResource(getResources(), R.drawable.jtz));
        myEvaluatedFilmDataForUIEntity1.setMyPartInNum("300000");
        myEvaluatedFilmDataForUIEntity1.setMyPartInFilmName("金刚大战哥斯拉");
        myEvaluatedFilmDataForUIEntity1.setMyPartInIncomRate("+1000%");
        myEvaluatedFilmDataForUIEntity1.setMyPartInprofit("+60000");
        myEvaluatedFilmDataForUIEntity1.setMyPartInboxOffice("1200000000");
        myEvaluatedFilmDataForUIEntity1.setMyPartIntreasureGrade("4");

        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity1);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity1);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity1);
        myEvaluatedFilmDataForUIEntities.add(myEvaluatedFilmDataForUIEntity1);

        //实际上这里需要从数据库读取数据！！！！！



    }



}
