package com.tang.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.AssetFragment;
import com.tang.MainFragmentAdapter;
import com.tang.PledgeFragment;
import com.tang.R;
import com.tang.blockchain.Token;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.TokenData;
import com.tang.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tang.C.EXTRA_ADDRESS;
import static com.tang.C.Key.WALLET;
import static com.tang.C.SHARE_REQUEST_CODE;

public class ChainAcountActivity extends ActivityRoot{

    //Todo  注意这里先放弃了token添删功能，直接写死了，以后需要的话可以添加。



    //资产列表布局
    AssetFragment assetFragment;  //android4.0一下用FragmentActivity进行兼容，以上可以用fragment
    //质押资产列表布局
    PledgeFragment pledgeFragmrnt;

    //资产列表相关
    private ViewPager mMainViewPager;
    public  static ArrayList<Fragment> mFragments;
    private TabLayout mTabLayout;


 //   List<TokenData> pledgeTokens;

    //各控件
    LinearLayout walletAddressLL;
    ImageView walleLogoCIV;
    RelativeLayout addTokenRL;
    private TextView accountAndWalletTV;

    private TextView walletNameTV;
    private TextView walletAddressTV;
    private TextView tolalAssetValueTV;
    private TextView tolalAssetTV;

    ImageView scannerIV;

    private Wallet currWallet;


    private static final int QRCODE_SCANNER_REQUEST = 1100;
    private static final int CREATE_WALLET_REQUEST = 1101;
    private static final int ADD_NEW_PROPERTY_REQUEST = 1102;
    private static final int WALLET_DETAIL_REQUEST = 1104;
    private static final int START_ACTIVITY_REQUEST=1106;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatas();
        initViews();
        initListeners();
    }

    /**
     * 本方法由父类的onCreate()调用，子类可在此方法中实现自已的UI显示逻辑。
     */
    private void initViews() {
        //开启按back键相当于按Home键的效果，目的是使得在首页这样的地方
        //按back键程序回到回台运行，而不是默认的finish掉进而关闭整个进程
        //，提高用户体验，省的总是要重新登陆
        this.goHomeOnBackPressed = true;
        //设定自定义标题栏（设定此值即意味着开启自定义标题栏的使用，必须要在setContentView前设定）
        //customeTitleBarResId = R.id.main_more_titleBar;
        //养成良好习惯：首先设置主layout，确保后绪的操作中使用到的组件都可以被find到
        setContentView(R.layout.chain_acount);
        //对自定义标题栏中的组件进行设定

        //获得所有控件。
        walletAddressLL=(LinearLayout)findViewById(R.id.lly_wallet_address);
        walleLogoCIV=(ImageView)findViewById(R.id.civ_wallet_logo) ;
//        addTokenRL=(RelativeLayout)findViewById(R.id.rly_add_token);
        scannerIV=(ImageView)findViewById(R.id.ic_scanner);

        accountAndWalletTV=(TextView)findViewById(R.id.account_and_wallet);
        walletNameTV=(TextView)findViewById(R.id.tv_wallet_name);
        walletAddressTV=(TextView)findViewById(R.id.tv_wallet_address);

        mMainViewPager=(ViewPager)findViewById(R.id.vp_main_page);
        mTabLayout=(TabLayout)findViewById(R.id.tab_layout);



        //下面这个可能是实现滑动功能，需要实现一下。目前滑动没有实现，后面再加上。
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
//                showToast(String.valueOf(position));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        mFragments = new ArrayList<>();  //Fragment的List

        assetFragment=new AssetFragment();
        pledgeFragmrnt=new PledgeFragment();
        mFragments.add(assetFragment);
        mFragments.add(pledgeFragmrnt);
//      FragmentManager mfragmentManager=this.getChildFragmentManager() ; //

        //这里的getSupportFragmentManager与尚行客中的不一样，注意一下
        MainFragmentAdapter mainFragmentAdapter =           //ViewPager的适配器
                new MainFragmentAdapter(getSupportFragmentManager()/*getFragmentManager()*/, mFragments);  //这里把getSupportFragmentManager()改了，注意一下
        mMainViewPager.setAdapter(mainFragmentAdapter);        //设置ViewPager的适配器  //这里崩溃了，可能是上面那行引起的
        mTabLayout.setupWithViewPager(mMainViewPager);

        mMainViewPager.setCurrentItem(0);

        //对于两个fragment后面再完善。



        //这里添加每个点击Item触发对应Token的转账及收款界面。这个参考尚行客



        //UI上显示钱包信息
        currWallet = Wallet.getInstance();
        showWallet(currWallet);

    }

