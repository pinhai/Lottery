package com.forum.lottery.model;

import java.io.Serializable;


/**
 * 收支流水
 * @author weijunlong
 *
 */
public class TradeStreamVo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6001658021714512123L;

	/**
	 * 
	 */
	private int id;
	/**
	 * '收支单号'
	 */
	private String paymentNo;
	/**
	 * '收支情况：0、支出  1、收入
	 */
	private int payIn;
	/**
	 * 交易类型: 0、充值   1、提款  2、投注  3、奖金派送 
	 */
	private int tradeType;
	
	/**
	 * 交易名称
	 */
	private String tradeName;
	/**
	 * 交易金额
	 */
	private double tradeMoney;
	/**
	 * 备注
	 */
	private String tradeTip;
	/**
	 * 支付类型：0、微信支付 1、支付宝支付  2在线入款
	 */
	private int payWay;
	
	private String payUrl;
	/**
	 * 交易状态：0、未支付 1、待确认 2、已存入 3、提款申请 4、提款申请审批成功 5、提款申请审批不通过
	 */
	private String statusCode;
	private Long userId;
	private String createDate;
	private String updateTime; //更新交易记录的时间
	private String updateByUser;//存款记录更新人
	private String bankNo;
	private String remark;
	
	private String beginTime;
	private String endTime;
	private String username;
	private boolean isAdmin;
	
	private String bindBankName;
	private String charge;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public int getPayIn() {
		return payIn;
	}
	public void setPayIn(int payIn) {
		this.payIn = payIn;
	}
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public double getTradeMoney() {
		return tradeMoney;
	}
	public void setTradeMoney(double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}
	public String getTradeTip() {
		return tradeTip;
	}
	public void setTradeTip(String tradeTip) {
		this.tradeTip = tradeTip;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public int getPayWay() {
		return payWay;
	}
	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateByUser() {
		return updateByUser;
	}
	public void setUpdateByUser(String updateByUser) {
		this.updateByUser = updateByUser;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getBindBankName() {
		return bindBankName;
	}
	public void setBindBankName(String bindBankName) {
		this.bindBankName = bindBankName;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	
}
