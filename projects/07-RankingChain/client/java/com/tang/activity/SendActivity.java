package com.tang.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.tang.C;
import com.tang.R;
import com.tang.data_entity.Address;
import com.tang.util.BalanceUtils;
import com.tang.util.ToastUtils;
import com.tang.view.ConfirmTransactionView;
import com.tang.view.InputPwdView;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;


//这个还有完善
public class SendActivity extends ActivityRoot {

//    ConfirmationViewModelFactory confirmationViewModelFactory;
//    ConfirmationViewModel viewModel;


    TextView tvTitle;


    LinearLayout ivBtn;
//    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;

//    @BindView(R.id.et_transfer_address)
    EditText etTransferAddress;

//    @BindView(R.id.send_amount)
    EditText amountText;

    LinearLayout llyContacts;

    SeekBar seekbar;
    TextView tvGasCost;
    TextView tvGasPrice;
    LinearLayout llyGas;
    EditText etHexData;
    LinearLayout llyAdvanceParam;


    Switch advancedSwitch;
    EditText customGasPrice;
    EditText customGasLimit;

    TextView nextButton;


    private String walletAddr;
    private String contractAddress;
    private int decimals;
    private String balance;
    private String symbol;

    private String netCost;
    private BigInteger gasPrice;
    private BigInteger gasLimit;


    private boolean sendingTokens = false;

    private Dialog dialog;

    private static final int QRCODE_SCANNER_REQUEST = 1100;

    private static final double miner_min = 5 ;
    private static final double miner_max = 55;

    private String scanResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_transfer_activity);
        initDatas();
        initViews();

