package com.nhnacademy.jdbc.board.good.service;


import com.nhnacademy.jdbc.board.good.domain.Good;

import java.util.Optional;

public interface GoodService {
    int insertGood(int postNo,int userNo);
    int deleteGood(int postNo,int userNo);
    boolean isUserGoodToPost(int postNo, int userNo);

    int getGoodCount(int postNo);
}