//
//    @Override
//    public void onResume() {
//        super.onResume();
//
////        ImmersionBar.with(this)
////                .titleBar(mToolbar, false)
////                .navigationBarColor(R.color.colorPrimary)
////                .init();
////
//        tokensViewModel.prepare();
//
//        // 更改货币单位
//        if (!currency.equals(tokensViewModel.getCurrency())) {
//            currency = tokensViewModel.getCurrency();
//            if (currency.equals("CNY")) {
//                tvTolalAsset.setText(R.string.property_total_assets_cny);
//            } else {
//                tvTolalAsset.setText(R.string.property_total_assets_usd);
//            }
//        }
//
//    }




    //总资产符号可以自己选择就可以了，这个计价功能可能暂时不需要，后面再考虑添加。因为这需要知道比价才行。


    public void initDatas(){


//        Token testToken=new Token();
//        //图片暂时没有，都是统一的图片
////      testToken.setCoinSymbol(BitmapFactory.decodeResource(getResources(), R.drawable.wallet_logo_demo));
//        testToken.setName("Eth");
//        testToken.setBalance("1000");
//        testToken.setBalance("100000");
//        assetTokens.add(testToken);
//        assetTokens.add(testToken);




    }





    public void initListeners(){


        walletAddressLL.setOnClickListener(new View.OnClickListener() {  //注意如果
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChainAcountActivity.this, GatheringQRCodeActivity.class);
                Wallet wallet = currWallet;
                if (wallet == null) {
                    return;
                }

                intent.putExtra(EXTRA_ADDRESS, wallet.getCurrentAddress());
                startActivity(intent);

            }
        });

        walleLogoCIV.setOnClickListener(new View.OnClickListener() { // 跳转钱包详情
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChainAcountActivity.this, WalletDetailActivity.class);
                Wallet wallet = currWallet;
                if (wallet == null) {
                    return;
                }
//                intent.putExtra("walletId", wallet.getId());
                intent.putExtra("walletPwd", wallet.getCurrentPassword());
                intent.putExtra("walletAddress", wallet.getCurrentAddress());
                intent.putExtra("walletName", wallet.getCurrentName());
//                intent.putExtra("walletMnemonic", wallet.getMnemonic());
//                intent.putExtra("walletIsBackup", wallet.getIsBackup());
                startActivityForResult(intent, WALLET_DETAIL_REQUEST); //这个暂时不需要


            }
        });


//        addTokenRL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(ChainAcountActivity.this, AddTokenActivity.class);
//                intent.putExtra(WALLET,  currEthWallet.getAddress());
//                startActivityForResult(intent, ADD_NEW_PROPERTY_REQUEST);
//
//
//            }
//        });

        accountAndWalletTV.setOnClickListener(new View.OnClickListener() { //直接跳转到startActivity中。
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ChainAcountActivity.this,StartActivity.class);
                startActivityForResult(intent,START_ACTIVITY_REQUEST);

            }
        });






        //别少了二维码功能
        scannerIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChainAcountActivity.this, QRCodeScannerActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);

            }
        });

    }


    public void showWallet(Wallet wallet) {
        currWallet = wallet;
        walletNameTV.setText(wallet.getCurrentName());
        walletAddressTV.setText(wallet.getCurrentAddress());
        int a=0;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) { //扫描在另一个界面，扫描结果
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
                // 对扫描结果进行处理
                ToastUtils.showMsg(this,scanResult);

                Intent intent = new Intent(this, SendActivity.class); //扫描成功后直接调用发送界面
                intent.putExtra("scan_result", scanResult );

                startActivity(intent);

            }
        }
        else if (requestCode == WALLET_DETAIL_REQUEST) {
            if (data != null) {
//                mPresenter.loadAllWallets();
//                startActivity(new Intent(this, WalletMangerActivity.class));//这个暂时不需要。
            }
        }else if(requestCode==SHARE_REQUEST_CODE){
            showUser();
        }
    }




    //这里是需要完善实现的，如果需要可以自选token功能（目前暂时不需要这个功能）

    //获得资产列表数据
    /**
     * Assetfragment获取activity的音乐数据
     */
//    public void  upDateAssetTokenDatas(List<Token> tokenDatas){
//        assetFragment.
//    }


    //获得质押资产列表数据
//    public List<TokenData> getPledgeTokenDatas(){
//        return pledgeTokens;
//    }


    private  void showUser(){ //钱包如果切换就需要修改这个用户的钱包信息

        Wallet wallet = Wallet.getInstance();
        showWallet(wallet);

    }



}
