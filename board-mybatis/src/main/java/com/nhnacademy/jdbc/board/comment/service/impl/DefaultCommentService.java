package com.nhnacademy.jdbc.board.comment.service.impl;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.mapper.CommentMapper;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentService implements CommentService {
    private final CommentMapper commentMapper;

    public DefaultCommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    private List<Comment> newLineParser(List<Comment> comments) {
        comments.forEach(comment -> {
            comment.setCommentContent(comment.getCommentContent().replace("\n", "<br/>"));
        });
        return comments;
    }

    @Override
    public List<Comment> getComments(int postNo) {
        return newLineParser(commentMapper.selectCommentsByPostNo(postNo));
    }

    @Override
    public Optional<Comment> getComment(int commentNo) {
        return commentMapper.selectComment(commentNo);
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    public void modifyComment(int loginUserNo, int commentNo, String modifyContent) {
        getComment(commentNo).ifPresent(comment -> {
            if (comment.getUserNo() == loginUserNo) {
                comment.setCommentContent(modifyContent);
                commentMapper.updateComment(comment);
            }
        });
    }

    @Override
    public int deleteComment(int commentNo) {
        return commentMapper.deleteCommentByNo(commentNo);
    }
}
