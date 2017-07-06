package com.forum.lottery.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/9/3.
 */
public class UserVO implements Parcelable {


    private String id;
    /**
     * 0,1,2   无，企业，银团
     */
    private int type;
    private String account;
    private String password;
    /**
     * 是否认证 0 未认证， 1已认证
     */
    private int isCredit;

    private String logo;
    private String companyName;
    private String token;
    private String sign;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(int isCredit) {
        this.isCredit = isCredit;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.account);
        dest.writeInt(this.isCredit);
        dest.writeString(this.logo);
        dest.writeString(this.companyName);
        dest.writeString(this.token);
        dest.writeString(this.sign);
    }

    public UserVO() {
    }

    protected UserVO(Parcel in) {
        this.id = in.readString();
        this.type = in.readInt();
        this.account = in.readString();
        this.isCredit = in.readInt();
        this.logo = in.readString();
        this.companyName = in.readString();
        this.token = in.readString();
        this.sign = in.readString();
    }

    public static final Creator<UserVO> CREATOR = new Creator<UserVO>() {
        @Override
        public UserVO createFromParcel(Parcel source) {
            return new UserVO(source);
        }

        @Override
        public UserVO[] newArray(int size) {
            return new UserVO[size];
        }
    };

    public UserVO cloneUser(UserVO userVO){
        if(userVO == null){
            userVO = new UserVO();
        }
        userVO.setAccount(getAccount());
        userVO.setPassword(getPassword());
        userVO.setId(getId());
        userVO.setType(getType());
        userVO.setIsCredit(getIsCredit());
        userVO.setToken(getToken());
        userVO.setSign(getSign());
        return userVO;
    }
}
