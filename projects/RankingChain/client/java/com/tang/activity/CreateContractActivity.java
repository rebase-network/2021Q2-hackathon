package com.tang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.eva.android.widget.WidgetUtils;
import com.tang.EvaluattingRecycleViewAdapter;
import com.tang.MySpinnerAdapter;
import com.tang.R;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.FilmDataPatInForUIEntity;
import com.tang.data_entity.SelectFilmItemEntity;
import com.tang.util.CreateContractConfirmPopupWindow;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.eva.android.BitmapHelper.drawableToBitmap;

//发起者创建智能合约界面
public class CreateContractActivity extends ActivityRoot implements AdapterView.OnItemSelectedListener{


    Button submitButton; //提交按钮
    String accountIdIntent;//钱包账户Id
    CreateContractConfirmPopupWindow mconfirmPopupWindowCreateContract;//任务计划白板弹窗。


    ImageView filmImageIV;
    Spinner selectFilmNameSP;
    String partInNum;//参与人数，这个数据要统计获得，不是直接界面获得。
    String hotNum;//热度，计算获得。
    EditText startBonuseEV;
    EditText endDeadLine;

    Context mcontext;

    /*电影选择下拉框相关*/
    //判断是否为刚进去时触发onItemSelected的标志
    private boolean spinner_selected = false;
    private BaseAdapter mySPAdadpter = null;
    private ArrayList<SelectFilmItemEntity> filmNameDatas = null;
    private int spinnerPosition;

    //先用最简单的方式更新Iteam数据
//    private ArrayListObservable<FilmDataPatInForUIEntity> staticListData = null;
//
//    private Observer addFilimDataObserver = new Observer(){
//        @Override
//        public void update(Observable observable, Object data)
//        {
//            rosterListAdapter.notifyDataSetChanged();
//        }
//    };
//
//    /** 数据模型变动观察者实现block */
//    private Observer listDatasObserver = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataFromIntent();

        setContentView(R.layout.chain_create_contract_activity);
        //这里要先把上一个Activity中的数据接收一下。

        mcontext=getParent();
        filmNameDatas = new ArrayList<SelectFilmItemEntity>();

        initDate();

