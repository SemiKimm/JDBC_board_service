package com.nhnacademy.jdbc.board.post.domain;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Post {
    private final Integer postNo;
    private final String postTitle;
    private final String postContent;
    private final Date writeDateTime;
    private final int replyOrder;
    private final int userNo; // fk
    private final int boardTypeCode; // fk
    private final Integer parentPostNo; // fk
    private final Integer topPostNo; // fk
    private int modifierUserNo; // fk
    private Date modifyDatetime;
    @Setter
    private boolean isDelete = true;

    public Post(Integer postNo, String postTitle, String postContent, Date writeDateTime,
                int replyOrder,
                int userNo, int boardTypeCode, Integer parentPostNo, Integer topPostNo) {
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
