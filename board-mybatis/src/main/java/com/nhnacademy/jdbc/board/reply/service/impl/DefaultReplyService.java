package com.nhnacademy.jdbc.board.reply.service.impl;

import com.nhnacademy.jdbc.board.post.dto.ReplierPost;
import com.nhnacademy.jdbc.board.reply.domain.Reply;
import com.nhnacademy.jdbc.board.reply.mapper.ReplyMapper;
import com.nhnacademy.jdbc.board.reply.service.ReplyService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class DefaultReplyService implements ReplyService {
    private final ReplyMapper replyMapper;

    public DefaultReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    private boolean hasTopPost(ReplierPost parentPost) {
        return Objects.nonNull(parentPost.getTopPostNo());
    }

    private boolean checkReplyOrder(ReplierPost parentPost){
        return Objects.nonNull(parentPost.getReplyOrder());
    }

    @Override
    public int registerReply(String replyTitle, String replyContent, int writerNo,
                             Integer parentPostNo) {
        ReplierPost parentPost = replyMapper.findParentPost(parentPostNo);
        Reply reply =
            new Reply(null, replyTitle, replyContent, new Timestamp(new Date().getTime()), writerNo,
                1, parentPost.getPostNo(), parentPost.getTopPostNo());
        if (!hasTopPost(parentPost)||!checkReplyOrder(parentPost)) {
            reply.setReplyOrder(1);
            reply.setTopPostNo(parentPost.getPostNo());
        } else {
            //todo:
            reply.setTopPostNo(parentPost.getTopPostNo());

        }
        return 0;
    }
}

