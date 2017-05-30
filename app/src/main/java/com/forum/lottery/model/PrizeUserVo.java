package com.forum.lottery.model;

import java.io.Serializable;

/**
 * 获奖记录VO
 *
 */
public class PrizeUserVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585385064010891802L;

	/**
	 * 彩种ID
	 */
	private Long gameId;
	
	/**
	 * 彩种名称
	 */
	private String gameName;
	
	/**
	 * 会员
	 */
	private String user;
	
	/**
	 * 赢得奖金
	 */
	private Double winAmount;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}


	
}
