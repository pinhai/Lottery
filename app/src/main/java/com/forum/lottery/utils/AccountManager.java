package com.forum.lottery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.forum.lottery.application.MyApplication;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.event.LoginEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AccountManager {

    private static AccountManager accountManager;
    private SharedPreferences mPreferences;
    private Handler mHandler;
    private UserVO mUserVO;
    private static final String USER_INFO_KEY = "user_info_key";
    private static final String USER_INFO_SAVE_PSW_KEY = "user_info_save_psw_key";
    private List<UserVO> savePswUserVOs;
    private List<OnUserStateChangeListener> onUserChangeListeners;
    private AccountManager(){
        mPreferences = MyApplication.getInstance().getSharedPreferences(AppConfig.FILE_NAME, Context.MODE_PRIVATE);
        mHandler = new Handler(Looper.getMainLooper());
        onUserChangeListeners = new LinkedList<>();
//        savePswUserVOs = new Gson().fromJson(mPreferences.getString(USER_INFO_SAVE_PSW_KEY, ""), List.class);
        savePswUserVOs = LotteryUtils.jsonToArrayList(mPreferences.getString(USER_INFO_SAVE_PSW_KEY, ""), UserVO.class);
        if(savePswUserVOs == null){
            savePswUserVOs = new ArrayList<>();
        }
    }

    public static AccountManager getInstance(){
        if(accountManager == null){
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public void saveUser(UserVO userVO){
        if(userVO == null){
            throw new IllegalAccessError("user对象为空");
        }
        if(isLogin()){
            userVO.cloneUser(getUser());
            updateUserToDisk();
            notifyListener(StateType.ALL);
        }else{
            mUserVO = new UserVO();
            userVO.cloneUser(mUserVO);
            updateUserToDisk();
            notifyListener(StateType.LOGIN);
        }
    }

    public UserVO getUser() {
        if(mUserVO == null){
            if(isLogin()){
                mUserVO = new Gson().fromJson(mPreferences.getString(USER_INFO_KEY, ""), UserVO.class);
            }else{
//                throw new IllegalStateException("当前用户未登录");
                return null;
            }
        }
        return mUserVO;
    }

    /**
     * 是否登录
     * @return
     */
    public boolean isLogin(){
        return mUserVO != null || mPreferences.contains(USER_INFO_KEY);
    }

    /**
     * 登出
     */
    public void logout(){
        mHandler.removeCallbacks(updateUserToDiskRunnable);
        mPreferences.edit().remove(USER_INFO_KEY).apply();
        mUserVO = null;
        notifyListener(StateType.LOGOUT);
        EventBus.getDefault().post(new LoginEvent());
    }

    /**
     * 记住密码
     * @param user
     */
    public void saveUserPsw(UserVO user){
        boolean flag = false;
        for(UserVO item : savePswUserVOs){
            if(item.getId().equals(user.getId())){
                item = user;
                flag = true;
            }
        }
        if(!flag){
            savePswUserVOs.add(user);
        }
        mPreferences.edit().putString(USER_INFO_SAVE_PSW_KEY, new Gson().toJson(savePswUserVOs)).apply();
    }

    /**
     * 删除已经记住密码的用户
     * @param id
     */
    public void deleteUserPsw(String id){
        for(UserVO item : savePswUserVOs){
            if(id.equals(item.getId())){
                savePswUserVOs.remove(item);
            }
        }
        mPreferences.edit().putString(USER_INFO_SAVE_PSW_KEY, new Gson().toJson(savePswUserVOs)).apply();
    }

    public List<UserVO> getUserPsw(){
        return savePswUserVOs;
    }

    public void addListener(OnUserStateChangeListener onUserChangeListener){
        onUserChangeListeners.add(onUserChangeListener);
    }

    public void removeListener(OnUserStateChangeListener onUserChangeListener){
        onUserChangeListeners.remove(onUserChangeListener);
    }

    private void notifyListener(StateType type){
        for (OnUserStateChangeListener onUserStateChangeListener : onUserChangeListeners){
            onUserStateChangeListener.onFieldChange(type, this);
        }
    }

    private void updateUserToDisk(){
        mHandler.removeCallbacks(updateUserToDiskRunnable);
        mHandler.postDelayed(updateUserToDiskRunnable, 100);
    }

    public void onSaveInstanceState(Bundle outState){
        if(mUserVO != null){
            outState.putParcelable("userEntity", mUserVO);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(mUserVO == null && savedInstanceState != null ){
            mUserVO =  savedInstanceState.getParcelable("userEntity");
        }
    }

    private Runnable updateUserToDiskRunnable = new Runnable() {
        @Override
        public void run() {
            mPreferences.edit().putString(USER_INFO_KEY, mUserVO.toString()).apply();

        }
    };

    public enum StateType{
        LOGIN,
        LOGOUT,
        U_NAME,
        HEADER,
        ALL
    }

    public interface OnUserStateChangeListener{
        void onFieldChange(StateType type, AccountManager accountManager);
    }
}
