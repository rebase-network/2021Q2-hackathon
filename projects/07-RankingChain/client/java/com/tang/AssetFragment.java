package com.tang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tang.activity.AddTokenActivity;
import com.tang.activity.ChainAcountActivity;
import com.tang.blockchain.ContractMethod;
import com.tang.blockchain.Token;
import com.tang.blockchain.TokenInfo;
import com.tang.blockchain.Wallet;
import com.tang.blockchain.config.Web3jUtil;
import com.tang.data_entity.TokenData;
import com.tang.util.LogUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class AssetFragment extends Fragment {

    private static final String TAG = "AssetFragment";
    public RecyclerView mRvAssetInfo;
//    List<Token> mAssetTokens;

    private static boolean shouldRefresh = false;   //是否需要刷新
    private View assetFragmentView;      //根布局
    private Context mContext;

    public AssetTokenInfoAdapter mAdapter;   //adapter


    List<AssetTokenItem> mItems = new ArrayList<>();

    //注意这里的token实际是为钱包服务的，token本身的种类已经写死，如下面几种，这个以后可以加入扩展功能，但本应用毕竟不是钱包，币种可以有限的定死几种。
    final TokenInfo[] tokenTypes=new TokenInfo[]{
            new TokenInfo("", "ETH", "ETH", 18),
            new TokenInfo("111", "AEC", "AEC", 18),
            new TokenInfo("0xB8c77482e45F1F44dE1745F52C74426C631bDD52", "BNB", "BNB", 18),
            new TokenInfo("0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "USD Coin", "USDC", 6),
            new TokenInfo("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", "Maker", "MKR", 18),
            new TokenInfo("0xd850942ef8811f2a866692a623011bde52a462c1", "VeChain", "VEN", 18),
            new TokenInfo("0x0000000000085d4780B73119b644AE5ecd22b376", "TrueUSD", "TUSD", 18)
            //这些地址都是已经在主链上部署的合约地址。所以只要获得这些已知的地址就能调用这些部署的合约，包括token

            //如果是自己的智能合约，由于已经在应用中已经部署了，直接使用对应的合约类即可。无需合约地址。
    };
    public  class AssetTokenItem {
         final TokenInfo tokenInfo;
         String balance;
         boolean added;  //这个暂时也用不上
         int iconId;

        public AssetTokenItem(TokenInfo tokenInfo, boolean added, int id) {
            this.tokenInfo = tokenInfo;
            this.added = added;
            this.iconId = id;

        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public TokenInfo getTokenInfo() {
            return tokenInfo;
        }


        public boolean isAdded() {
            return added;
        }

        public void setAdded(boolean added) {
            this.added = added;
        }

        public int getIconId() {
            return iconId;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }



    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//1
        super.onCreate(savedInstanceState);
        mContext = getActivity();


        Log.e("AssetFragment", "onReceive: " );
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {//2
        super.onCreateView(inflater, container, savedInstanceState);
        if (assetFragmentView == null) {
            assetFragmentView = inflater.inflate(R.layout.chain_asset_fragment, container, false);
            mRvAssetInfo = assetFragmentView.findViewById(R.id.rv_asset_info);

        }

        initeData();


        //初始的时候不要刷新数据
        refreshAssetInfo();   //??????????????主要问题是数据没有刷新的问题，把顺序优化一下。
        return assetFragmentView;
    }



    //应该可以不需要下面通过消息来执行的方法。
      Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:

                    if((Boolean)msg.obj) { //已获得获得对应Token的balance。
                        //最好加一个正在加载控件，以提高用户体验。
                        showTokenInfo(); //改变主线程UI时需要放到消息等异步方式实现

                    }
                    break;

                default:
                    break;
            }
        }
    };





    public void initeData(){
//        mAssetTokens=new ArrayList<>();
        //从本地数据库中获得对应钱包的token。assetFrement中会从这里获得数据。
      //  mAssetTokens= Wallet.getInstance().getTokenListByWalletAddress(Wallet.getInstance().getCurrentAddress());

        //Item数据直接定死就可以
        mItems.add(new AssetTokenItem(tokenTypes[0], true, R.drawable.wallet_logo_demo)); //以太首先默认显示
        mItems.add(new AssetTokenItem(tokenTypes[1], false, R.drawable.wallet_logo_demo));
        mItems.add(new AssetTokenItem(tokenTypes[2], false, R.drawable.wallet_logo_demo));
        mItems.add(new AssetTokenItem(tokenTypes[3], false, R.drawable.wallet_logo_demo));
        mItems.add(new AssetTokenItem(tokenTypes[4], false, R.drawable.wallet_logo_demo));
        mItems.add(new AssetTokenItem(tokenTypes[5], false, R.drawable.wallet_logo_demo));
        mItems.add(new AssetTokenItem(tokenTypes[6], false, R.drawable.wallet_logo_demo));

        //这里加载各个token的余额


        ru();



    }

    public void ru(){

//        mDialog = new SpotsDialog(this,"loading....");
//        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run(){
                ContractMethod contractMethod = ContractMethod.getInstance();
                Boolean valid = true;
//                BigInteger balance =  Web3jUtil.etherToWei(contractMethod.getETHBalance());

//                int len = mItems.size();
//                Token[] result = new Token[len];
                for (int i = 0; i < mItems.size(); i++) {
                    String balance = null;
                    try {
                        if (mItems.get(i).getTokenInfo().address.isEmpty()) {  //地址为空，表示是以太币
                            balance = contractMethod.getETHBalance().toString();//这里从获得余额
                        } else if(mItems.get(i).getTokenInfo().address.equals("111")){ //本应用的Token

                            //这个账号是需要根据当前使用的钱包来获得的。注意这段代码只是用来测试的，后面需要与下面合在一起
                            String test_user1="0x7c5fdc81096cce9144cc5f542e3a15c1da798c28";

                            BigInteger balanceIn=AllEvaluateChainApplication.getsInstance().getLqktoken().balanceOf(test_user1).send();
                            balance=balanceIn.toString(); //把获得的余额转换一下


//                            String userBalances=AllEvaluateChainApplication.getsInstance().getLqktoken().balanceOf(test_user1).send().toString();
//                            Log.e(TAG,"userBalances:"+userBalances);
                        }else{


                            if(Wallet.getInstance().getCurrentAddress()!=null){
                                balance = contractMethod.getBalance(Wallet.getInstance().getCurrentAddress(), mItems.get(i).getTokenInfo()).toString();  //智能合约调用
                            }
                            else{

                                Log.e("AssetFragment","Wallet CurrentAddress is null");
                            }

                        }
                    } catch (Exception e1) {
                        Log.d("TOKEN", "Err" +  e1.getMessage());
                        /* Quietly */
                    }

                    LogUtils.d("balance:" + balance);
                    if (balance == null || balance.compareTo(BigDecimal.ZERO.toString()) == 0) {
                        mItems.get(i).setBalance("0");//没有值就直接设置为0
                    } else {
                        BigDecimal decimalDivisor = new BigDecimal(Math.pow(10, mItems.get(i).getTokenInfo().decimals));
                        //不需要以太转换
//                        BigDecimal balanceBigDe=BigDecimal.valueOf(Long.parseLong(balance));
//                        BigDecimal ethBalance = balanceBigDe.divide(decimalDivisor);

                        BigDecimal EBalance=BigDecimal.valueOf(Long.parseLong(balance));
                        if (mItems.get(i).getTokenInfo().decimals > 4) {
                            mItems.get(i).setBalance(EBalance.setScale(4, RoundingMode.CEILING).toPlainString());
//                            result[i] = new Token(items[i], ethBalance.setScale(4, RoundingMode.CEILING).toPlainString());
                        } else {
                            mItems.get(i).setBalance(EBalance.setScale(mItems.get(i).getTokenInfo().decimals, RoundingMode.CEILING).toPlainString());
//                            result[i] = new Token(items[i], ethBalance.setScale(items[i].decimals, RoundingMode.CEILING).toPlainString());
                        }
                    }
                }

                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = valid;
                handler.sendMessage(msg);

            }
        }).start();
    }

    private void showTokenInfo() {
        // Log.i("test", "showMusicInfo: " + mAllMusicDatas.size());
        mRvAssetInfo.setLayoutManager(new LinearLayoutManager( getContext()));
        mAdapter = new AssetTokenInfoAdapter(mContext, mItems); //这里应该是异步加载表项,所以上面线程加载后没有更新到这里。
        mAdapter.SetFreagment(this);//用于播放音乐的列表显示。
        mRvAssetInfo.setAdapter(mAdapter);




//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){  //点击钱包中不同的币种时创建具体的对应币种Activyt
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                Intent intent = new Intent(getActivity(), PropertyDetailActivity.class);
//                Token token = tokenItems.get(position);
//
//                intent.putExtra(C.EXTRA_BALANCE, token.balance);
//                intent.putExtra(C.EXTRA_ADDRESS, currEthWallet.getAddress());
//                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, token.tokenInfo.address);
//                intent.putExtra(C.EXTRA_SYMBOL, token.tokenInfo.symbol);
//                intent.putExtra(C.EXTRA_DECIMALS, token.tokenInfo.decimals);//小数
//
//                startActivity(intent);
//            }
//        }); //后面加上点击事件

    }





    //这里是需要完善实现的，如果需要可以自选token功能（目前暂时不需要这个功能）
    //刷新数据,主要是addtoken界面对token数据进行的修改。
    public void refreshAssetInfo() {


//        Log.i(TAG, "refreshAllMusic: 刷新列表AllMusic");
//        if (mAssetDatas == null){
//            mAssetDatas = new ArrayList<>();
//        }
//        // if(mAllMusicDatas.size()!=MusicBaseActivity.mMusicDatas.size()){//只有mAllMusicDatas的数据与从数据库或者sd卡中读取的数据mMusicDatas数据大小不一样时才重新加载数据
//        mAssetDatas.clear();
//        if (mContext != null) {    //context会等于Null
//
//
//            //mAllMusicDatas.addAll(((TeamChattingActivity)mContext).getMusicDatas());//这个改为从数据库中添加
//            mAssetDatas.addAll(((ChainAcountActivity)mContext).getAsstTokenDatas());
//            showTokenInfo();
//
//
//
//        }
//        //}
    }



    //注意这个地方至少有两个地方需要调用。一个是addtoken界面，一个是钱包切换界面
    public void updateAssetTokens(Token token){  //更新是根据单个Toen数据来实现的。其实也可以在addtoken界面添加一个保存按钮，一次性添加所有更新数据
//        //先更新数据库数据
//
//
//        //再更新assetToken中的数据。
//        for(int i=0;i<mAssetTokens.size();i++){
//            if((mAssetTokens.get(i).getWalletAddrges().equals(token.getWalletAddrges()))&&(mAssetTokens.get(i).getName().equals(token.getName()))){
//                mAssetTokens.get(i).setIsDisply(token.getIsDisply());
//                return;
//            }
//
//        }
//        mAssetTokens.add(token);
//
//        //通知adapter数据已经发生改变。让adapter重新加载



    }










}
