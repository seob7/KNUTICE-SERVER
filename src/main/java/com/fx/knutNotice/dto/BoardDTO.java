package com.fx.knutNotice.dto;
import com.fx.knutNotice.domain.entity.GeneralNews;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class BoardDTO {
    private List<GeneralNews> generalNewsList;

    public BoardDTO(List<GeneralNews> generalNews) {
        this.generalNewsList = generalNews;
    }
}
