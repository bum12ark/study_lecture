package com.sjnono.domain.bbs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller @RequestMapping("/bbs")
public class BbsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BbsService bbsService;

    public BbsController(BbsService bbsService) {
        this.bbsService = bbsService;
    }

    public void printLog(String methodName, String msg) {
        logger.info("######################################");
        logger.info("## BbsController." + methodName + ": " +  msg);
        logger.info("######################################");
    }

    @GetMapping("")
    public String board(Model model) {
        this.printLog("board", "게시판 메인");

        // 게시물 조회
        List<Board> boardList = bbsService.findJoinMember();

        model.addAttribute("boardList", boardList);
        return "/bbs/board";
    }

}
