package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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


    public List<BaseNews> showNews (final int newsType) {
        switch (newsType) {
            case 0:
                return new ArrayList<>(generalNewsRepository.findAll());
            case 1:
                return new ArrayList<>(scholarshipNewsRepository.findAll());
            case 2:
                return new ArrayList<>(eventNewsRepository.findAll());
            case 3:
                return new ArrayList<>(academicNewsRepository.findAll());
            default:
                throw new IllegalArgumentException("Invalid newsType");
        }
    }
}
