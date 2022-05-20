package com.nhnacademy.jdbc.board.good.service.impl;

import com.nhnacademy.jdbc.board.good.domain.Good;
import com.nhnacademy.jdbc.board.good.mapper.GoodMapper;
import com.nhnacademy.jdbc.board.good.service.GoodService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultGoodService implements GoodService {

    private final GoodMapper goodMapper;

    public DefaultGoodService(GoodMapper goodMapper) {
        this.goodMapper = goodMapper;
    }

    @Override
    public Optional<Good> isUserGoodToPost(int postNo, int userNo) {
        return goodMapper.selectGood(postNo,userNo);
    }

    @Override
    public int insertGood(int postNo, int userNo) {
        return goodMapper.insertGood(postNo,userNo);
    }

    @Override
    public int deleteGood(int postNo, int userNo) {
        return goodMapper.deleteGood(postNo,userNo);
    }

    @Override
    public int getGoodCount(int postNo) {
        return goodMapper.getGoodCount(postNo);
    }
}