        initViews();
    }


    protected void initDataFromIntent() //先从上一个Acitivity中获得相关数据
    {
        //解析从intent中传过来的数据
        ArrayList intentDatas = parseCreatContractIntent(getIntent());
//        accountIdIntent = (String)intentDatas.get(0);
        accountIdIntent= Wallet.getInstance().getCurrentAddress();

        int a=0;

    }


    private void initViews() {

        filmImageIV=(ImageView)findViewById(R.id.film_pic);
        selectFilmNameSP=(Spinner)findViewById(R.id.select_film_name);
        startBonuseEV=(EditText) findViewById(R.id.create_contract_bonus);
        endDeadLine=(EditText)findViewById(R.id.end_cantract_deadtime);


//      mUpdateFilmData=(EvaluatingActivity)mcontext.mRecyclerAdapt;


        //selectFilmNameSP需要进行适配。
        mySPAdadpter=new MySpinnerAdapter<SelectFilmItemEntity>(filmNameDatas,R.layout.chain_spin_select_film_item) {
            @Override
            public void bindView(ViewHolder holder, SelectFilmItemEntity obj) {
//                holder.setImageResource(R.id.img_icon,obj.gethIcon());
                holder.setText(R.id.txt_name, obj.getFilmName());
            }
        };
        selectFilmNameSP.setAdapter(mySPAdadpter);
        selectFilmNameSP.setOnItemSelectedListener(this);



        submitButton=(Button)findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                String[] endStrs=endDeadLine.getText().toString().split("-");
                String endString=endDeadLine.getText().toString().trim();
                if(isDateStringValid(endString)){

                    if(endString.length()==10){//日期格式必须为10位 例：2014-02-24
                        //触发确认弹窗。
                        PopPlanWindow(v);
                    } else {
                        WidgetUtils.showToast(CreateContractActivity.this, "请出入正确的日期格式！", WidgetUtils.ToastType.WARN);
                    }
                } else {
                    WidgetUtils.showToast(CreateContractActivity.this, "请出入正确的日期格式！", WidgetUtils.ToastType.WARN);
                }




              ///  if((endStrs[0].equals()l)&&(endStrs[1]!=null)&&(endStrs[2]!=null)){ //年月日数据不能为空

//                    int endYear=Integer.parseInt(endStrs[0]);
//                    int endMonth=Integer.parseInt(endStrs[1]);
//                    int endDay=Integer.parseInt(endStrs[2]);
//
//
//                    //这里要检验一下输入框中的数据是否正确。
//                if(endDeadLine.getText()!=null){   //直接解析输入框中的数据看是否满足要求
//







//                WidgetUtils.areYouSure(CreateContractActivity.this
//                        , "确认要创建评定合约吗？"
////						, "Discover the new version already exists on the SD card, if not by downloading directly from the local installation?"
//                        , parentActivity.getResources().getString(R.string.general_prompt)
//                        , new Action() //确认时要执行的动作——直接安装该存在的APK文件
//                        {
//                            @Override
//                            public void actionPerformed(Object dialog){
////								FileSystemHelper.viewFile(doawnLoadedFile, parentActivity);
//                                OpenFileUtil.openFile(parentActivity, doawnLoadedFile.getAbsolutePath());
//                            }
//                        }
//                        , new Action() //取消时要执行的动作——忽略已存的apk文件重新下载新版程序
//                        {
//                            @Override
//                            public void actionPerformed(Object dialog)
//                            {
//                                downloadTheFile(newVersionDowloadURL);
//                                showDownWaitDialog();
//                            }
//                        }
//                );


            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.select_film_name:
                if(spinner_selected){

                    spinnerPosition=position; //获得选定的下拉框数据位置

//                    Toast.makeText(this,"您的分段是~：" + parent.getItemAtPosition(position).toString(),
//                            Toast.LENGTH_SHORT).show();
                }else spinner_selected = true;
                break;
//            case R.id.spin_two:
//                if(two_selected){
//                    TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
//                    Toast.makeText(mContext,"您选择的英雄是~：" + txt_name.getText().toString(),
//                            Toast.LENGTH_SHORT).show();
//                }else two_selected = true;
//                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void initDate(){


        //这个数据是需要从数据库中获得的新上映影视名称。目前下拉列表中不需要给各个电影表项提供图片，减少麻烦。
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"金刚大战哥斯拉"));
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"我的姐姐"));
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"无极剑圣：易（Yi）"));
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"德莱厄斯：德莱文（Draven）"));
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"德邦总管：赵信（XinZhao）"));
        filmNameDatas.add(new SelectFilmItemEntity("0001",null,"狂战士：奥拉夫（Olaf）"));

    }

    /*
    * 整个Item的添加流程。从 CreateContractConfirmPopupWindow->CreateContractActivity->
    * EvaluattingRecycleViewAdapter.
    * 且不同Activity中直接采用静态方法调用
    * */

    public void addNewFileData(){ //添加一个新的影评合约数据。
        FilmDataPatInForUIEntity newFileData=new FilmDataPatInForUIEntity();  //创建新的合约电影数据
        newFileData.setFilmPic(drawableToBitmap(filmImageIV.getDrawable()));
        newFileData.setFilmName(filmNameDatas.get(spinnerPosition).getFilmName());
        newFileData.setFilmId(filmNameDatas.get(spinnerPosition).getFilmId()); //这个Id可以是和服务器
        newFileData.setBonus(startBonuseEV.getText().toString());
//        newFileData.setStartTime(StartDeadLine.getText().toString());
        newFileData.setEndTime(endDeadLine.getText().toString());
        newFileData.setSponsorAccountId(accountIdIntent);
        //日期格式没有问题，只是要自己输入


        EvaluattingRecycleViewAdapter.recycleViewInstance.AddfilmDataItem(newFileData);
//        return  newFileData;

    }



    /**
     * 解析intent传过来给团队/世界频道的数据。
     *
     * @param intent
     * @return
     */
    public static ArrayList parseCreatContractIntent(Intent intent)
    {
        ArrayList datas = new ArrayList();
        datas.add(intent.getSerializableExtra("__accountId__"));
        return datas;
    }


    ArrayList<String>infoDatas=new ArrayList<>();
    private void PopPlanWindow(View parent){
        infoDatas.add(accountIdIntent);
        infoDatas.add(startBonuseEV.getText().toString());
        mconfirmPopupWindowCreateContract =new CreateContractConfirmPopupWindow(this,infoDatas,CreateContractActivity.this);
        mconfirmPopupWindowCreateContract.setOutsideTouchable(false);
        mconfirmPopupWindowCreateContract.setFocusable(false);
        mconfirmPopupWindowCreateContract.update();
        mconfirmPopupWindowCreateContract.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }


    //判断日期输入框中输入的日期格式

    /**
     * 判断字符串是否为日期格式
     * @param date
     * @return
     */
    public boolean isDateStringValid(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        // 输入对象不为空
        try {
            sdf.parse(date);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }





}
