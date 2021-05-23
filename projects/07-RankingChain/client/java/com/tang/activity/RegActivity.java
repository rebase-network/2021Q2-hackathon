package com.tang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tang.blockchain.Wallet;
import com.tang.blockchain.WalletBean;
import com.tang.widget.ClearEditText;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tang.R;

public class RegActivity extends AppCompatActivity {

    private static final String TAG = "RegActivity";

    // 默认使用中国区号
    // private static final String DEFAULT_COUNTRY_ID = "42";

//    @ViewInject(R.id.toolbar)
//    private CNiaoToolBar mToolBar;
    @ViewInject(R.id.etxt_pn)
    private ClearEditText mEtxtPn;
    @ViewInject(R.id.etxt_pwd_3)
    private ClearEditText mEtxtPwd_3;
    @ViewInject(R.id.etxt_pwd_4)
    private ClearEditText mEtxtPwd_4;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressBar pgb=(ProgressBar)findViewById(R.id.progressbar);
            switch (msg.what) {
                case 0:
                    Toast.makeText(RegActivity.this, "Wallet created successfully.", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);

                    Intent intent = new Intent(RegActivity.this,StartActivity.class);
                    startActivity(intent);

                    break;
                case 1:
                    Toast.makeText(RegActivity.this, "两次输入的密码不一致，请确认！", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
                case 2:
                    Toast.makeText(RegActivity.this, "该钱包名已存在！", Toast.LENGTH_SHORT).show();
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
                default:
                    pgb.setVisibility(ProgressBar.GONE);
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_reg_activity);
        ViewUtils.inject(this);

//        initToolBar();


     /*   SMSSDK.initSDK(this, ManifestUtil.getMetaDataValue(this, "mob_sms_appKey"),
                ManifestUtil.getMetaDataValue(this,"mob_sms_appSecrect"));
        evenHanlder = new SMSEvenHanlder();
        SMSSDK.registerEventHandler(evenHanlder);

        String[] country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        if (country != null) {

            mTxtCountryCode.setText("+"+country[1]);

            mTxtCountry.setText(country[0]);
        }
        SMSSDK.getSupportedCountries();*/
    }

/*    class SMSEvenHanlder extends EventHandler{


        @Override
        public void afterEvent(final int event, final int result,
                               final Object data) {



            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {

                            onCountryListGot((ArrayList<HashMap<String, Object>>) data);

                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            // 请求验证码后，跳转到验证码填写页面

                            afterVerificationCodeRequested((Boolean) data);

                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                        }
                    } else {

                        // 根据服务器返回的网络错误，给toast提示
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                ToastUtils.show(RegActivity.this, des);
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }

                    }


                }
            });
        }
    }

*/

    @OnClick(R.id.btn_login_1)
    public void go_login(View view){
        Intent intent = new Intent(RegActivity.this,LoginActivity.class);
        startActivity(intent);
        //setResult(RESULT_OK);
        finish();
    }

//    private void initToolBar() {
//        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RegActivity.this.finish();
//            }
//        });
//
//    }

    public void progressBar_Reg(View view) {   //这个函数直接在XML对应的按钮点击触发
        ProgressBar pgb=(ProgressBar)findViewById(R.id.progressbar);
        pgb.setVisibility(ProgressBar.VISIBLE);
        createWallet(view);
    }

    public void createWallet(View view) {
        List<String> names = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name=mEtxtPn.getText().toString();
                    String password1=mEtxtPwd_3.getText().toString();
                    String password2=mEtxtPwd_4.getText().toString();
                    Message msg = mHandler.obtainMessage();
                    if(!password1.equals(password2)){
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        //finish();
                        return;
                    }
                    Wallet wallet=Wallet.getInstance();
                    /*List namelist= null;
                    namelist = wallet.getNameLists();*/
                    File f = new File(getFilesDir()+"/keystore");
                    if (!f.exists()){
                        f.mkdirs();
                    }
                    String filepath = getFilesDir()+"/keystore";

                    try {
                        List<WalletBean>lists=wallet.getWalletBeanList(filepath);
                        for (WalletBean walletBean:lists){
                            String temp=walletBean.getName();
                            String tname=walletBean.getName();
                            String tpassword=walletBean.getName();
                            //names.add(tname);
                            //passwords.add(tpassword);
                            if(temp.equals(name)){
                                msg.what = 2;
                                mHandler.sendMessage(msg);
                                return;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*if(namelist.contains(name)){
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                        //finish();
                        return;
                    }*/

                    wallet.createWallet(name,password1,filepath);
                    msg.what = 0;
                    //msg.obj = "ok";//可以是基本类型，可以是对象，可以是List、map等；
                    mHandler.sendMessage(msg);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        //SMSSDK.unregisterEventHandler(evenHanlder);

    }

}
