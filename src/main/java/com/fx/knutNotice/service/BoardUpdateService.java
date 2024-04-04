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

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    private final JsoupCrawling jsoupCrawling;

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60 * 60)// 60분마다 실행
    public void updateCheck() throws IOException {
        updateNews(generalNewsRepository, KnutURL.GENERAL_NEWS);
        updateNews(scholarshipNewsRepository, KnutURL.SCHOLARSHIP_NEWS);
        updateNews(eventNewsRepository, KnutURL.EVENT_NEWS);
        updateNews(academicNewsRepository, KnutURL.ACADEMIC_NEWS);
    }




    private <T> void updateNews(JpaRepository<T, Long> repository, KnutURL url) throws IOException {
        List<BoardDTO> newList = jsoupCrawling.crawlBoard(url.URL());
        Set<Long> oldNttIds = repository.findAll().stream()
                .map(entity -> {
                    if (entity instanceof AcademicNews) {
                        return ((AcademicNews) entity).getNttId();
                    } else if (entity instanceof EventNews) {
                        return ((EventNews) entity).getNttId();
                    } else if (entity instanceof GeneralNews) {
                        return ((GeneralNews) entity).getNttId();
                    } else if (entity instanceof ScholarshipNews) {
                        return ((ScholarshipNews) entity).getNttId();
                    }
                    return null;
                })
                .collect(Collectors.toSet());
        updateNews(repository, newList, oldNttIds);
    }

    private <T> void updateNews(JpaRepository<T, Long> repository, List<BoardDTO> newList, Set<Long> oldNttIds) {
        for (BoardDTO newBoard : newList) {
            boolean isNew = !oldNttIds.contains(newBoard.getNttId());
            T newEntity = null;
            if (repository instanceof AcademicNewsRepository) {
                newEntity = (T) AcademicNews.builder()
                        .nttId(newBoard.getNttId())
                        .boardNumber(newBoard.getBoardNumber())
                        .title(newBoard.getTitle())
                        .newCheck(Boolean.toString(isNew))
                        .build();
            } else if (repository instanceof EventNewsRepository) {
                newEntity = (T) EventNews.builder()
                        .nttId(newBoard.getNttId())
                        .boardNumber(newBoard.getBoardNumber())
                        .title(newBoard.getTitle())
                        .newCheck(Boolean.toString(isNew))
                        .build();
            } else if (repository instanceof GeneralNewsRepository) {
                newEntity = (T) GeneralNews.builder()
                        .nttId(newBoard.getNttId())
                        .boardNumber(newBoard.getBoardNumber())
                        .title(newBoard.getTitle())
                        .newCheck(Boolean.toString(isNew))
                        .build();
            } else if (repository instanceof ScholarshipNewsRepository) {
                newEntity = (T) ScholarshipNews.builder()
                        .nttId(newBoard.getNttId())
                        .boardNumber(newBoard.getBoardNumber())
                        .title(newBoard.getTitle())
                        .newCheck(Boolean.toString(isNew))
                        .build();
            }
            repository.save(newEntity);
        }
    }
}