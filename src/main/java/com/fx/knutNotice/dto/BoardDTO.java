package com.fx.knutNotice.dto;
import com.fx.knutNotice.domain.entity.Board;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class BoardDTO {
    private List<Board> boardList;

    public BoardDTO(List<Board> boards) {
        this.boardList = boards;
    }
}
