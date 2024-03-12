package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.Board;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BoardRepository {


    private final Map<Long, Board> store = new ConcurrentHashMap<>();

    public void save(Board board) {
        store.put(board.getNttId(), board);
    }

    public void findByNttId(Long nttId) {
        store.get(nttId);
    }

    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteBoard(Long nttId) {
        if (store.containsKey(nttId)) {
            store.remove(nttId);
        }
    }
}
