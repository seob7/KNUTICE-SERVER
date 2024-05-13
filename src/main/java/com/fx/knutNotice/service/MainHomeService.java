package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.*;
import com.fx.knutNotice.dto.NewsListDTO;
import com.fx.knutNotice.web.form.ResponseForm.MainForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MainHomeService {

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    public MainForm showMainTopThreeTitle() {

        List<NewsListDTO> generalNewsRecent3Title = generalNewsRepository.findRecent3Title();
        List<NewsListDTO> scholarshipNewsRecent3Title = scholarshipNewsRepository.findRecent3Title();
        List<NewsListDTO> eventNewsRecent3Title = eventNewsRepository.findRecent3Title();
        List<NewsListDTO> academicNewsRecent3Title = academicNewsRepository.findRecent3Title();

        return new MainForm(generalNewsRecent3Title, scholarshipNewsRecent3Title,
            eventNewsRecent3Title, academicNewsRecent3Title);
    }


}
