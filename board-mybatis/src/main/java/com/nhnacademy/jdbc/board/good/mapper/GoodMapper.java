package com.nhnacademy.jdbc.board.good.mapper;

import com.nhnacademy.jdbc.board.good.domain.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface GoodMapper {
    int insertGood(@Param("postNo") int postNo, @Param("userNo") int userNo);
    int deleteGood(@Param("postNo") int postNo, @Param("userNo") int userNo);
    Optional<Good> selectGood(@Param("postNo") int postNo, @Param("userNo") int userNo);
    int getGoodCount(@Param("postNo") int postNo);
}
