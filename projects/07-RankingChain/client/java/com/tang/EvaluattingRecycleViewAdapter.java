package com.tang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tang.activity.EvaluatingActivity;
import com.tang.activity.PartInEvaluatttingFilmActivity;
import com.tang.data_entity.FilmDataPartInFromServerEntity;
import com.tang.data_entity.FilmDataPatInForUIEntity;
import com.tang.data_entity.FilmEvaluateDataToSave;
import com.tang.util.ContractTimeDurationProcess;
import com.tang.util.PreferencesListDataSave;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


//为了效果好看点，让每个电影以卡片的显示方式是展现，而不是图片
public class EvaluattingRecycleViewAdapter extends RecyclerView.Adapter<EvaluattingRecycleViewAdapter.MyViewHolder>{
    static public List<FilmDataPatInForUIEntity> mFlimDatas;
    Context mContext;
    RecyclerView mRecyclerView;

    public static EvaluattingRecycleViewAdapter recycleViewInstance=null;   //这个是最简单的两个Activity进行交互方法，但是也容易造成内存泄漏，这个后面可以优化一下。


    //这里需要起一个定时器，不断检查所有Item对应的时间是否到了触发事件的时候。
    public  final Timer timer=new Timer();
//    public TimerTask timerTask=new TimerTask() {    //点前定时器任务有问题
//        @Override
//        public void run() {
//            //查询电影合约数据，所有电影合约中是否有需要根据时间进行相应操作的情况
//            for(int i=0;i<mFlimDatas.size();i++){  //定时器实际上只需要处理合约结束操作，无需处理开始操作。
//                if((mFlimDatas.get(i)!=null)&&(mFlimDatas.get(i).getEndTime()!=null)){
//                    String[] endStrs=mFlimDatas.get(i).getEndTime().split("-");
//                    if((endStrs[0]!=null)&&(endStrs[1]!=null)&&(endStrs[2]!=null)){ //年月日数据不能为空
//
//                        int endYear=Integer.parseInt(endStrs[0]);
//                        int endMonth=Integer.parseInt(endStrs[1]);
//                        int endDay=Integer.parseInt(endStrs[2]);
//
//                        if(ContractTimeDurationProcess.CheckEndTimeOk(endYear,endMonth,endDay)){
//                            //1.是否在合约中加一个开关，表示合约投票结束
//                            //2.把当前Item的转态也要修改一下。
//
//
////                            //验证一下这个本地保存方法
////                            PreferencesListDataSave preferencesListDataSave=new PreferencesListDataSave(mContext,"text");
////                            ArrayList<FilmEvaluateDataToSave> listString = new ArrayList<FilmEvaluateDataToSave>();
////                            FilmEvaluateDataToSave s = new FilmEvaluateDataToSave("8","10000");
////                            listString.add(s);
////                            listString.add(s);
////                            preferencesListDataSave.setDataList("12", listString); //第一个是key值。
//
//                            ArrayList<FilmEvaluateDataToSave> mEvaluateData=getEvaluateDataToSave("my_evaluate",mFlimDatas.get(i).getFilmId());//获得保存的评定
//                            if(mEvaluateData!=null){ //获得有效数据
//                                //二次提交相应评定
//                                String subjectivityScore= mEvaluateData.get(0).getSubjectivityScore();
//                                String boxOffice=mEvaluateData.get(0).getBoxOffice();
//
//                                //调用合约把该获得的评定提交给合约。
//
//
//
//                                //修改Item状态，表示已经完成评定，但是活动还没有
//
//
//
//                            }else{ //这里需要加上如果没有获得数据时，但又符合评定二次提交时，需要提示用户手动。另一种方法是用服务器保存也是可以的。
//
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    };


    //从本地获得影评数据，并进行二次提交
    private ArrayList<FilmEvaluateDataToSave> getEvaluateDataToSave(String saveFileName, String filmId){  //电影Id,也就是key值
        ArrayList<FilmEvaluateDataToSave> filmEvaluateList=new ArrayList<FilmEvaluateDataToSave>();
        PreferencesListDataSave preferencesListDataSave=new PreferencesListDataSave(mContext,saveFileName);

        filmEvaluateList=preferencesListDataSave.getDataList(filmId);

        if((filmEvaluateList!=null)&&(!filmEvaluateList.get(0).isFinished())){  //应该把评定有效标志加进来。
            return filmEvaluateList;
        }
        return null;
    }





    public EvaluattingRecycleViewAdapter(Context context, List<FilmDataPatInForUIEntity> filmDataPatInFromServerEntities) {
        super();
        mFlimDatas = filmDataPatInFromServerEntities;
        mContext = context;

        recycleViewInstance=this;

//        timer.schedule( timerTask, 0, 60000);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("test", "onCreateViewHolder: test 1");
        return new MyViewHolder(LayoutInflater.from(parent.getContext())   //崩溃
                .inflate(R.layout.chain_film_item_cardview, null, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Log.i("test", "onCreateViewHolder: test 2");

        holder.filmPic.setImageBitmap(mFlimDatas.get(position).getFilmPic());
        holder.partInNum.setText(mFlimDatas.get(position).getPartInNum());
        holder.hotNum.setText(mFlimDatas.get(position).getHotNum());
        holder.filmName.setText(mFlimDatas.get(position).getFilmName());
        holder.startTime.setText(mFlimDatas.get(position).getStartTime());
        holder.endTime.setText(mFlimDatas.get(position).getEndTime());
        holder.startBonus.setText(mFlimDatas.get(position).getBonus());
        holder.sponsorAccountId.setText(mFlimDatas.get(position).getSponsorAccountId());


        /*注意如果想获得对应的viewholder
        在onBindViewHolder中 对viewHolder 使用setTag给对应的viewHolder添加Tag
        *
        * */

        setItemListener(holder,position);


    }
    @Override
    public int getItemCount() {
        Log.i("test", "onCreateViewHolder: test 3");
        return mFlimDatas.size();

    }


    private void setItemListener(final MyViewHolder holder, final int position){

        holder.partInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String partInNum=mFlimDatas.get(position).getPartInNum();
                final String filmName=mFlimDatas.get(position).getFilmName();
                final String hotNum=mFlimDatas.get(position).getHotNum();

                //注意在输入框中设置输入时间格式
                String startTimeText=mFlimDatas.get(position).getEndTime();
                String endTimeText=mFlimDatas.get(position).getEndTime();

//              String txt = etDate.getText().toString();
//              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//              Date date = sdf.parse(startTimeText);
//              LocalDate date= LocalDate.parse(startTimeText);
                String[] startStrs=startTimeText.split("-"); //解析字符串，各个时间数据放到字符数组中
                int startYear=Integer.parseInt(startStrs[0]);
                int startMonth=Integer.parseInt(startStrs[1]);
                int startDay=Integer.parseInt(startStrs[2]);

                String[] endStrs=endTimeText.split("-");
                int endYear=Integer.parseInt(endStrs[0]);
                int endMonth=Integer.parseInt(endStrs[1]);
                int endDay=Integer.parseInt(endStrs[2]);


//                Log.e("test", "testing!!!!!!!!");
//                PreferencesListDataSave preferencesListDataSave=new PreferencesListDataSave(mContext,"text");
//                ArrayList<FilmEvaluateDataToSave> listString = new ArrayList<FilmEvaluateDataToSave>();
//                FilmEvaluateDataToSave s = new FilmEvaluateDataToSave("8","10000");
//                listString.add(s);
//                listString.add(s);
//                preferencesListDataSave.setDataList("12", listString); //第一个是key值。
//
//                if(getEvaluateDataToSave("text","12")!=null){
//                    ArrayList<FilmEvaluateDataToSave> test0=getEvaluateDataToSave("text","12");
//                    String test= getEvaluateDataToSave("text","12").get(0).getSubjectivityScore();
//                    Log.e("test", test);
//
//                }






                //触发参加活动，弹出一个填写对话框。可能还要一些过滤条件
                //先注释一下，便于调试
//                if(ContractTimeDurationProcess.CheckStratTimeOk(startYear,startMonth,startDay)){ //当前时间已经达到合约起始时间

                    Intent i= createPartInEvaluattingFilmActivityIntent((EvaluatingActivity)mContext,mFlimDatas.get(position));
                    mContext.startActivity(i);

//                }else if(ContractTimeDurationProcess.CheckEndTimeOk(endYear,endMonth,endDay)){ //这个条件可能不需要
//
//
//
//                }else{
//                    //弹出一个时间窗口，表示参评时间未到
//                    //可以改为一个Activity，并且
//                    WidgetUtils.showToast(mContext
//                            , "参评时间还未到，请留意时间！", WidgetUtils.ToastType.INFO);
//
//                }




            }
        });


    }




    /**
     * 获取其中的一个item
     * @param position position
     * @return MusicData
     */
    public FilmDataPatInForUIEntity getItem(int position){
        return mFlimDatas.get(position);
    }

