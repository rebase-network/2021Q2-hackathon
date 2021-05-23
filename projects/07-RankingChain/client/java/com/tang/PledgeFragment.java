package com.tang;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

public class PledgeFragment extends Fragment{

    private static final String TAG = "PledgeFragment";
    private List<PledgetItemData> mAllPledgetDatas;
    public RecyclerView mRvPledgetInfo;


    private View pledgeFragmentView;      //根布局
    private Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//1
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        Log.e("PledgeFragment", "onReceive: " );

    }





    public class PledgetItemData{



    }

}
