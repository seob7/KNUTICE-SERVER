package com.fx.knutNotice.service;

import com.fx.knutNotice.config.JsoupCrawling;
import com.fx.knutNotice.config.KnutURL;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.service.newsUpdateService.AcademicNewsUpdateService;
import com.fx.knutNotice.service.newsUpdateService.EventNewsUpdateService;
import com.fx.knutNotice.service.newsUpdateService.GeneralNewsUpdateService;
import com.fx.knutNotice.service.newsUpdateService.ScholarshipNewsUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardUpdateService {

    private final GeneralNewsUpdateService generalNewsUpdateService;
    private final EventNewsUpdateService eventNewsUpdateService;
    private final AcademicNewsUpdateService academicNewsUpdateService;
    private final ScholarshipNewsUpdateService scholarshipNewsUpdateService;

    private final JsoupCrawling jsoupCrawling;

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60 * 60)// 60분마다 실행
    public void updateCheck() throws IOException {
        List<BoardDTO> generalNewsList = jsoupCrawling.crawlBoard(KnutURL.GENERAL_NEWS.URL(),
                KnutURL.GENERAL_NEWS.articleURL());
        generalNewsUpdateService.newsCheck(generalNewsList);

        List<BoardDTO> eventNewsList = jsoupCrawling.crawlBoard(KnutURL.EVENT_NEWS.URL());
        eventNewsUpdateService.newsCheck(eventNewsList);

        List<BoardDTO> academicNewsList = jsoupCrawling.crawlBoard(KnutURL.ACADEMIC_NEWS.URL());
        academicNewsUpdateService.newsCheck(academicNewsList);

        List<BoardDTO> scholarshipNewsList = jsoupCrawling.crawlBoard(KnutURL.SCHOLARSHIP_NEWS.URL());
        scholarshipNewsUpdateService.newsCheck(scholarshipNewsList);
    }
}
