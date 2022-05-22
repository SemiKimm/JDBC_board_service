package com.nhnacademy.jdbc.board.reply.domain;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Reply {
    private final Integer postNo;
    @Setter
    private String postTitle;
    @Setter
    private String postContent;
    private final Date writeDateTime;
    @Setter
    private int replyOrder;
    private final int userNo; // fk
    private final int boardTypeCode; // fk
    private final Integer parentPostNo; // fk
    @Setter
    private Integer topPostNo; // fk
    @Setter
    private Integer modifierUserNo; // fk
    @Setter
    private Date modifyDatetime;
    @Setter
    private boolean isDelete = false;

    public Reply(Integer postNo, String postTitle, String postContent, Date writeDateTime,
                 int userNo,
                 int boardTypeCode, Integer parentPostNo, Integer topPostNo) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.writeDateTime = writeDateTime;
        this.userNo = userNo;
        this.boardTypeCode = boardTypeCode;
        this.parentPostNo = parentPostNo;
        this.topPostNo = topPostNo;
    }
}