//
//    @Override
//    public void onDestory(){
//
//
//
//    }







    public void setRecyclerView(RecyclerView recyclerView){


        mRecyclerView=recyclerView;
    }


    /**
     * 添加Item
     *
     */
    public  void  AddfilmDataItem(FilmDataPatInForUIEntity filmDataPatInForUIEntity){
//        if(mFlimDatas==null){
//            mFlimDatas=new ArrayList<>();
//        }

//        int ItemLastPosition=mRecyclerView.getChildCount(); //获得当前可见的item个数
        if(mRecyclerView.getAdapter()!=null){
            int ItemLastPosition=mRecyclerView.getAdapter().getItemCount(); //获得实际Item个数
            int ItemCounts=mFlimDatas.size();
            //  mTeamPlanDatas.add(position,Itemdata);//指定位置添加
            mFlimDatas.add(ItemLastPosition, filmDataPatInForUIEntity); //指定添加位置

            this.notifyDataSetChanged();
            notifyItemInserted(ItemLastPosition);
            notifyItemRangeChanged(ItemLastPosition,ItemCounts-ItemLastPosition); //这个函数是通知一段变更的范围，添加的话，只有最后一个变动，所以变动范围为原来最后一个位置起始变动一个即可。

        }

        //数据库数据保存操作在Save中执行
    }


    /*
    * 修改对应Item的相关数据及状态
    * */
    public void UpdateFilmDataItem(FilmDataPatInForUIEntity filmDataPatInForUIEntity){
        for(int i=0;i<mFlimDatas.size();i++){
            if((mFlimDatas.get(i).getFilmName().equals(filmDataPatInForUIEntity.getFilmName()))||(mFlimDatas.get(i).getFilmId().equals(filmDataPatInForUIEntity.getFilmId()))){ //找到这个界面对应需要修改数据的Item


                View cardView=mRecyclerView.getChildAt(i); //获得这个位置的CardView

                //直接修改这个布局中按钮
                Button partInButton=cardView.findViewById(R.id.part_in_button);


                partInButton.setText("参与中");
                partInButton.setTextColor(Color.parseColor("#FFFFFF"));
                partInButton.setBackgroundColor(Color.parseColor("#ff45c01a"));

//                this.notifyDataSetChanged();
//                notifyItemInserted(ItemLastPosition);
//                notifyItemRangeChanged(ItemLastPosition,ItemCounts-ItemLastPosition); //这个函数是通知一段变更的范围，添加的话，只有最后一个变动，所以变动范围为原来最后一个位置起始变动一个即可。



            }

        }



    }






    class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView filmPic;
        TextView partInNum;
        TextView hotNum;
        TextView filmName;
        TextView startTime;
        TextView endTime;
        TextView startBonus;
        TextView sponsorAccountId;
        Button partInButton;


         public  String partInNumStr;


        MyViewHolder(View view) {
            super(view);
            filmPic=(ImageView)view.findViewById(R.id.film_pic);
            partInNum=(TextView)view.findViewById(R.id.part_in_num);
            hotNum=(TextView)view.findViewById(R.id.hot_num);
            filmName=(TextView)view.findViewById(R.id.film_name);
            startTime=(TextView) view.findViewById(R.id.start_time);
            endTime=(TextView) view.findViewById(R.id.end_time);
            startBonus=(TextView)view.findViewById(R.id.start_bouns);
            sponsorAccountId=(TextView)view.findViewById(R.id.sponsor_account_id);
            partInButton=(Button) view.findViewById(R.id.part_in_button);

        }


    }




    //参与提交界面
    public static Intent createPartInEvaluattingFilmActivityIntent(Context thisActivity
            , FilmDataPatInForUIEntity filmDataPatInForUIEntity)   //图片只能通过读取得方式获得
    {
        FilmDataPartInFromServerEntity filmDataPartInFromServerEntity =new FilmDataPartInFromServerEntity();
        filmDataPartInFromServerEntity.setFilmId(filmDataPatInForUIEntity.getFilmId());
        filmDataPartInFromServerEntity.setFilmName(filmDataPatInForUIEntity.getFilmName());
        filmDataPartInFromServerEntity.setPartInNum(filmDataPatInForUIEntity.getPartInNum());
        filmDataPartInFromServerEntity.setHotNum(filmDataPatInForUIEntity.getHotNum());
        filmDataPartInFromServerEntity.setBonus(filmDataPatInForUIEntity.getBonus());
        filmDataPartInFromServerEntity.setStartTime(filmDataPatInForUIEntity.getStartTime());
        filmDataPartInFromServerEntity.setEndTime(filmDataPatInForUIEntity.getEndTime());
        filmDataPartInFromServerEntity.setSponsorAccountId(filmDataPatInForUIEntity.getSponsorAccountId());

        Intent intent = new Intent(thisActivity, PartInEvaluatttingFilmActivity.class);
        intent.putExtra("filmDataPartInFromServerEntity", filmDataPartInFromServerEntity);
//        intent.putExtra("__filmName__", filmName);
//        intent.putExtra("__hotNum__", hotNum);
         return intent;
    }


}
