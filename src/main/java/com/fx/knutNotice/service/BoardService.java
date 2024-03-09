package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.BoardRepository;
import com.fx.knutNotice.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> showBoardList() {

        List<Board> boardList = boardRepository.findAll();

        return new ArrayList<>(boardList);
    }



}
