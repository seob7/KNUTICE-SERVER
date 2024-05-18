package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.BaseNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.dto.NewsListDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardListService {

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    public List<NewsListDTO> showGeneralNewsList(Long startBoardNumber, int size) {
        return getNewsList(generalNewsRepository, startBoardNumber, size);
    }

    public List<NewsListDTO> showScholarshipNews(Long startBoardNumber, int size) {
        return getNewsList(scholarshipNewsRepository, startBoardNumber, size);
    }

    public List<NewsListDTO> showEventNews(Long startBoardNumber, int size) {
        return getNewsList(eventNewsRepository, startBoardNumber, size);
    }

    public List<NewsListDTO> showAcademicNews(Long startBoardNumber, int size) {
        return getNewsList(academicNewsRepository, startBoardNumber, size);
    }

    private List<NewsListDTO> getNewsList(BaseNewsRepository repository, Long startBoardNumber, int size) {

        // startBoardNumber가 null일 경우 최근 게시물부터 20개를 가져옴
        if (startBoardNumber == null)
            return repository.find20RecentNews(
                PageRequest.of(0, size, Sort.by("boardNumber").descending()));


        return repository.findPaginationList(startBoardNumber,
            PageRequest.of(0, size, Sort.by("boardNumber").descending()));

    }
}
