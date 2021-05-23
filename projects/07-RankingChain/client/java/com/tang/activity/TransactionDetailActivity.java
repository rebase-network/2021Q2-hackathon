package com.tang.activity;

//import android.arch.lifecycle.ViewModelProviders;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import com.tang.R;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.Transaction;
import com.tang.util.BalanceUtils;


import static com.tang.C.Key.TRANSACTION;

/**
 * Created by tang
 *
 */


public class TransactionDetailActivity extends ActivityRoot implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.from)
    TextView tvFrom;

    @BindView(R.id.to)
    TextView tvTo;

    @BindView(R.id.gas_fee)
    TextView tvGasFee;

    @BindView(R.id.txn_hash)
    TextView tvTxHash;

    @BindView(R.id.txn_time)
    TextView tvTxTime;
    @BindView(R.id.block_number)
    TextView tvBlockNumber;

    private Transaction transaction;

//    TransactionDetailViewModelFactory transactionDetailViewModelFactory;
//    private TransactionDetailViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_activity_transaction_detail);

        initDatas();
//        initViews();


    }


    public void initDatas() {

        transaction = getIntent().getParcelableExtra(TRANSACTION);
        if (transaction == null) {
            finish();
            return;
        }

        BigInteger gasFee = new BigInteger(transaction.gasUsed).multiply(new BigInteger(transaction.gasPrice));

        tvFrom.setText(transaction.from);
        tvTo.setText(transaction.to);
        tvGasFee.setText(BalanceUtils.weiToEth(gasFee).toPlainString());
        tvTxHash.setText(transaction.hash);
        tvTxTime.setText(getDate(transaction.timeStamp));
        tvBlockNumber.setText(transaction.blockNumber);

//        transactionDetailViewModelFactory  = new TransactionDetailViewModelFactory();
//
//        viewModel = ViewModelProviders.of(this, transactionDetailViewModelFactory)
//                .get(TransactionDetailViewModel.class);
//        viewModel.defaultNetwork().observe(this, this::onDefaultNetwork);
//        viewModel.defaultWallet().observe(this, this::onDefaultWallet);


        findViewById(R.id.more_detail).setOnClickListener(this);

    }


//    private void onDefaultNetwork(NetworkInfo networkInfo) {
//        findViewById(R.id.more_detail).setVisibility(
//                TextUtils.isEmpty(networkInfo.etherscanUrl) ? View.GONE : View.VISIBLE);
//    }

//    private void onDefaultWallet(Wallet wallet) {
//        boolean isSent = transaction.from.toLowerCase().equals(wallet.getAddress().toLowerCase());
//        String rawValue;
//        String symbol;
//        long decimals = 18;
//        NetworkInfo networkInfo = viewModel.defaultNetwork().getValue();
//        if (transaction.operations == null || transaction.operations.length == 0) {
//            rawValue = transaction.value;
//            symbol = networkInfo == null ? "" : networkInfo.symbol;
//        } else {
//            rawValue = transaction.operations[0].value;
//            decimals = transaction.operations[0].contract.decimals;
//            symbol = transaction.operations[0].contract.symbol;
//        }
//
//        amount.setTextColor(ContextCompat.getColor(this, isSent ? R.color.red : R.color.green));
//        if (rawValue.equals("0")) {
//            rawValue = "0 " + symbol;
//        } else {
//            rawValue = (isSent ? "-" : "+") + getScaledValue(rawValue, decimals) + " " + symbol;
//        }
//        amount.setText(rawValue);
//    }


    private String getDate(long timeStampInSec) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStampInSec * 1000);
        return DateFormat.getLongDateFormat(this).format(cal.getTime());
    }


    private String getScaledValue(String valueStr, long decimals) {
        // Perform decimal conversion
        BigDecimal value = new BigDecimal(valueStr);
        value = value.divide(new BigDecimal(Math.pow(10, decimals)));
        int scale = 3 - value.precision() + value.scale();
        return value.setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }



    @Override
    public void onClick(View v) {
//        viewModel.showMoreDetails(v.getContext(), transaction);
    }

}
