package com.tang.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tang.R;
import com.tang.adapter.StartAdapter;
import com.tang.blockchain.Wallet;
import com.tang.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

//在这这之前可以加上一个flash页面，增强用户体验。还有账号流程需要补上，并且钱包也需要修改为可以选择的控件。总体来讲这一块还是需要优化一下。
public class StartActivity extends Activity{
    private List<String> names = new ArrayList<>();
    private List<String> passwords = new ArrayList<>();
    private List<String> filepaths = new ArrayList<>();
    private StartAdapter startAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_wallet_activity);

        //本本应用中没有密码记入功能，所以需要每次都重新登录一下
        //起始时会在这个界面。从Login界面返回时会切换到这个界面。但是在操作上还是有bug,如果log界面没有创建或者导入钱包就切换到start界面就无法操作了。
        //并且这个界面如果一切OK，就会切换到MainActivity.

        //就
        initData();

        if (names.size() == 0){
            Intent intent = new Intent();
            intent.setClass(this,RegActivity.class);  //钱包创建界面，也是钱包注册界面
            startActivity(intent);
        }
        else { //表示有钱包

            ListView listView = findViewById(R.id.listview);
            startAdapter = new StartAdapter(this, R.layout.chain_wallet_item, names);

            listView.setAdapter(startAdapter);

            View footerView  = getLayoutInflater().inflate(R.layout.chain_start_footer_btn,null);
            Button btn= footerView.findViewById(R.id.btn_import);  //导入钱包按钮
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this,RegActivity.class);
                    startActivity(intent);
                }
            });
            listView.addFooterView(footerView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //通过view获取其内部的组件，进而进行操作
                    final String choice = names.get(i);
                    final String pass = passwords.get(i);
                    final String fi = filepaths.get(i);

                    //大多数情况下，position和id相同，并且都从0开始
                    //String showText = "点击第" + i + " wallet:  " + choice + "，ID为：" + l;
                    //Toast.makeText(Start.this, showText, Toast.LENGTH_LONG).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    final EditText et = new EditText(StartActivity.this);
                    builder.setTitle("验证").setMessage("请输入 " + choice + " 的密码").
                            setView(et).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String inputPass = et.getText().toString();
                                    if (inputPass.equals(pass)) {

                                        ToastUtils.showMsg(StartActivity.this, "验证成功");


                                        startUp(choice,pass,fi);
                                    } else {
                                        ToastUtils.showMsg(StartActivity.this, "验证失败，请重试");
                                    }

                                }
                            }).
                            setNegativeButton("取消", null);
                    builder.create().show();

                }
            });
        }
    }

    public void initData(){

        Wallet wallet = Wallet.getInstance();
        String filepath = getFilesDir()+"/keystore";
        try {
            wallet.getWalletBeanList(filepath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        names = wallet.getNames();
        passwords =wallet.getPasswords();
        filepaths = wallet.getFilepaths();

    }


    public void startUp(String name,String pass,String file)
    {


        Wallet wallet = Wallet.getInstance();
        wallet.useWallet(name,pass,file);//这里非常慢，需要从链上获得钱包信息。

        Intent intent = new Intent(this,PortalActivity.class);  //进入主界面
        startActivity(intent);
    }
}
