package com.tang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tang.R;
import com.tang.adapter.AddTokenListAdapter;
import com.tang.blockchain.Token;
import com.tang.blockchain.TokenInfo;
import com.tang.blockchain.Wallet;
import com.tang.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddTokenActivity extends ActivityRoot{

//    TokensViewModelFactory tokensViewModelFactory;
//    private TokensViewModel tokensViewModel;

//    protected AddTokenViewModelFactory addTokenViewModelFactory;
//    private AddTokenViewModel addTokenViewModel;

    private static final int SEARCH_ICO_TOKEN_REQUEST = 1000;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_ico)
    ListView tokenList;
//    @BindView(R.id.common_toolbar)
//    Toolbar commonToolbar;
    @BindView(R.id.lly_back)
    LinearLayout llyBackBtn;


    List<TokenItem> mItems = new ArrayList<TokenItem>();

    private AddTokenListAdapter mAdapter;

    //注意这里的token实际是为钱包服务的，token本身的种类已经写死，如下面几种，这个以后可以加入扩展功能，但本应用毕竟不是钱包，币种可以有限的定死几种。
    final TokenInfo[] tokenTypes=new TokenInfo[]{
            new TokenInfo("", "ETH", "ETH", 18),
            new TokenInfo("0xB8c77482e45F1F44dE1745F52C74426C631bDD52", "", "BNB", 18),
            new TokenInfo("0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48", "USD Coin", "USDC", 6),
            new TokenInfo("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2", "Maker", "MKR", 18),
            new TokenInfo("0xd850942ef8811f2a866692a623011bde52a462c1", "VeChain", "VEN", 18),
            new TokenInfo("0x0000000000085d4780B73119b644AE5ecd22b376", "TrueUSD", "TUSD", 18)
    };






    public static class TokenItem {
        public String walletAddress;
        public final TokenInfo tokenInfo;
        public boolean added;  //这个表示控件的开关，display可以根据这个值来设置
        public int iconId;
        public boolean isDispay;  //是否显示这个token

        public TokenItem(String walletAddress,TokenInfo tokenInfo, boolean added, int id,boolean isDispay) {
            this.walletAddress=walletAddress;
            this.tokenInfo = tokenInfo;
            this.added = added;
            this.iconId = id;
            this.isDispay=isDispay;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_add_new_property_activity);
        initDatas();
        initViews();
//        initListeners();
    }

    public void initDatas() {
        // TODO 写死了几个热门的ERC20 （ 主网地址）


        mItems.add(new TokenItem( Wallet.getInstance().getCurrentAddress(),tokenTypes[0], true, R.drawable.wallet_logo_demo,true)); //以太首先默认显示
        mItems.add(new TokenItem(Wallet.getInstance().getCurrentAddress(),tokenTypes[1], false, R.drawable.wallet_logo_demo,false));
        mItems.add(new TokenItem(Wallet.getInstance().getCurrentAddress(),tokenTypes[2], false, R.drawable.wallet_logo_demo,false));
        mItems.add(new TokenItem(Wallet.getInstance().getCurrentAddress(),tokenTypes[3], false, R.drawable.wallet_logo_demo,false));
        mItems.add(new TokenItem(Wallet.getInstance().getCurrentAddress(),tokenTypes[4], false, R.drawable.wallet_logo_demo,false));
        mItems.add(new TokenItem(Wallet.getInstance().getCurrentAddress(),tokenTypes[5], false, R.drawable.wallet_logo_demo,false));


//        tokensViewModelFactory = new TokensViewModelFactory();
//        tokensViewModel = ViewModelProviders.of(this, tokensViewModelFactory)
//                .get(TokensViewModel.class);
//        tokensViewModel.tokens().observe(this, this::onTokens);
//
//
//        tokensViewModel.prepare();


//        addTokenViewModelFactory = new AddTokenViewModelFactory();
//        addTokenViewModel = ViewModelProviders.of(this, addTokenViewModelFactory)
//                .get(AddTokenViewModel.class);

    }
    private void initViews(){

        mAdapter = new AddTokenListAdapter(this, mItems, R.layout.chain_list_item_add_ico_property);
        tokenList.setAdapter(mAdapter);


    }


//    private void onTokens(Token[] tokens) {
//
//        for (TokenItem item : mItems) {
//            for (Token token: tokens) {
//                if (item.tokenInfo.address.equals(token.getAddress())) {
//                    item.added = true;
//                }
//            }
//        }
//
//        // TODO:  Add missed for tokens
//
//        mAdapter = new AddTokenListAdapter(this, mItems, R.layout.chain_list_item_add_ico_property);
//        tokenList.setAdapter(mAdapter);
//    }

    public void onCheckedChanged(CompoundButton btn, boolean checked){ //这个地方加载时会调用许多次，并且每次修改一个Item就会调用一次
        TokenItem info = (TokenItem) btn.getTag();
        info.added = checked;
        LogUtils.d(info.toString() + ", checked:" + checked);

        if (checked) { //注意未实现

            //把选定的数据保存一下。不要参考钱包文件，自己单独实现。这些token实际是保存在本地数据库中或者文件中
          //  addTokenViewModel.save(info.tokenInfo.address, info.tokenInfo.symbol, info.tokenInfo.decimals);

            //应该从这里进行保存。
            //对Account界面中的列表变量进行设置。


            //然后修改数据库中数据。



        }

    }



    //同一个钱包下，所有Token都是一样的地址。
//    @OnClick({R.id.rl_btn})   //右侧自定义添加代币符号，这个先放一方，主流币才值得质押。
//    public void onClick(View view) {
//        if (view.getId() == R.id.rl_btn) {
//            Intent intent = new Intent(this, AddCustomTokenActivity.class);
//            startActivityForResult(intent, SEARCH_ICO_TOKEN_REQUEST);
//        }
//
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }




}