//        initListeners();
    }

    private void initViews() {
        advancedSwitch=(Switch)findViewById(R.id.advanced_switch);
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        customGasPrice=(EditText)findViewById(R.id.custom_gas_price);
        customGasLimit=(EditText)findViewById(R.id.custom_gas_limit);

        ivBtn=(LinearLayout)findViewById(R.id.lly_back);
        rlBtn=(LinearLayout)findViewById(R.id.rl_btn);
        etTransferAddress=(EditText)findViewById(R.id.et_transfer_address);
        amountText=(EditText)findViewById(R.id.send_amount);
        llyContacts=(LinearLayout)findViewById(R.id.lly_contacts);
        tvGasCost=(TextView)findViewById(R.id.tv_gas_cost);
        tvGasPrice=(TextView)findViewById(R.id.gas_price);
        llyGas=(LinearLayout)findViewById(R.id.lly_gas);
        etHexData=(EditText)findViewById(R.id.et_hex_data);
        llyAdvanceParam=(LinearLayout)findViewById(R.id.lly_advance_param);
        nextButton=(TextView)findViewById(R.id.btn_next);

        //这个先不用
//        advancedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    llyAdvanceParam.setVisibility(View.VISIBLE);
//                    llyGas.setVisibility(View.GONE);
//
//                    customGasPrice.setText(Convert.fromWei(new BigDecimal(gasPrice), Convert.Unit.GWEI).toString());
//                    customGasLimit.setText(gasLimit.toString());
//
//                } else {
//                    llyAdvanceParam.setVisibility(View.GONE);
//                    llyGas.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });


        final DecimalFormat gasformater = new DecimalFormat();
        //保留几位小数
        gasformater.setMaximumFractionDigits(2);
        //模式  四舍五入
        gasformater.setRoundingMode(RoundingMode.CEILING);


        final String etherUnit = getString(R.string.transfer_ether_unit);


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                double p = progress / 100f;
                double d = (miner_max - miner_min) * p + miner_min;

                gasPrice = BalanceUtils.gweiToWei(BigDecimal.valueOf(d));
                tvGasPrice.setText(gasformater.format(d) + " " + C.GWEI_UNIT);

                updateNetworkFee();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar.setProgress(10);
        try {
            netCost = BalanceUtils.weiToEth(gasPrice.multiply(gasLimit), 4) + etherUnit;
        } catch (Exception e) {
        }

        customGasPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    return;
                }
                gasPrice = BalanceUtils.gweiToWei(new BigDecimal(s.toString()));

                try {
                    netCost = BalanceUtils.weiToEth(gasPrice.multiply(gasLimit),  4) + etherUnit;
                    tvGasCost.setText(String.valueOf(netCost ));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        customGasLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                gasLimit = new BigInteger(s.toString());

                updateNetworkFee();
            }
        });


        rlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this, QRCodeScannerActivity.class);
                startActivityForResult(intent, QRCODE_SCANNER_REQUEST);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toAddr = etTransferAddress.getText().toString().trim();
                String amount = amountText.getText().toString().trim();

                if (verifyInfo(toAddr, amount)) {


                    ConfirmTransactionView confirmView = new ConfirmTransactionView(SendActivity.this, SendActivity.this::onClick);
                    confirmView.fillInfo(walletAddr, toAddr, " - " + amount + " " +  symbol, netCost, gasPrice, gasLimit);

                    dialog = new BottomSheetDialog(SendActivity.this, R.style.BottomSheetDialog);
                    dialog.setContentView(confirmView);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
            }
        });





    }

    private void initDatas() {
        Intent intent = getIntent();
        walletAddr = intent.getStringExtra(C.EXTRA_ADDRESS);

        contractAddress = intent.getStringExtra(C.EXTRA_CONTRACT_ADDRESS);

        decimals = intent.getIntExtra(C.EXTRA_DECIMALS, C.ETHER_DECIMALS);
        symbol = intent.getStringExtra(C.EXTRA_SYMBOL);
        symbol = symbol == null ? C.ETH_SYMBOL : symbol;

        tvTitle=(TextView)findViewById(R.id.tv_title);
        tvTitle.setText(symbol + "转账");




        //这里可以实时观察加以变化，如onTransaction中当交易成功时会触发这里面的交易成功弹窗提示

//        confirmationViewModelFactory = new ConfirmationViewModelFactory();
//        viewModel = ViewModelProviders.of(this, confirmationViewModelFactory)
//                .get(ConfirmationViewModel.class);
//
//        viewModel.sendTransaction().observe(this, this::onTransaction);
//        viewModel.gasSettings().observe(this, this::onGasSettings);
//        viewModel.progress().observe(this, this::onProgress);
//        viewModel.error().observe(this, this::onError);

        // 首页直接扫描进入
        //本发送界面也有扫描功能。
        scanResult = intent.getStringExtra("scan_result");
        if (!TextUtils.isEmpty(scanResult)) {
            parseScanResult(scanResult);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        viewModel.prepare(this, sendingTokens? ConfirmationType.ETH: ConfirmationType.ERC20);
//    }


    private void updateNetworkFee() {

        try {
            netCost = BalanceUtils.weiToEth(gasPrice.multiply(gasLimit),  4) + " " + C.ETH_SYMBOL;
            tvGasCost.setText(String.valueOf(netCost ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void onGasSettings(GasSettings gasSettings) {
//        gasPrice = gasSettings.gasPrice;
//        gasLimit = gasSettings.gasLimit;
//
//    }
//
    private boolean verifyInfo(String address, String amount) {

        try {
            new Address(address);   //直接实现一个地址类就可以判断是否为地址
        } catch (Exception e) {
            ToastUtils.showMsg(this,"地址编写不正确");
            return false;
        }

        try {
            String wei = BalanceUtils.EthToWei(amount);
            return wei != null;
        } catch (Exception e) {
            ToastUtils.showMsg(this,"金额填写不正确");

            return false;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.confirm_button:   //可以把这个按钮放到confirm_transcation_view。智能合约也可以用这个。
                // send
                dialog.hide();

                InputPwdView pwdView = new InputPwdView(this, pwd -> {  //弹出一个密码底部弹窗
                    if (sendingTokens) {

                        //这里实现Token的交易，直接调用Token交易方法。
//                        viewModel.createTokenTransfer(pwd,    //实现ERC20交易创建及发送
//                                etTransferAddress.getText().toString().trim(),
//                                contractAddress,
//                                BalanceUtils.tokenToWei(new BigDecimal(amountText.getText().toString().trim()), decimals).toBigInteger(),
//                                gasPrice,
//                                gasLimit
//                        );
                    } else {

                        //创建以太交易
//                        viewModel.createTransaction(pwd, etTransferAddress.getText().toString().trim(),
//                                Convert.toWei(amountText.getText().toString().trim(), Convert.Unit.ETHER).toBigInteger(),
//                                gasPrice,
//                                gasLimit );
                    }
                });

                dialog = new BottomSheetDialog(this);
                dialog.setContentView(pwdView);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                break;
        }
    }


//    private void onProgress(boolean shouldShowProgress) {
//        hideDialog();
//        if (shouldShowProgress) {
//            dialog = new AlertDialog.Builder(this)
//                    .setTitle(R.string.title_dialog_sending)
//                    .setView(new ProgressBar(this))
//                    .setCancelable(false)
//                    .create();
//            dialog.show();
//        }
//    }

//    private void onError(ErrorEnvelope error) {
//        hideDialog();
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.error_transaction_failed)
//                .setMessage(error.message)
//                .setPositiveButton(R.string.button_ok, (dialog1, id) -> {
//                    // Do nothing
//                })
//                .create();
//        dialog.show();
//    }

//    private void onTransaction(String hash) {
//        hideDialog();
//        dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.transaction_succeeded)
//                .setMessage(hash)
//                .setPositiveButton(R.string.button_ok, (dialog1, id) -> {
//                    finish();
//                })
//                .setNeutralButton(R.string.copy, (dialog1, id) -> {
//                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    ClipData clip = ClipData.newPlainText("transaction hash", hash);
//                    clipboard.setPrimaryClip(clip);
//                    finish();
//                })
//                .create();
//        dialog.show();
//    }

    private void fillAddress(String addr) {
        try {
            new Address(addr);
            etTransferAddress.setText(addr);
        } catch (Exception e) {
            ToastUtils.showMsg(this,"地址填写不正确");
        }
    }

    private void parseScanResult(String result) {
        if (result.contains(":") && result.contains("?")) {  // 符合协议格式
            String[] urlParts = result.split(":");
            if (urlParts[0].equals("ethereum")) {
                urlParts =  urlParts[1].split("\\?");

                fillAddress(urlParts[0]);

                // ?contractAddress=0xdxx & decimal=1 & value=100000
//                 String[] params = urlParts[1].split("&");
//                for (String param : params) {
//                    String[] keyValue = param.split("=");
//                }

            }


        } else {  // 无格式， 只有一个地址
            fillAddress(result);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRCODE_SCANNER_REQUEST) {
            if (data != null) {
                String scanResult = data.getStringExtra("scan_result");
                // 对扫描结果进行处理
                parseScanResult(scanResult);
//                ToastUtils.showLongToast(scanResult);
            }
        }
    }


}
