package com.fx.knutNotice.web;

import com.fx.knutNotice.service.BoardService;
import com.fx.knutNotice.domain.entity.GeneralNews;
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
        List<GeneralNews> generalNews = boardService.showBoardList();
        return ResponseEntity.ok(generalNews);
    }



}
