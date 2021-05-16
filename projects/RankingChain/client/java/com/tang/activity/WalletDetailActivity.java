package com.tang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.R;
import com.tang.blockchain.Wallet;
import com.tang.util.ToastUtils;
import com.tang.view.InputPwdDialog;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class WalletDetailActivity extends ActivityRoot{

    private static final int WALLET_DETAIL_RESULT = 2201;
    private static final int MODIFY_PASSWORD_REQUEST = 1102;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.iv_btn)
    TextView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.civ_wallet)
    CircleImageView civWallet;
    @BindView(R.id.tv_eth_balance)
    TextView tvEthBalance;
    @BindView(R.id.lly_wallet_property)
    LinearLayout llyWalletProperty;
    @BindView(R.id.tv_wallet_address)
    TextView tvWalletAddress;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.rl_modify_pwd)
    RelativeLayout rlModifyPwd;
//    @BindView(R.id.rl_derive_private_key)
//    RelativeLayout rlDerivePrivateKey;
//    @BindView(R.id.rl_derive_keystore)
//    RelativeLayout rlDeriveKeystore;
//    @BindView(R.id.btn_delete_wallet)
//    TextView btnDeleteWallet;
//    @BindView(R.id.btn_mnemonic_backup)
    TextView btnMnemonicBackup;
    private long walletId;
    private String walletPwd;
    private String walletAddress;
    private String walletName;
    private boolean walletIsBackup;
    private InputPwdDialog inputPwdDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatas();
        initViews();

//        initListeners();
    }
    private void initViews() {
        setContentView(R.layout.chain_wallet_detail_activity);

        tvTitle.setText(walletName);
        etWalletName.setText(walletName);
        tvWalletAddress.setText(walletAddress);





    }

    private void initDatas(){

        inputPwdDialog = new InputPwdDialog(this);
        Intent intent = getIntent();
//        walletId = intent.getLongExtra("walletId", -1);
        walletPwd = intent.getStringExtra("walletPwd");
        walletAddress = intent.getStringExtra("walletAddress");
        walletName = intent.getStringExtra("walletName");
        //walletIsBackup = intent.getBooleanExtra("walletIsBackup", false);
    }

    public void modifySuccess(boolean s) {

    }
    public void modifyPwdSuccess(Wallet wallet) {

    }

    @OnClick({R.id.rl_btn, R.id.rl_modify_pwd, })
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.rl_btn:
//                String name = etWalletName.getText().toString().trim();
//                if (!TextUtils.equals(this.walletName, name)) {
//                    modifyWalletInteract.modifyWalletName(walletId, name).subscribe(this::modifySuccess);
//                }
//                showDialog(getString(R.string.saving_wallet_tip));
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        //execute the task
//                        dismissDialog();
//                        ToastUtils.showMsg(this,R.string.wallet_detail_save_success);
//                        setResult(WALLET_DETAIL_RESULT, new Intent());
//                        TKeybord.closeKeybord(etWalletName);
//                        finish();
//                    }
//                }, 2000);
//
//                break;
            case R.id.rl_modify_pwd:// 修改密码
                Intent intent = new Intent(this, ModifyPasswordActivity.class);
                intent.putExtra("walletPwd", walletPwd);
                intent.putExtra("walletAddress", walletAddress);
                intent.putExtra("walletName", walletName);
//                intent.putExtra("walletMnemonic", walletMnemonic);
//                intent.putExtra("walletIsBackup", walletIsBackup);
                startActivityForResult(intent, MODIFY_PASSWORD_REQUEST);
                break;
//            case R.id.rl_derive_private_key:// 导出明文私钥
//                inputPwdDialog.show();
//                inputPwdDialog.setDeleteAlertVisibility(false);
//                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
//                    @Override
//                    public void onCancel() {
//                        inputPwdDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirm(String pwd) {
//                        inputPwdDialog.dismiss();
//                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
//                            showDialog(getString(R.string.deriving_wallet_tip));
//                            modifyWalletInteract.deriveWalletPrivateKey(walletId, pwd).subscribe(WalletDetailActivity.this::showDerivePrivateKeyDialog);
//
//                        } else {
//                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
//                        }
//                    }
//                });
//                break;
//            case R.id.rl_derive_keystore:// 导出明文keystore
//                inputPwdDialog.show();
//                inputPwdDialog.setDeleteAlertVisibility(false);
//                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
//                    @Override
//                    public void onCancel() {
//                        inputPwdDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirm(String pwd) {
//                        inputPwdDialog.dismiss();
//                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
//                            showDialog(getString(R.string.deriving_wallet_tip));
//                            modifyWalletInteract.deriveWalletKeystore(walletId, pwd).subscribe(WalletDetailActivity.this::showDeriveKeystore);
//                        } else {
//                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
//                        }
//                    }
//                });
//                break;
//            case R.id.btn_mnemonic_backup:
//                inputPwdDialog.show();
//                inputPwdDialog.setDeleteAlertVisibility(false);
//                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
//                    @Override
//                    public void onCancel() {
//                        inputPwdDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirm(String pwd) {
//                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
//                            Intent intent = new Intent(WalletDetailActivity.this, MnemonicBackupActivity.class);
//                            intent.putExtra("walletId", walletId);
//                            intent.putExtra("walletMnemonic", walletMnemonic);
//                            startActivity(intent);
//                        } else {
//                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
//                        }
//                        inputPwdDialog.dismiss();
//                    }
//                });
//                break;
//
//            case R.id.btn_delete_wallet:
//                inputPwdDialog.show();
//                inputPwdDialog.setDeleteAlertVisibility(true);
//                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
//                    @Override
//                    public void onCancel() {
//                        inputPwdDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onConfirm(String pwd) {
//                        if (TextUtils.equals(walletPwd, Md5Utils.md5(pwd))) {
//                            showDialog(getString(R.string.deleting_wallet_tip));
//                            modifyWalletInteract.deleteWallet(walletId).subscribe(WalletDetailActivity.this::deleteSuccess);
//                        } else {
//                            ToastUtils.showToast(R.string.wallet_detail_wrong_pwd);
//                            inputPwdDialog.dismiss();
//                        }
//                    }
//                });
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_PASSWORD_REQUEST) {
            if (data != null) {
                walletPwd = data.getStringExtra("newPwd");
            }
        }
    }



}
