package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    public List<GeneralNews> showGeneralNews() { //일반공지
        List<GeneralNews> generalNewsList = generalNewsRepository.findAll();
        return new ArrayList<>(generalNewsList);
    }

    public List<ScholarshipNews> showScholarshipNews() { //장학안내
        List<ScholarshipNews> scholarshipNews = scholarshipNewsRepository.findAll();
        return new ArrayList<>(scholarshipNews);
    }

    public List<EventNews> showEventNews() { //행사안내
        List<EventNews> eventNews = eventNewsRepository.findAll();
        return new ArrayList<>(eventNews);
    }

    public List<AcademicNews> showAcademicNews() { //학사공지사항
        List<AcademicNews> academicNews = academicNewsRepository.findAll();
        return new ArrayList<>(academicNews);
    }


}
