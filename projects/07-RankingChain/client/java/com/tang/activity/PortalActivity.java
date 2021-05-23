package com.tang.activity;

import android.app.DownloadManager;
import android.app.TabActivity;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tang.R;
import com.tang.blockchain.TutorialToken;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class PortalActivity extends TabActivity {

    private final String TAG = PortalActivity.class.getSimpleName();

    /** 导航栏TabHost */
    private TabHost tabNavigator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        //**********************************************************
//        //测试区块链
//        //注意高版本andoroid中网络请求基本都需要放到非主线程中
//
//
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//
//                    /*
//                     * 原来模拟器默认把127.0.0.1和localhost当做本身了，在模拟器上可以用10.0.2.2代替127.0.0.1和localhost，
//                     * 另外如果是在局域网环境可以用 192.168.0.x或者192.168.1.x(根据具体配置)连接本机,这样应该就不会报错了。
//                     *
//                     * */
//
//                    String url="http://10.0.2.2:9545";
//                    Web3j web3j= Web3jFactory.build(new HttpService(url));
//
//
//
//                    String user1="0x7c5fdc81096cce9144cc5f542e3a15c1da798c28";
//                    String user1PrivateKey="b331d320d9aaa1761a5718534572dca63b9175e0ff48eed70592e366bb7196e6";
//
//                    Credentials credentials= Credentials.create(user1PrivateKey);
//                    Log.e(TAG,"web3j credentials address:"+credentials.getAddress());
//                    //智能合约部署
//
//                    TutorialToken lqktoken=TutorialToken.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
//                    Log.e(TAG,"web3j lqktoken contract address:"+lqktoken.getContractAddress());
//
//
//                    Request<?,Web3ClientVersion> request= web3j.web3ClientVersion();
//                    Web3ClientVersion response=request.send();
//                    String version=response.getWeb3ClientVersion();
//                    Log.e(TAG,"web3j version:" +version);
//
//                    EthAccounts ethAccounts=web3j.ethAccounts().sendAsync().get();
//                    List<String> accountList=ethAccounts.getAccounts();
//                    for(int i=0;i<accountList.size();i++){
//                        Log.e(TAG,"web3j account:"+i+":" +accountList.get(i));
//                    }
//
//
//
//                    //查询余额
//                    BigInteger userBalances=lqktoken.balanceOf(user1).send();
//                    Log.e(TAG,"user1 balances:"+userBalances.toString());
//
//
//                    //转账
//                    String user2="0x1b3f6cb3ef515757c486150497a5fef44bc3a10d";
//                    TransactionReceipt transactionReceipt=lqktoken.transfer(user2,BigInteger.valueOf(5000)).send();
//                    String status=transactionReceipt.getStatus();
//
//                    //转账结果
//                    userBalances=lqktoken.balanceOf(user1).send();
//                    Log.e(TAG,"user1 balances:"+userBalances.toString());
//                    userBalances=lqktoken.balanceOf(user2).send();
//                    Log.e(TAG,"user2 balances:"+userBalances.toString());
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
////***************************************************************


        //初始化
        init();

    }

    public void init(){

        setContentView(R.layout.main_portal); //把这个Activity的名称统一一下。

        //TAB 的tag常量定义
        String TAB_TAG_ACOUNT = "账户";
        String TAB_TAG_EVALUATTING="参评";
        String TAB_TAG_EVALUATED="评定榜";
        String TAB_TAG_MYPARTIN="我的参评";

        tabNavigator = getTabHost();

        tabNavigator.addTab(getTabSpec(TAB_TAG_ACOUNT, R.drawable.main_portal_tab_bg_moreitems
                , new Intent(this, ChainAcountActivity.class)));
        tabNavigator.addTab(getTabSpec(TAB_TAG_EVALUATTING, R.drawable.main_portal_tab_bg_moreitems //图标换一下
                , new Intent(this, EvaluatingActivity.class)));
        tabNavigator.addTab(getTabSpec(TAB_TAG_EVALUATED, R.drawable.main_portal_tab_bg_moreitems //图标换一下
                , new Intent(this, EvaluatedBoardActivity.class)));
        tabNavigator.addTab(getTabSpec(TAB_TAG_MYPARTIN, R.drawable.main_portal_tab_bg_moreitems //图标换一下
                , new Intent(this, MyPartInActivity.class)));

    }


    /**
     * 返回一个导航栏上用的TabSpec对象.
     *
     * @param tag tabItem的tag（text也是用的这个）
     * @param imgResId 对应的图标资源id
     * @param i 对应的Intent
     * @return
     */
    private TabHost.TabSpec getTabSpec(String tag, int imgResId, Intent i)
    {
        TabHost.TabSpec tabSpec = tabNavigator.newTabSpec(tag).
                setIndicator(getTabItemView(imgResId, tag)).
                setContent(i);
        return tabSpec;
    }


    /**
     * 返回一个TabItem的UI对象.
     *
     * @param imgResId 对应的图标资源id
     * @param text 显示文本
     * @return
     */
    private View getTabItemView(int imgResId, String text)
    {
        View view = LayoutInflater.from(this).inflate(R.layout.main_portal_tab_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.main_portal_tab_item_iconView);
        if (imageView != null)
            imageView.setImageResource(imgResId);

        TextView textView = (TextView) view.findViewById(R.id.main_portal_tab_item_textView);
        if (textView != null)
            textView.setText(text);

        return view;
    }

}
