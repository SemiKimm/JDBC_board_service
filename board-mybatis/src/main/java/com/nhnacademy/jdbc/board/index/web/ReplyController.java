package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.reply.domain.Reply;
import com.nhnacademy.jdbc.board.reply.service.ReplyService;
import com.nhnacademy.jdbc.board.utils.SessionUtils;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/register")
    public String replyRegisterForm(@RequestParam("parentPostNo") int parentPostNo,
                                    Model model) {
        model.addAttribute("parentPostNo", parentPostNo);
        return "/reply/replyForm";
    }

    @PostMapping("/register")
    public String doRegisterReply(@RequestParam("replyTitle") String replyTitle,
                                  @RequestParam("replyContent") String replyContent,
                                  @RequestParam("parentPostNo") Integer parentPostNo,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (SessionUtils.checkLogin(session)) {
            int writerNo = (int) session.getAttribute("no");
            replyService.registerReply(replyTitle, replyContent, writerNo, parentPostNo);
        }

        return "redirect:/post/list";
    }
}
