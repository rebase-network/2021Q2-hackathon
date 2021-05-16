package com.tang.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.gyf.barlibrary.ImmersionBar;
import com.tang.C;
import com.tang.R;
import com.tang.adapter.TransactionsAdapter;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.Transaction;
import com.tang.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


import static com.tang.C.EXTRA_ADDRESS;
import static com.tang.C.Key.TRANSACTION;

/**
 * Created by tang
 *
 */


public class PropertyDetailActivity extends ActivityRoot {

//    TransactionsViewModelFactory transactionsViewModelFactory;
//    private TransactionsViewModel viewModel;

    private TransactionsAdapter adapter;

    private String currWallet;
    private String contractAddress;
    private int decimals;
    private String balance;
    private String symbol;


    SwipeRefreshLayout refreshLayout;
    LinearLayout back_button;
    TextView tvTitle;

    LinearLayout transferButton;
    LinearLayout gatheringButton;
    TextView tvAmount;

    List<Transaction> transactionLists;  //这是交易记入


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_activity_property_detail);

        initDatas();
        initViews();
    }




    public void initDatas() {

        Intent intent = getIntent();
        currWallet = intent.getStringExtra(C.EXTRA_ADDRESS);
        balance = intent.getStringExtra(C.EXTRA_BALANCE);
        contractAddress = intent.getStringExtra(C.EXTRA_CONTRACT_ADDRESS);
        decimals = intent.getIntExtra(C.EXTRA_DECIMALS, C.ETHER_DECIMALS);
        symbol = intent.getStringExtra(C.EXTRA_SYMBOL);
        symbol = symbol == null ? C.ETH_SYMBOL : symbol;

        back_button=(LinearLayout)findViewById(R.id.lly_back);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        tvAmount=(TextView)findViewById(R.id.tv_amount);
        transferButton=(LinearLayout)findViewById(R.id.lly_transfer);
        gatheringButton=(LinearLayout)findViewById(R.id.lly_gathering);

        tvTitle.setText(symbol);
        tvAmount.setText(balance);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(PropertyDetailActivity.this, SendActivity.class);

                intent.putExtra(C.EXTRA_BALANCE, balance);
                intent.putExtra(C.EXTRA_ADDRESS, currWallet);
                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
                intent.putExtra(C.EXTRA_SYMBOL, symbol);
                intent.putExtra(C.EXTRA_DECIMALS, decimals);

                startActivity(intent);
            }
        });
        gatheringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(PropertyDetailActivity.this, GatheringQRCodeActivity.class);
//                ETHWallet wallet = WalletDaoUtils.getCurrent();

                intent.putExtra(EXTRA_ADDRESS, Wallet.getInstance().getCurrentAddress());
                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
                intent.putExtra(C.EXTRA_SYMBOL, symbol);
                intent.putExtra(C.EXTRA_DECIMALS, decimals);

                startActivity(intent);
            }
        });



        //下面是涉及到交易记录的相关，暂时先放一放，后面再加上
//        transactionsViewModelFactory = new TransactionsViewModelFactory();
//        viewModel = ViewModelProviders.of(this, transactionsViewModelFactory)
//                .get(TransactionsViewModel.class);
//
//        viewModel.transactions().observe(this, this::onTransactions);
//        viewModel.progress().observe(this, this::onProgress);

    }

    public void initViews(){


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));


        //从代码来看交易记录可以从本地来获取也可以从网页中获取，也就是本地会进行数据保护。
        //历史记入没有实现，多以点击历史记录列表不会触发下面的类。
        adapter = new TransactionsAdapter(R.layout.chain_list_item_transaction, null );
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            Transaction t = transactionLists.get(position);

            Intent intent = new Intent(this, TransactionDetailActivity.class);
            intent.putExtra(TRANSACTION, t);
            startActivity(intent);
        });




//        refreshLayout.setOnRefreshListener(viewModel::fetchTransactions);



    }



    @Override
    protected void onResume() {
        super.onResume();

        LogUtils.d("contractAddress " + contractAddress);

//        if (!TextUtils.isEmpty(contractAddress)) {
//            viewModel.prepare(contractAddress);
//        } else {
//            viewModel.prepare(null);
//        }

    }



    private void onTransactions(List<Transaction> transactions) {
        LogUtils.d("onTransactions", "size: " + transactions.size());
        transactionLists = transactions;
        adapter.addTransactions(transactionLists, currWallet, symbol);
    }



    private void onProgress(boolean shouldShow) {
        if (shouldShow && refreshLayout != null && refreshLayout.isRefreshing()) {
            return;
        }

        if (shouldShow) {
//            if (transactionLists.size() > 0) {
//                refreshLayout.setRefreshing(true);
//            }

        } else {
            refreshLayout.setRefreshing(false);
        }
    }



    @OnClick({R.id.lly_back, R.id.lly_transfer, R.id.lly_gathering})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.lly_back:
                finish();
                break;
            case R.id.lly_transfer:

                intent = new Intent(PropertyDetailActivity.this, SendActivity.class);

                intent.putExtra(C.EXTRA_BALANCE, balance);
                intent.putExtra(C.EXTRA_ADDRESS, currWallet);
                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
                intent.putExtra(C.EXTRA_SYMBOL, symbol);
                intent.putExtra(C.EXTRA_DECIMALS, decimals);

                startActivity(intent);
                break;
            case R.id.lly_gathering:
                intent = new Intent(PropertyDetailActivity.this, GatheringQRCodeActivity.class);
//                ETHWallet wallet = WalletDaoUtils.getCurrent();

                intent.putExtra(EXTRA_ADDRESS, Wallet.getInstance().getCurrentAddress());
                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, contractAddress);
                intent.putExtra(C.EXTRA_SYMBOL, symbol);
                intent.putExtra(C.EXTRA_DECIMALS, decimals);

                startActivity(intent);
                break;
        }
    }
}
