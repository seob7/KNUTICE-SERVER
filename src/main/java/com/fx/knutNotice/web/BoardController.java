package com.fx.knutNotice.web;

import com.fx.knutNotice.JsoupCrawlingService;
import com.fx.knutNotice.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final JsoupCrawlingService jsoupCrawlingService;

    @GetMapping()
    public List<Board> showBoardList() {
        try {
            List<Board> board = jsoupCrawlingService.getBoard();
            return board;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }



}
