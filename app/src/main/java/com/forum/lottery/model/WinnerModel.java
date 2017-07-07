package com.forum.lottery.model;

/**
 * Created by admin on 2017/5/29.
 */

public class WinnerModel {

    private String gameId;
    private String gameName;
    private float winAmount;
    private String user;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public float getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(float winAmount) {
        this.winAmount = winAmount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
