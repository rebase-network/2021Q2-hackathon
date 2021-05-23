package com.tang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eva.android.widget.AProgressDialog;
import com.tang.R;
import com.tang.blockchain.Wallet;
import com.tang.util.Md5Utils;
import com.tang.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class ModifyPasswordActivity extends ActivityRoot{

    private static final int MODIFY_PWD_RESULT = 2201;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @BindView(R.id.iv_btn)
    TextView ivBtn;
    @BindView(R.id.tv_import_wallet)
    TextView tvImportWallet;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    private long walletId;
    private String walletPwd;
    private String walletAddress;
    private String walletName;
    private boolean walletIsBackup;

    Wallet mWallet;

    AProgressDialog pd;//钱包保存弹窗
    //这里修改的密码，需要把对应的钱包获得，然后把修改的密码保存起来

//    private ModifyWalletInteract  modifyWalletInteract;
//    private InputPwdDialog inputPwdDialog;
    private String walletMnemonic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chain_modify_password_activity);


        initDatas();
        initViews();

//        initListeners();
    }



    public void initDatas() {
        mWallet =Wallet.getInstance();


        Intent intent = getIntent();
        walletPwd = intent.getStringExtra("walletPwd");
        walletAddress = intent.getStringExtra("walletAddress");
        walletName = intent.getStringExtra("walletName");
//        walletIsBackup = intent.getBooleanExtra("walletIsBackup", false);
//        walletMnemonic = intent.getStringExtra("walletMnemonic");
    }


    public void initViews() {

        rlBtn.setVisibility(View.VISIBLE);
        ivBtn.setText("完成");
        tvTitle.setText("更改密码");
        rlBtn.setEnabled(false);
        ivBtn.setTextColor(getResources().getColor(R.color.property_ico_worth_color));




        etOldPwd.addTextChangedListener(watcher);
        etNewPwd.addTextChangedListener(watcher);
        etNewPwdAgain.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String oldPwd = etOldPwd.getText().toString().trim();
            String newPwd = etNewPwd.getText().toString().trim();
            String newPwdAgain = etNewPwdAgain.getText().toString().trim();
            if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newPwdAgain)) {
                rlBtn.setEnabled(false);
                ivBtn.setTextColor(getResources().getColor(R.color.property_ico_worth_color));
            } else {
                rlBtn.setEnabled(true);
                ivBtn.setTextColor(getResources().getColor(R.color.transfer_advanced_setting_help_text_color));
            }
        }
    };

    @OnClick({R.id.tv_import_wallet, R.id.rl_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            //这个暂时不用，后面添加
//            case R.id.tv_import_wallet:
//                startActivity(new Intent(this, ImportWalletActivity.class));
//                finish();
//                break;
            case R.id.rl_btn:
                //这里要获得对应的钱包，并保存这个钱包对应的密码
                String oldPwd = etOldPwd.getText().toString().trim();
                String newPwd = etNewPwd.getText().toString().trim();
                String newPwdAgain = etNewPwdAgain.getText().toString().trim();
                if (verifyPassword(oldPwd, newPwd, newPwdAgain)) {


//                    showDialog(getString(R.string.saving_wallet_tip));//这个是一个旋转加载的进度条
                    pd = new AProgressDialog(this, getString(R.string.saving_wallet_tip));
                    pd.show();

                    mWallet.getCurrnetWalletBean().setPassword(newPwd);//直接对钱包的密码进行设置。
//                    modifyWalletInteract.modifyWalletPwd(walletId, walletName, oldPwd, newPwd).subscribe(this::modifyPwdSuccess);
                }
                break;
        }
    }

    private boolean verifyPassword(String oldPwd, String newPwd, String newPwdAgain) {
        if (!TextUtils.equals(Md5Utils.md5(oldPwd), walletPwd)) {
            ToastUtils.showMsg(this,"原密码错误");
            return false;
        } else if (!TextUtils.equals(newPwd, newPwdAgain)) {
            ToastUtils.showMsg(this,"新密码两次输入不一致");
            return false;
        }
        return true;
    }

    public void modifySuccess() {

    }

    public void modifyPwdSuccess(Wallet wallet) {
//        dismissDialog();
        if(pd!=null){
            pd.dismiss();
            ToastUtils.showMsg(this,"密码保存成功");
            Intent data = new Intent();
            data.putExtra("newPwd", wallet.getCurrentPassword());
            setResult(MODIFY_PWD_RESULT, data);
            finish();

        }
    }

    public void showDerivePrivateKeyDialog(String privateKey) {

    }

    public void showDeriveKeystore(String keystore) {

    }

    public void deleteSuccess(boolean isDelete) {

    }


}
