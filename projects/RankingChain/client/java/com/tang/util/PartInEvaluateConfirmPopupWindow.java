package com.tang.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tang.AllEvaluateChainApplication;
import com.tang.R;
import com.tang.activity.PartInEvaluatttingFilmActivity;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.FilmDataPatInForUIEntity;
import com.tang.data_entity.FilmEvaluateDataToSave;
import com.tang.data_entity.PartInFilmEvaluateData;

import java.math.BigInteger;
import java.util.ArrayList;

public class PartInEvaluateConfirmPopupWindow extends PopupWindow {

    private Context mContext;
    private View view;

//    Bitmap filmImage;
//    String partInNumTV;
//    TextView hotNumTV;
//    TextView filmNameTV;
    TextView subjectivityScoreTV;//主观评分。
    TextView boxOfficeNumTV;//票房。
    TextView partInTickNumTV; //投票数
    TextView partInMemberAccountTV; //参评者账户。

    TextView fightSponsorTV;//争夺发起方

    PartInFilmEvaluateData mpartInFilmEvaluateData;
    private ImageView BackButton;

    //参与者数据

    final int partInMember_fee=1;


    Button confirmButton;
    Button cancellButton;

    PartInEvaluatttingFilmActivity mPartInEvaluatttingFilmActivity;


    @SuppressLint("InflateParams")
    public PartInEvaluateConfirmPopupWindow(Context context, PartInFilmEvaluateData partInFilmEvaluateData){
        this(context, LayoutInflater.from(context)
                .inflate(R.layout.chain_partin_contract_confirm_popupwindow,null),partInFilmEvaluateData);//这里的后面3个参数无用

    }
    private PartInEvaluateConfirmPopupWindow(Context context, View contextView,PartInFilmEvaluateData partInFilmEvaluateData){
        super(contextView,1500,1800);
        mContext=context;
        mpartInFilmEvaluateData=partInFilmEvaluateData;
        mPartInEvaluatttingFilmActivity=(PartInEvaluatttingFilmActivity)context;
        initRecycleView(contextView);



    }

    @Override
    public void showAtLocation(View parent,int gravity,int x,int y){
        //这里可能要初始化一些相关的数据。
        super.showAtLocation(parent,gravity,x,y);

    }

    private void initRecycleView(View view) {
        subjectivityScoreTV=(TextView)view.findViewById(R.id.subjectivity_score);
        boxOfficeNumTV=(TextView)view.findViewById(R.id.box_office);
        partInTickNumTV=(TextView)view.findViewById(R.id.tick_num);
        partInMemberAccountTV=(TextView)view.findViewById(R.id.sponsor_account_id);


        subjectivityScoreTV.setText(mpartInFilmEvaluateData.getSubjectivityScore());
        boxOfficeNumTV.setText(mpartInFilmEvaluateData.getBoxOfficeNum());
        partInTickNumTV.setText(mpartInFilmEvaluateData.getPartInTickNum());
        partInMemberAccountTV.setText(Wallet.getInstance().getCurrentAddress());
//        partInMemberAccount.setText(mpartInFilmEvaluateData.get);



        confirmButton=(Button)view.findViewById(R.id.confirm_button);
        cancellButton=(Button)view.findViewById(R.id.cancell_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发调用参加智能合约

                if(AllEvaluateChainApplication.getsInstance().getRankingChain_sol()!=null) {
                    AllEvaluateChainApplication.getsInstance().getRankingChain_sol().updateVotingInfo("0x7c5fdc81096cce9144cc5f542e3a15c1da798c28",
                            BigInteger.valueOf(Integer.parseInt(mpartInFilmEvaluateData.getPartInTickNum())),
                            BigInteger.valueOf(Integer.parseInt(mpartInFilmEvaluateData.getSubjectivityScore())),
                            BigInteger.valueOf(Integer.parseInt(mpartInFilmEvaluateData.getBoxOfficeNum())));


                    if (mpartInFilmEvaluateData != null) {
                        //先获得对应影评的数据
                        //把提交的影评和票房数据保存起来，用于后续自动明码提交这些数据。
                        Log.e("test", "save the evaluate in local");
                        PreferencesListDataSave preferencesListDataSave = new PreferencesListDataSave(mContext, "my_evaluate");
                        ArrayList<FilmEvaluateDataToSave> listString = new ArrayList<FilmEvaluateDataToSave>();
                        FilmEvaluateDataToSave filmEvaluateDataToSave = new FilmEvaluateDataToSave(mpartInFilmEvaluateData.getSubjectivityScore(), mpartInFilmEvaluateData.getBoxOfficeNum());
                        listString.add(filmEvaluateDataToSave);
                        preferencesListDataSave.setDataList(mpartInFilmEvaluateData.getFilmName(), listString); //第一个是key值。最好不用名字，而是用电影Id

                    }

                    //把对应的修改的转态Item加入到评定界面。

                    FilmDataPatInForUIEntity filmDataPatInForUIEntity = new FilmDataPatInForUIEntity();
                    //只需要下面这两个值就可以在目的界面找到对应的数据
                    filmDataPatInForUIEntity.setFilmName(mpartInFilmEvaluateData.getFilmName());
                    filmDataPatInForUIEntity.setFilmId(mpartInFilmEvaluateData.getFilmId());
                    mPartInEvaluatttingFilmActivity.upDatefilmItem(filmDataPatInForUIEntity);

                    //参评过的记录是否要放到后台服务器中，还是本地记录就可以了（如果本地数据被清除了就无法显示）。最终决定放入服务器。


                    //关闭确认弹窗
                    closeWindow(PartInEvaluateConfirmPopupWindow.this);
                    ToastUtils.showMsg(mContext,"参评成功！");
                }
                else{

                    //合约创建成功
                    ToastUtils.showMsg(mContext,"参评失败！");
                }
            }
        });

        cancellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow(PartInEvaluateConfirmPopupWindow.this);
            }
        });



    }

    private void AddItemInRacycleView(){



    }


    private void closeWindow(PartInEvaluateConfirmPopupWindow window){
        if(window!=null){
            window.dismiss();
        }
    }



}
