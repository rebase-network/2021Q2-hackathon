package com.tang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.data_entity.MyEvaluatedFilmDataForUIEntity;

import java.util.List;

public class MyPartInRecycleViewAdapter extends RecyclerView.Adapter<MyPartInRecycleViewAdapter.MyPartInViewHolder>{

    List<MyEvaluatedFilmDataForUIEntity> myEvaluatedFilmDataForUIEntities;
    Context mContext;
    RecyclerView mRecyclerView;

    public MyPartInRecycleViewAdapter(Context context, List<MyEvaluatedFilmDataForUIEntity> myEvaluatedFilmDataForUIEntities) {
        super();
        this.myEvaluatedFilmDataForUIEntities = myEvaluatedFilmDataForUIEntities;
        mContext = context;

    }

    @Override
    public MyPartInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("test", "onCreateViewHolder: test 1");
        return new MyPartInViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chain_film_mypartin_item_card, null, false));
    }


    @Override
    public void onBindViewHolder(MyPartInViewHolder holder, int position) {
        Log.i("test", "onCreateViewHolder: test 2");

        holder.MyPartInFilmPic.setImageBitmap(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInFilmPic());
        holder.MyPartInFilmName.setText(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInFilmName());
        holder.MyPartInNum.setText(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInNum());
        holder.MyPartInprofit.setText(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInprofit());
        holder.MyPartInIncomRate.setText(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInIncomRate());
        holder.MyPartInboxOffice.setText(myEvaluatedFilmDataForUIEntities.get(position).getMyPartInboxOffice());
        int gradeint=Integer.parseInt(myEvaluatedFilmDataForUIEntities.get(position).getMyPartIntreasureGrade());
        switch (gradeint){
            case 1:
                holder.MyPartIntreasureGrade.setText("铁矿");
                break;
            case 2:
                holder.MyPartIntreasureGrade.setText("铜矿");
                break;
            case 3:
                holder.MyPartIntreasureGrade.setText("银矿");
                break;
            case 4:
                holder.MyPartIntreasureGrade.setText("金矿");
                break;
            case 5:
                holder.MyPartIntreasureGrade.setText("砖石矿");
                break;

        }
    }

    @Override
    public int getItemCount() {
        Log.i("test", "onCreateViewHolder: test 3");
        return myEvaluatedFilmDataForUIEntities.size();

    }


    public void setRecyclerView(RecyclerView recyclerView){

        mRecyclerView=recyclerView;
    }




    class MyPartInViewHolder extends RecyclerView.ViewHolder {

        ImageView MyPartInFilmPic;
        TextView MyPartInFilmName;
        TextView MyPartInNum;
        TextView MyPartInprofit;
        TextView MyPartInboxOffice;
        TextView MyPartInIncomRate;
        TextView MyPartIntreasureGrade;

        MyPartInViewHolder(View view) {
            super(view);
            MyPartInFilmPic = (ImageView) view.findViewById(R.id.my_film_pic);
            MyPartInFilmName = (TextView) view.findViewById(R.id.my_film_name);
            MyPartInNum = (TextView) view.findViewById(R.id.my_part_in_num);
            MyPartInprofit = (TextView) view.findViewById(R.id.my_film_profit);
            MyPartInIncomRate=(TextView) view.findViewById(R.id.my_incom_rate);
            MyPartInboxOffice = (TextView) view.findViewById(R.id.my_box_office);
            MyPartIntreasureGrade = (TextView) view.findViewById(R.id.my_treasure);

        }

    }

}
