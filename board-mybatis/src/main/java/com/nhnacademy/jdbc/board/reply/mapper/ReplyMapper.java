package com.nhnacademy.jdbc.board.reply.mapper;

import com.nhnacademy.jdbc.board.post.dto.ReplierPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyMapper {
    ReplierPost findParentPost(@Param("parentPostNo") Integer parentPostNo);
}
