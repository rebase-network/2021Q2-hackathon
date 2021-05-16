package com.tang.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.EvaluattingRecycleViewAdapter;
import com.tang.R;
import com.tang.data_entity.FilmDataPartInFromServerEntity;
import com.tang.data_entity.FilmDataPatInForUIEntity;
import com.tang.data_entity.PartInFilmEvaluateData;
import com.tang.util.PartInEvaluateConfirmPopupWindow;
import com.tang.util.ToastUtils;

import java.util.ArrayList;

//参与影视评定界面，以界面的方式展现，不用弹窗
public class PartInEvaluatttingFilmActivity extends ActivityRoot {

    Button partInSubmitButton;
    private PartInEvaluateConfirmPopupWindow mpartInPopupWindowCreateContract;
    FilmDataPartInFromServerEntity filmData;
    Bitmap fimPic;//电影对应的图片，


    ImageView filmImageIV;
    TextView partInNumTV;
    TextView hotNumTV;
    TextView filmNameTV;
    EditText subjectivityScoreET;//主观评分。
    EditText boxOfficeNumET;//票房。
    EditText partInTickNumET; //投票数
    EditText fightSponsorET;//争夺发起方
    //账号参数可能也是需要的

    //当前主观分独立的话，那么就不需要分开评定了，直接给出一个总的主观评分即可。
//    EditText filmStoryScoreEV;
//    EditText roleDisplayEV;
//    EditText filmEffectEV;



    FilmDataPartInFromServerEntity filmDataIntent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_partin_evaluatting_activity);

        //这里要先把上一个Activity中的数据接收一下。
        initDataFromIntent();
        initViews();
        initDate();
    }

    protected void initDataFromIntent() //先从上一个Acitivity中获得相关数据
    {


        ArrayList intentDatas = parsePartInEvaluattingIntent(getIntent());
        filmDataIntent = (FilmDataPartInFromServerEntity)intentDatas.get(0);

        filmData=filmDataIntent;

        if(filmData!=null){  //这里实际上id和名字条件应该是或的关系
            if(filmData.getFilmId()!=null ){
                for(int i=0;i<EvaluattingRecycleViewAdapter.mFlimDatas.size();i++){
                    if(filmData.getFilmId().equals(EvaluattingRecycleViewAdapter.mFlimDatas.get(i).getFilmId())){
                        fimPic=EvaluattingRecycleViewAdapter.mFlimDatas.get(i).getFilmPic();

                    }
                }
            }else if(filmData.getFilmName()!=null){
                for(int i=0;i<EvaluattingRecycleViewAdapter.mFlimDatas.size();i++){
                    if(filmData.getFilmName().equals(EvaluattingRecycleViewAdapter.mFlimDatas.get(i).getFilmName())){
                        fimPic=EvaluattingRecycleViewAdapter.mFlimDatas.get(i).getFilmPic();

                    }
                }

            }
        }
    }

    /**
     * 解析intent传过来给该Activity。
     * @param intent
     * @return
     */
    public static ArrayList parsePartInEvaluattingIntent(Intent intent)   //这里可能出问题了。
    {
        ArrayList datas = new ArrayList();
        datas.add(intent.getSerializableExtra("filmDataPartInFromServerEntity"));
        return datas;
    }


    private void initViews() {
        //获得各参评控件放到infoDatas中。
        //注意下面的控件也是需要赋值的。
        filmImageIV=(ImageView) findViewById(R.id.film_pic);
        partInNumTV=(TextView)findViewById(R.id.part_in_num);
        filmNameTV=(TextView)findViewById(R.id.my_film_name);
        hotNumTV=(TextView)findViewById(R.id.hot_num);
        subjectivityScoreET=(EditText) findViewById(R.id.subjectivity_score);//主观评分。
        boxOfficeNumET=(EditText) findViewById(R.id.forecast_box_office);//票房。
        partInTickNumET=(EditText)findViewById(R.id.part_in_tick); //投票数
        fightSponsorET=(EditText)findViewById(R.id.fight_sponsor);//争夺发起方


        //对各控件进行赋值。

        filmImageIV.setImageBitmap(fimPic);
        if(filmData!=null){

            filmNameTV.setText(filmData.getFilmName());  //filmData出现了null问题
            partInNumTV.setText(filmData.getPartInNum());
            hotNumTV.setText(filmData.getHotNum());
        }



        partInSubmitButton=(Button)findViewById(R.id.submit_button);
        partInSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!subjectivityScoreET.getText().toString().equals("")&&!boxOfficeNumET.getText().toString().equals("")
                        &&!partInTickNumET.getText().toString().equals("")){

                    PartInFilmEvaluateData partInFilmEvaluateData=new PartInFilmEvaluateData();
                    partInFilmEvaluateData.setFilmImage(fimPic);
                    partInFilmEvaluateData.setFilmName(filmData.getFilmName());

                    //对输入框中的数据进行检验一下，比如主观评分一定要有，


                    partInFilmEvaluateData.setSubjectivityScore(subjectivityScoreET.getText().toString());
                    partInFilmEvaluateData.setBoxOfficeNum(boxOfficeNumET.getText().toString());
                    partInFilmEvaluateData.setPartInTickNum(partInTickNumET.getText().toString());
                    partInFilmEvaluateData.setFightSponsor(fightSponsorET.getText().toString());

                    //触发确认弹窗。
                    PopPlanWindow(v,partInFilmEvaluateData );

                }
                else{
                    //合约创建成功
                    ToastUtils.showMsg(PartInEvaluatttingFilmActivity.this,"请输入正确的参数！");
                }

            }
        });

    }

    public void initDate(){



    }


    ArrayList<String> infoDatas;
    private void PopPlanWindow(View parent,PartInFilmEvaluateData partInFilmEvaluateData){
        mpartInPopupWindowCreateContract =new PartInEvaluateConfirmPopupWindow(PartInEvaluatttingFilmActivity.this,partInFilmEvaluateData);

        mpartInPopupWindowCreateContract.setOutsideTouchable(false);
        mpartInPopupWindowCreateContract.setFocusable(false);
        mpartInPopupWindowCreateContract.update();
        mpartInPopupWindowCreateContract.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }

    public void upDatefilmItem(FilmDataPatInForUIEntity filmDataPatInForUIEntity){

        EvaluattingRecycleViewAdapter.recycleViewInstance.UpdateFilmDataItem(filmDataPatInForUIEntity);

    }



    //输入文本框监听
//    class EditChangedListener implements TextWatcher {
//
//        // 输入文本之前的状态
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            Log.d("TAG", "beforeTextChanged--------------->");
//        }
//
//        // 输入文字中的状态，count是一次性输入字符数
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.d("TAG", "onTextChanged--------------->");
//        }
//
//        // 输入文字后的状态
//        @Override
//        public void afterTextChanged(Editable s) {
//            Log.d("TAG", "afterTextChanged--------------->");
//            String str = editText.getText().toString();
//            try {
////				if ((editText.getText().toString()) != null)
//                Integer.parseInt(str);
//            } catch (Exception e) {
//                showDialog();
//            }
//        }
//    };
//
//    private void showDialog() {
//        AlertDialog dialog;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("消息").setIcon(android.R.drawable.stat_notify_error);
//        builder.setMessage("你输出的整型数字有误，请改正");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // TODO Auto-generated method stub
//            }
//        });
//        dialog = builder.create();
//        dialog.show();
//    }


}
