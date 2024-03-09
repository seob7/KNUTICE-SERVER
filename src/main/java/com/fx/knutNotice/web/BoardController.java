package com.fx.knutNotice.web;

import com.fx.knutNotice.service.BoardService;
import com.fx.knutNotice.config.JsoupCrawling;
import com.fx.knutNotice.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping()
    public ResponseEntity showBoardList() {
        List<Board> boards = boardService.showBoardList();
        return ResponseEntity.ok(boards);
    }



}
