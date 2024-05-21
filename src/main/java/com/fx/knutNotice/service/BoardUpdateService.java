package com.fx.knutNotice.service;

import com.fx.knutNotice.crawler.KnutCrawler;
import com.fx.knutNotice.common.KnutURL;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.dto.FcmDTO;
import com.fx.knutNotice.service.newsUpdateService.*;
import com.google.firebase.messaging.FirebaseMessagingException;
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

    private final KnutCrawler knutCrawler;
    private final FcmService fcmService;

    @Transactional
    @Scheduled(cron = "0 0 8-20 * * MON-FRI") //월요일부터 금요일까지 매 시간 정각마다 실행되지만 8시부터 20시까지만 실행
    public void updateCheck() throws IOException, FirebaseMessagingException {
        List<BoardDTO> eventNewsList = knutCrawler.crawlBoard(KnutURL.EVENT_NEWS.URL(),
                KnutURL.EVENT_NEWS.articleURL());
        List<String> updatedEventNewsTitle = getUpdatedNewsTitles(eventNewsList, eventNewsUpdateService);

        List<BoardDTO> generalNewsList = knutCrawler.crawlBoard(KnutURL.GENERAL_NEWS.URL(),
            KnutURL.GENERAL_NEWS.articleURL());
        List<String> updatedGeneralNewsTitle = getUpdatedNewsTitles(generalNewsList, generalNewsUpdateService);

        List<BoardDTO> academicNewsList = knutCrawler.crawlBoard(KnutURL.ACADEMIC_NEWS.URL(),
                KnutURL.ACADEMIC_NEWS.articleURL());
        List<String> updatedAcademicNewsTitle = getUpdatedNewsTitles(academicNewsList, academicNewsUpdateService);

        List<BoardDTO> scholarshipNewsList = knutCrawler.crawlBoard(KnutURL.SCHOLARSHIP_NEWS.URL(),
                KnutURL.SCHOLARSHIP_NEWS.articleURL());
        List<String> updatedScholarshipNewsTitle = getUpdatedNewsTitles(scholarshipNewsList, scholarshipNewsUpdateService);


        fcmService.fcmTrigger(updatedGeneralNewsTitle, updatedEventNewsTitle, updatedAcademicNewsTitle,
            updatedScholarshipNewsTitle);

    }

    /**
     * 새로운 뉴스의 제목을 반환.
     *
     * @param newsList
     * @param newsUpdateService
     */
    private List<String> getUpdatedNewsTitles(List<BoardDTO> newsList, BaseNewsService newsUpdateService) {
        return newsUpdateService.updateNews(newsList).getUpdateTitles();
    }
}