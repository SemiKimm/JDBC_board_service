package com.nhnacademy.jdbc.board.comment.mapper;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    Optional<Comment> selectComment(@Param("commentNo") int commentNo);

    List<Comment> selectComments(@Param("postNo") int postNo);

    int insertComment(@Param("comment") Comment comment);

    int updateComment(@Param("comment") Comment comment);

    int deleteCommentByNo(@Param("commentNo") int commentNo);
}
