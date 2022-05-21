package com.nhnacademy.jdbc.board.post.dto;

import java.util.Date;
import lombok.Getter;

@Getter
public class PostListDTO {
    private final int postNo;
    private final String postTitle;
    private final int userNo;
    private Integer modifierUserNo;
    private final String userNickname;
    private String modifierUserNickname;
    private final Date writeDatetime;
    private Integer commentCount;
    private Integer viewCount;
    private Integer replyOrder;
    private Integer parentPostNo;
    private Integer topPostNo;

    public PostListDTO(int postNo, String postTitle, int userNo, Integer modifierUserNo,
                       String userNickname, String modifierUserNickname, Date writeDatetime,
                       Integer commentCount,Integer viewCount, Integer replyOrder, Integer parentPostNo,
                       Integer topPostNo) {
        this.postNo = postNo;
        this.postTitle = postTitle;
        this.userNo = userNo;
        this.modifierUserNo = modifierUserNo;
        this.userNickname = userNickname;
        this.modifierUserNickname = modifierUserNickname;
        this.writeDatetime = writeDatetime;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.replyOrder = replyOrder;
        this.parentPostNo = parentPostNo;
        this.topPostNo = topPostNo;
    }
}
