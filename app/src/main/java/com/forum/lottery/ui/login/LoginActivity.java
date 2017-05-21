package com.forum.lottery.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.chenhanfeng.textverify.VerifyHandler;
import com.chenhanfeng.textverify.VerifyUtils;
import com.chenhanfeng.textverify.annotation.NotEmpty;
import com.chenhanfeng.textverify.annotation.Order;
import com.forum.lottery.R;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.RegisterResult;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.utils.AccountManager;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2017/4/25.
 */

public class LoginActivity extends BaseActionBarActivity implements View.OnClickListener{
    public static final int REGISTER_CODE = 100;
    @Order(0)
    @NotEmpty(message = "请输入用户账号")
    private EditText editUsername;
    @Order(1)
    @NotEmpty(message = "请输入密码")
    private EditText editPassword;

    private VerifyHandler verifyHandler;

    private ProgressDialog dialog;
    public static void startActivity(Activity activity, int requestCode){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Fragment fragment, int requestCode){
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        editUsername = findView(R.id.edit_username);
        editPassword = findView(R.id.edit_password);

        verifyHandler = VerifyUtils.from(this);
        findView(R.id.btn_register).setOnClickListener(this);
        findView(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍等...");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                RegisterActivity.startActivity(this, REGISTER_CODE);
                break;
            case R.id.btn_login:
                if(verifyHandler.verify()){
                    netLogin(editUsername.getText().toString(), editPassword.getText().toString());
                }
                break;
        }
    }

    private void netLogin(String userName, String password){
        dialog.show();
        createHttp(UserService.class)
                .login(userName, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<RegisterResult>() {
                    @Override
                    public void onSuccess(RegisterResult value) {
                        dialog.dismiss();
                        if(value.isResult()){
                            UserVO userVO = new UserVO();
                            userVO.setAccount(value.getUserName());
                            userVO.setId(String.valueOf(value.getUserId()));
                            AccountManager.getInstance().saveUser(userVO);
                            setResult(RESULT_OK);
                            self().finish();
                        }else{
                            toast("账号或者密码错误");
                        }
                    }
                    @Override
                    public void onError(Throwable error) {
                        dialog.dismiss();
                    }
                });
    }
}
