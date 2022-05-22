package com.nhnacademy.jdbc.board.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ReplierPost {
    private Integer postNo;
    @Setter
    private Integer topPostNo; // 답글 포스트 번호 (오른쪽 노드)
    @Setter
    private Integer parentPostNo; // 동일한 depth 의 답글 번호 (왼쪽 노드)
    private Integer replyOrder;

    public ReplierPost(Integer postNo, Integer topPostNo, Integer parentPostNo, Integer replyOrder) {
        this.postNo = postNo;
        this.topPostNo = topPostNo;
        this.parentPostNo = parentPostNo;
        this.replyOrder = replyOrder;
    }
}
