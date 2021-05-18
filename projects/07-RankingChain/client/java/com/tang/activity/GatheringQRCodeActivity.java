package com.tang.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.web3j.utils.Convert;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Single;
import com.tang.R;
import com.tang.util.GlideImageLoader;
import com.tang.util.ToastUtils;
//import pro.upchain.wallet.utils.GlideImageLoader;
//import pro.upchain.wallet.utils.ToastUtils;

import static com.tang.C.EXTRA_ADDRESS;
import static com.tang.C.EXTRA_CONTRACT_ADDRESS;
import static com.tang.C.EXTRA_DECIMALS;
import static org.spongycastle.asn1.x500.style.RFC4519Style.cn;


/**
 * Created by Tiny 熊 @ Upchain.pro
 * WeiXin: xlbxiong
 */

public class GatheringQRCodeActivity extends ActivityRoot {

    ImageView backButton;
    TextView shareButton;
    ImageView ivGatheringQrcode;

//    @BindView(R.id.btn_copy_address)
    Button btnCopyAddress;

//    @BindView(R.id.tv_wallet_address)
    TextView tvWalletAddress;

//    @BindView(R.id.et_gathering_money)
    EditText etGatheringMoney;


    private String walletAddress;
    private String contractAddress;
    private int decimals;
    private String qRStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_gathering_qrcode_activity);
        initData();
        initViews();

//        initListeners();
    }
    private void initViews() {

        backButton=(ImageView)findViewById(R.id.back_button);
        shareButton=(TextView)findViewById(R.id.share_button);
        ivGatheringQrcode=(ImageView)findViewById(R.id.iv_gathering_qrcode);
        tvWalletAddress=(TextView)findViewById(R.id.tv_wallet_address);
        etGatheringMoney=(EditText)findViewById(R.id.et_gathering_money);
        btnCopyAddress=(Button)findViewById(R.id.btn_copy_address);

        tvWalletAddress.setText(walletAddress);


        initAddressQRCode();

        etGatheringMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Single.fromCallable(
                        () -> {
                            String value = etGatheringMoney.getText().toString().trim();

                            if (TextUtils.isEmpty(value)) {
                                return QRCodeEncoder.syncEncodeQRCode(qRStr, BGAQRCodeUtil.dp2px(GatheringQRCodeActivity.this, 270), Color.parseColor("#000000"));
                            } else {
                                String weiValue = Convert.toWei(value, Convert.Unit.ETHER).toString();
                                return QRCodeEncoder.syncEncodeQRCode(qRStr + "&value=" + weiValue, BGAQRCodeUtil.dp2px(GatheringQRCodeActivity.this, 270), Color.parseColor("#000000"));
                            }
                        }
                ).subscribe( bitmap ->  GlideImageLoader.loadBmpImage(ivGatheringQrcode, bitmap, -1) );

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyWalletAddress();
            }
        });

    }

    private void initData(){
        Intent intent = getIntent();
        walletAddress = intent.getStringExtra(EXTRA_ADDRESS);
        contractAddress = intent.getStringExtra(EXTRA_CONTRACT_ADDRESS);
        decimals = intent.getIntExtra(EXTRA_DECIMALS, 18);




    }


    // 参考
    // ethereum:0x6B523CD4FCDF3332BcB3177050e22cF7272b4c3A?contractAddress=0xd03e0c90c088d92f05c0f493312860d9e524049c&decimal=1&value=100000
    private void initAddressQRCode() {

        qRStr = "ethereum:" + walletAddress + "?decimal=" + decimals;
        if (!TextUtils.isEmpty(contractAddress)) {
            qRStr += "&contractAddress=" + contractAddress;
        }

        Single.fromCallable(
                () -> {
                    return QRCodeEncoder.syncEncodeQRCode(qRStr, BGAQRCodeUtil.dp2px(GatheringQRCodeActivity.this, 270), Color.parseColor("#000000"));
                }
        ).subscribe( bitmap ->  GlideImageLoader.loadBmpImage(ivGatheringQrcode, bitmap, -1) );

    }



//    @OnClick({R.id.back_button, R.id.share_button})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.lly_back:
//                finish();
//                break;
//            case R.id.btn_copy_address:
//                copyWalletAddress();
//                break;
//        }
//    }

    private void copyWalletAddress() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm != null) {
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", walletAddress);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
        ToastUtils.showMsg(this,"已复制");
        btnCopyAddress.setText(R.string.gathering_qrcode_copy_success);
    }
}
