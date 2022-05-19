package com.nhnacademy.jdbc.board.post.domain;

import java.util.Date;

public class Post {
    private final int postNo;
    private final String postTitle;
    private final String postContent;
    private final Date writeDateTime;
    private final int replyOrder;
    private final int userNo; // fk
    private final int boardTypeCode; // fk
    private final int parentPostNo; // fk
    private final int topPostNo; // fk
    private int modifierUserNo; // fk
    private Date modifyDatetime;

    public Post(int postNo, String postTitle, String postContent, Date writeDateTime,
                int replyOrder,
                int userNo, int boardTypeCode, int parentPostNo, int topPostNo) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.writeDateTime = writeDateTime;
        this.replyOrder = replyOrder;
        this.userNo = userNo;
        this.boardTypeCode = boardTypeCode;
        this.parentPostNo = parentPostNo;
        this.topPostNo = topPostNo;
    }
}
