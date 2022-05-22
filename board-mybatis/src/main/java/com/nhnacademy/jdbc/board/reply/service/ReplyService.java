package com.nhnacademy.jdbc.board.reply.service;

public interface ReplyService {
    int registerReply(String replyTitle, String replyContent, int writerNo, Integer parentPostNo);
}
