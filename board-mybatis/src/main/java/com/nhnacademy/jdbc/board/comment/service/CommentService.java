package com.nhnacademy.jdbc.board.comment.service;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getComments(int postNo);
    Optional<Comment> getComment(int commentNo);
    int addComment(Comment comment);
    void modifyComment(int loginUserNo, int commentNo, String modifyContent);
    int deleteComment(int commentNo);
}
