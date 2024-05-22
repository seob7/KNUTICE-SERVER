package com.fx.knutNotice.worker;

import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.factory.ServiceFactory;
import com.fx.knutNotice.service.FcmService;
import com.fx.knutNotice.service.newsUpdateService.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BoardUpdateWorker {
    private final ServiceFactory serviceFactory;
    private final FcmService fcmService;

    public void updateBoardTitles (final List<BoardDTO> titles, final byte type)
        throws FirebaseMessagingException {
        BaseNewsService baseNewsService = serviceFactory.getService(type);
        List updateTitles = baseNewsService.updateNews(titles, type).getUpdateTitles();

        fcmService.fcmTrigger(updateTitles, type);
    }
}
