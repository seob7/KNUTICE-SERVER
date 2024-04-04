package com.fx.knutNotice.service;

import com.fx.knutNotice.config.JsoupCrawling;
import com.fx.knutNotice.config.KnutURL;
import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.service.NewsUpdateService.AcademicNewsUpdateService;
import com.fx.knutNotice.service.NewsUpdateService.EventNewsUpdateService;
import com.fx.knutNotice.service.NewsUpdateService.GeneralNewsUpdateService;
import com.fx.knutNotice.service.NewsUpdateService.ScholarshipNewsUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<BoardDTO> generalNewsList = jsoupCrawling.crawlBoard(KnutURL.GENERAL_NEWS.URL());
        generalNewsUpdateService.newsCheck(generalNewsList);

        List<BoardDTO> eventNewsList = jsoupCrawling.crawlBoard(KnutURL.EVENT_NEWS.URL());
        eventNewsUpdateService.newsCheck(eventNewsList);

        List<BoardDTO> academicNewsList = jsoupCrawling.crawlBoard(KnutURL.ACADEMIC_NEWS.URL());
        academicNewsUpdateService.newsCheck(academicNewsList);

        List<BoardDTO> scholarshipNewsList = jsoupCrawling.crawlBoard(KnutURL.SCHOLARSHIP_NEWS.URL());
        scholarshipNewsUpdateService.newsCheck(scholarshipNewsList);
    }
}
