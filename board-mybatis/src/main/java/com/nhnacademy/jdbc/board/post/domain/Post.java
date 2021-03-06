package com.nhnacademy.jdbc.board.post.domain;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Post {
    private final Integer postNo;
    @Setter
    private String postTitle;
    @Setter
    private String postContent;
    private Date writeDateTime;
    private int replyOrder;
    private int userNo; // fk
    private int boardTypeCode; // fk
    private Integer parentPostNo; // fk
    private Integer topPostNo; // fk
    @Setter
    private Integer modifierUserNo; // fk
    @Setter
    private Date modifyDatetime;
    @Setter
    private boolean isDelete = false;

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

    public Post(Integer postNo, String postTitle, String postContent, Date writeDateTime,
                int replyOrder,
                int userNo,Integer modifierUserNo, int boardTypeCode, Integer parentPostNo, Integer topPostNo) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.writeDateTime = writeDateTime;
        this.replyOrder = replyOrder;
        this.userNo = userNo;
        this.modifierUserNo = modifierUserNo;
        this.boardTypeCode = boardTypeCode;
        this.parentPostNo = parentPostNo;
        this.topPostNo = topPostNo;
    }

    public Post(Integer postNo, String postTitle, String postContent, Date writeDateTime, int replyOrder, int userNo, int boardTypeCode, Integer parentPostNo, Integer topPostNo, Integer modifierUserNo, Date modifyDatetime) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.writeDateTime = writeDateTime;
        this.replyOrder = replyOrder;
        this.userNo = userNo;
        this.boardTypeCode = boardTypeCode;
        this.parentPostNo = parentPostNo;
        this.topPostNo = topPostNo;
        this.modifierUserNo = modifierUserNo;
        this.modifyDatetime = modifyDatetime;
    }

    public Post(Integer postNo) {
        this.postNo = postNo;
    }
}
