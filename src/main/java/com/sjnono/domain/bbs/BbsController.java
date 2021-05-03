package com.sjnono.domain.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller @RequestMapping("/bbs")
public class BbsController {
    @Autowired
    private BbsService bbsService;

    @GetMapping("")
    public String board(Model model) {
        Page<Board> boardByPageRequest = bbsService.findBoardByPageRequest(0, 10);
        model.addAttribute("boardByPageRequest", boardByPageRequest);
        return "/bbs/board";
    }

}
