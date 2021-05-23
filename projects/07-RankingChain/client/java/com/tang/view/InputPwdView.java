package com.tang.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.tang.R;

public class InputPwdView extends FrameLayout {

    private EditText password;
    private onConfirmSend onConfirmSender;

    public interface onConfirmSend {
        void sendTransaction(String pwd);
    }

    public InputPwdView(@NonNull Context context, onConfirmSend l) {
        super(context);
        onConfirmSender = l;

        LayoutInflater.from(getContext())
                .inflate(R.layout.chain_input_password_view, this, true);
        password = (EditText)findViewById(R.id.password);

        findViewById(R.id.send_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmSender.sendTransaction(getPassword());
            }
        });
    }



    public String getPassword() {
        return password.getText().toString();
    }

    public void showKeyBoard() {
        password.requestFocus();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showKeyBoard();
    }


}
