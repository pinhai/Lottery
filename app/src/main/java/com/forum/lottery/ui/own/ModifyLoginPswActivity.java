package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chenhanfeng.textverify.VerifyHandler;
import com.chenhanfeng.textverify.VerifyUtils;
import com.chenhanfeng.textverify.annotation.Length;
import com.chenhanfeng.textverify.annotation.NotEmpty;
import com.chenhanfeng.textverify.annotation.Order;
import com.chenhanfeng.textverify.annotation.Same;
import com.forum.lottery.R;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by admin_h on 2017/6/14.
 */

public class ModifyLoginPswActivity extends BaseActionBarActivity {

    @Order(0)
    @NotEmpty(message = "请输入旧密码", sequence = 0)
    @Length(min = 6, max = 12, message = "密码长度为6-12", sequence = 1)
    private EditText et_oldPsw;
    @Order(1)
    @NotEmpty(message = "请输入新密码", sequence = 0)
    @Length(min = 6, max = 12, message = "密码长度为6-12", sequence = 1)
    private EditText et_newPsw;
    @Order(2)
    @NotEmpty(message = "请再次输入新密码", sequence = 0)
    @Length(min = 6, max = 12, message = "密码长度为6-12", sequence = 1)
    @Same(message = "两次新密码不匹配", sourceId = R.id.et_newPsw, sequence = 2)
    private EditText et_newPsw2;
    private Button btn_modify;

    private VerifyHandler verifyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_modify_login_psw);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        setTitle("修改登录密码");

        et_oldPsw = findView(R.id.et_oldPsw);
        et_newPsw = findView(R.id.et_newPsw);
        et_newPsw2 = findView(R.id.et_newPsw2);
        verifyHandler = VerifyUtils.from(this);

        btn_modify = findView(R.id.btn_modify);
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()){
                    submit();
                }
            }

        });
    }

    private boolean checkData() {
        if (!verifyHandler.verify()) {
            return false;
        }
        return true;
    }

    private void submit() {
        String oldPsw = et_oldPsw.getText().toString();
        final String newPsw = et_newPsw.getText().toString();
//        String newPsw2 = et_newPsw2.getText().toString();

        showProgressDialog(false);
        createHttp(UserService.class)
                .modifyLoginPsw(AccountManager.getInstance().getUser().getId(), oldPsw, newPsw)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        dismissProgressDialog();
                        if(value != null && value.getResult().equals("SUCCESS")){
                            UserVO userVO = AccountManager.getInstance().getUser();
                            userVO.setLogo(newPsw);
                            AccountManager.getInstance().saveUser(userVO);
                            toast(R.string.modify_success);
                            finish();
                        }else{
                            toast(value.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        toast(R.string.connection_failed);
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void initData() {

    }
}
