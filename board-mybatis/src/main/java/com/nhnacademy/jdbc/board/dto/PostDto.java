package com.nhnacademy.jdbc.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {
    private Integer postNo;
    private String postTitle;
    private String postContent;
    private Date writeDateTime;
    private int replyOrder;
    private int userNo; // fk
    private int boardTypeCode; // fk
    private Integer parentPostNo; // fk
    private Integer topPostNo; // fk
    private Integer modifierUserNo; // fk
    private Date modifyDatetime;
    private boolean isDelete = false;

}
