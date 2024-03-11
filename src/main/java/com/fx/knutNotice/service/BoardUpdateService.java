package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.BoardRepository;
import com.fx.knutNotice.domain.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardUpdateService {

    private final BoardRepository boardRepository;

    public void updateCheck() {
        List<Board> all = boardRepository.findAll();
    }

}
