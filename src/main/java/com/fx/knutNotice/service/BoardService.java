package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.dto.NewsListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    public Page<NewsListDTO> showGeneralNewsList(Pageable pageable) { //일반공지
        Page<NewsListDTO> generalNewsPageList = generalNewsRepository.findPaginationList(pageable);
        return generalNewsPageList;
    }

    public Page<NewsListDTO> showScholarshipNews(Pageable pageable) { //장학안내
        Page<NewsListDTO> scholarshipNewsPageList = scholarshipNewsRepository.findPaginationList(pageable);
        return scholarshipNewsPageList;
    }

    public Page<NewsListDTO> showEventNews(Pageable pageable) { //행사안내
        Page<NewsListDTO> eventNewsPageList = eventNewsRepository.findPaginationList(pageable);
        return eventNewsPageList;
    }

    public Page<NewsListDTO> showAcademicNews(Pageable pageable) { //학사공지사항
        Page<NewsListDTO> academicNewsPageList = academicNewsRepository.findPaginationList(pageable);
        return academicNewsPageList;
    }


}
