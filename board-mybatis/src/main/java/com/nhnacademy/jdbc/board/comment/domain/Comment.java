package com.nhnacademy.jdbc.board.comment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Comment {
    private int commentNo;
    @Setter
    private String commentContent;
    private final int postNo;
    private final int userNo;

    // insert 할 comment 객체 생성시 사용되는 생성자
    public Comment(String commentContent, int postNo, int userNo) {
        this.commentContent = commentContent;
        this.postNo = postNo;
        this.userNo = userNo;
    }

    // db 로부터 파싱할때 사용되는 생성자
    public Comment(int commentNo, String commentContent, int postNo, int userNo) {
        this.commentNo = commentNo;
        this.commentContent = commentContent;
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
