package com.forum.lottery.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenhanfeng.textverify.VerifyHandler;
import com.chenhanfeng.textverify.VerifyUtils;
import com.chenhanfeng.textverify.annotation.Length;
import com.chenhanfeng.textverify.annotation.NotEmpty;
import com.chenhanfeng.textverify.annotation.Order;
import com.chenhanfeng.textverify.annotation.Regex;
import com.chenhanfeng.textverify.annotation.Same;
import com.forum.lottery.R;
import com.forum.lottery.api.CommonService;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.RegisterResult;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.network.RxHttp;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.utils.ImageUtil;

import okhttp3.ResponseBody;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/4/29.
 */

public class RegisterActivity extends BaseActionBarActivity implements View.OnClickListener {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    @Order(0)
    @NotEmpty(message = "请输入用户名", sequence = 0)
    @Length(min = 4, max = 16, message = "用户名长度为4-16", sequence = 1)
    @Regex(regex = "^[A-Za-z0-9]+$", message = "用户名只能为英文字母或数字", sequence = 2)
    private EditText editUsername;
    @Order(1)
    @NotEmpty(message = "请输入密码", sequence = 0)
    @Length(min = 6, max = 12, message = "密码长度为6-12", sequence = 1)
    private EditText editPassword;
    @Order(2)
    @Same(sourceId = R.id.edit_password, message = "密码不一致")
    private EditText editPasswordAgain;
    @Order(3)
    @NotEmpty(message = "请输入验证码", sequence = 0)
    @Regex(regex = "^[a-z0-9]{4}$", message = "验证码格式不正确", sequence = 1)
    private EditText editCode;
    private ImageView imgCode;
    private CheckBox checkRead;
    private VerifyHandler verifyHandler;
    private ProgressDialog dialog;
    public static void startActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        editUsername = findView(R.id.edit_username);
        editPassword = findView(R.id.edit_password);
        editPasswordAgain = findView(R.id.edit_password_again);
        editCode = findView(R.id.edit_code);
        imgCode = findView(R.id.img_code);
        checkRead = findView(R.id.check_read);

        verifyHandler = VerifyUtils.from(this);

        imgCode.setOnClickListener(this);
        findView(R.id.btn_register).setOnClickListener(this);



        //ImageUtil.loadImage(RxHttp.BASE_URL + "validateCode?t=" + System.currentTimeMillis(), imgCode);
        loadCodePic();
    }

    private void loadCodePic(){
        createHttp(CommonService.class)
                .loadCodeImage()
                .map(new Func1<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap call(ResponseBody responseBody) {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap value) {
                        imgCode.setImageBitmap(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.v(TAG, "验证码生成失败");
                    }
                });
    }

    @Override
    protected void initData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍等...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_code:
                //ImageUtil.loadImage(RxHttp.BASE_URL + "validateCode?t=" + System.currentTimeMillis(), imgCode);
                loadCodePic();
                break;
            case R.id.btn_register:
                if (checkData()) {
                    netRegister(editUsername.getText().toString(), editPassword.getText().toString(), editCode.getText().toString());
                }
                break;
        }
    }

    private boolean checkData() {
        if (!verifyHandler.verify()) {
            return false;
        }
        if (checkRead.isChecked()) {
            return true;
        } else {
            toast("请同意《法律申明》");
            return false;
        }
    }

    private void netRegister(String userName, String password, String code){
        dialog.show();
        createHttp(UserService.class)
                .register(userName, password, code)
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
                        }
                        toast(value.getDesc());
                    }

                    @Override
                    public void onError(Throwable error) {
                        dialog.dismiss();
                    }
                });
    }

}