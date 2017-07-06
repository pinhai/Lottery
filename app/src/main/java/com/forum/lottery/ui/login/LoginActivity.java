package com.forum.lottery.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chenhanfeng.textverify.VerifyHandler;
import com.chenhanfeng.textverify.VerifyUtils;
import com.chenhanfeng.textverify.annotation.NotEmpty;
import com.chenhanfeng.textverify.annotation.Order;
import com.forum.lottery.R;
import com.forum.lottery.adapter.LoginCacheAdapter;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.RegisterResult;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.event.LoginEvent;
import com.forum.lottery.ui.BaseActionBarActivity;
import com.forum.lottery.utils.AccountManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
    private CheckBox cb_rememberPsw;

    private VerifyHandler verifyHandler;

    private List<UserVO> userPswList;
    private ImageButton img_arrow_down;
    private PopupWindow mPopupWindow;
    private ListView userNameList;
    private LoginCacheAdapter adapter;

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
        initPopupWindow();
    }

    @Override
    protected void initView() {
        editUsername = findView(R.id.edit_username);
        editPassword = findView(R.id.edit_password);
        cb_rememberPsw = findView(R.id.cb_rememberPsw);

        verifyHandler = VerifyUtils.from(this);
        findView(R.id.btn_register).setOnClickListener(this);
        findView(R.id.btn_login).setOnClickListener(this);

        img_arrow_down = findView(R.id.img_arrow_down);
        img_arrow_down.setOnClickListener(this);
        if(userPswList.size() == 0){
            img_arrow_down.setVisibility(View.GONE);
        }else{
            img_arrow_down.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("请稍等...");

        userPswList = AccountManager.getInstance().getUserPsw();
    }

    private void initPopupWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        View popupView = getLayoutInflater().inflate(R.layout.common_list, null);
        userNameList = (ListView) popupView.findViewById(R.id.listview_diabetes);
        initCacheAdapter(userNameList);
        mPopupWindow = new PopupWindow(popupView, width - 48, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        userNameList.setItemsCanFocus(false);
        userNameList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.update();
        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// 设置在软件盘之上
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void initCacheAdapter(ListView v) {
        adapter = new LoginCacheAdapter(this, userPswList, new LoginCacheAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(UserVO userVO){
                editUsername.setText(userVO.getAccount());
                editPassword.setText(userVO.getPassword());
            }
        });
        v.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showPopup() {
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(editUsername, 0, 0);
        }else {
            mPopupWindow.dismiss();
        }
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
            case R.id.img_arrow_down:
                showPopup();
                break;
        }
    }

    private void netLogin(final String userName, final String password){
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
                            userVO.setAccount(userName);
                            userVO.setPassword(password);
                            userVO.setId(String.valueOf(value.getUserId()));
                            AccountManager.getInstance().saveUser(userVO);
                            if(cb_rememberPsw.isChecked()){
                                AccountManager.getInstance().saveUserPsw(userVO);
                            }
                            EventBus.getDefault().post(new LoginEvent());
                            setResult(RESULT_OK);
                            self().finish();
                        }else{
                            toast("账号或者密码错误");
                        }
                    }
                    @Override
                    public void onError(Throwable error) {
                        toast(getString(R.string.connection_failed));
                        dialog.dismiss();
                    }
                });
    }
}
