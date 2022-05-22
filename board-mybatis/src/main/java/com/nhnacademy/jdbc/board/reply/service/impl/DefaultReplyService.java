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
            //fixme 이때 replyorder 어케 구할까..
            reply.setTopPostNo(parentPost.getTopPostNo());

        }
        /*
        1
        2
            4 t:2 p:2
            7 t:2 p:2
                8  t:2 p:7
                    9  t:2 p:7
                        10  t:2 p:7
                            11  t:2 p:7
        3
            5
                6
         */
        return 0;
    }
}

