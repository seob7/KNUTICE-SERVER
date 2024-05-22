package com.fx.knutNotice.worker;

import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.factory.ServiceFactory;
import com.fx.knutNotice.service.newsUpdateService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BoardUpdateWorker {
    private final ServiceFactory serviceFactory;
    public void updateBoardTitles (final List<BoardDTO> titles, final byte type){
        BaseNewsService baseNewsService = serviceFactory.getService(type);
        baseNewsService.updateNews(titles, type).getUpdateTitles();
    }
}
