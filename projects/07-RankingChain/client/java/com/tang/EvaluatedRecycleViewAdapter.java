package com.tang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.data_entity.FilmDataEvaluatedForUIEntity;

import java.util.List;

public class EvaluatedRecycleViewAdapter extends RecyclerView.Adapter<EvaluatedRecycleViewAdapter.EvaluatedViewHolder>{

    List<FilmDataEvaluatedForUIEntity> mFlimDatas;
    Context mContext;
    RecyclerView mRecyclerView;

    public EvaluatedRecycleViewAdapter(Context context, List<FilmDataEvaluatedForUIEntity>filmDatas) {
        super();
        mFlimDatas = filmDatas;
        mContext = context;

    }

    @Override
    public EvaluatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("test", "onCreateViewHolder: test 1");
        return new EvaluatedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chain_film_done_item_card, null, false));
    }




    @Override
    public void onBindViewHolder(EvaluatedViewHolder holder, int position) {
        Log.i("test", "onCreateViewHolder: test 2");

        holder.filmPicDone.setImageBitmap(mFlimDatas.get(position).getFilmPic());
        holder.partInNumDone.setText(mFlimDatas.get(position).getPartInNum());
        holder.TotalBonus.setText(mFlimDatas.get(position).getTotalBonus());
        holder.filmNameDone.setText(mFlimDatas.get(position).getFilmName());
        holder.FilmScore.setText(mFlimDatas.get(position).getScore());
        holder.HeiMaScore.setText(mFlimDatas.get(position).getHeiMaScore());


    }
    @Override
    public int getItemCount() {
        Log.i("test", "onCreateViewHolder: test 3");
        return mFlimDatas.size();

    }


    public void setRecyclerView(RecyclerView recyclerView){

        mRecyclerView=recyclerView;
    }


    class EvaluatedViewHolder extends RecyclerView.ViewHolder {


        ImageView filmPicDone;
        TextView partInNumDone;
        TextView TotalBonus;
        TextView filmNameDone;
        TextView FilmScore;
        TextView HeiMaScore;


        EvaluatedViewHolder(View view) {
            super(view);
            filmPicDone=(ImageView)view.findViewById(R.id.film_pic_done);
            partInNumDone=(TextView)view.findViewById(R.id.part_in_num_done);
            filmNameDone=(TextView)view.findViewById(R.id.film_name_done);
            TotalBonus=(TextView)view.findViewById(R.id.total_bouns);
            FilmScore=(TextView)view.findViewById(R.id.film_score);
            HeiMaScore=(TextView)view.findViewById(R.id.heima_score);

        }


    }

}
