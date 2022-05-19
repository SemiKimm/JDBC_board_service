package com.nhnacademy.jdbc.board.user.domain;

public class User {

    private final int userNo;
    private final String userId;
    private final String userPassword;
    private final String userNickname;
    private final int userTypeCode;

    public User(int userNo, String userId, String userPassword, String userNickname, int userTypeCode) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userTypeCode = userTypeCode;
    }

    public int getUserNo() {
        return userNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserNickName() {
        return userNickname;
    }

    public int getUserTypeCode() {
        return userTypeCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "userNo=" + userNo +
                ", userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userTypeCode=" + userTypeCode +
                '}';
    }
}
