package com.nhnacademy.jdbc.board.good.domain;

import lombok.Data;

public class Good {
    int postNo;
    int userNo;

    public Good(int postNo, int userNo) {
        this.postNo = postNo;
        this.userNo = userNo;
    }

    public int getPostNo() {
        return postNo;
    }

    public int getUserNo() {
        return userNo;
    }

    @Override
    public String toString() {
        return "Good{" +
                "postNo=" + postNo +
                ", userNo=" + userNo +
                '}';
    }
}
