package com.fx.knutNotice.web;

import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.service.BoardService;
import com.fx.knutNotice.domain.entity.GeneralNews;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/general-news")
    public ResponseEntity showGeneralNews() { //일반소식
        List<GeneralNews> generalNews = boardService.showGeneralNews();
        return ResponseEntity.ok(generalNews);
    }

    @GetMapping("/scholarship-news")
    public ResponseEntity showScholarshipNews() { //장학안내
        List<ScholarshipNews> scholarshipNews = boardService.showScholarshipNews();
        return ResponseEntity.ok(scholarshipNews);
    }

    @GetMapping("/event-news")
    public ResponseEntity showEventNews() { //행사안내
        List<EventNews> eventNews = boardService.showEventNews();
        return ResponseEntity.ok(eventNews);
    }

    @GetMapping("/academic-news") //학사공지사항
    public ResponseEntity showAcademicNews() {
        List<AcademicNews> academicNews = boardService.showAcademicNews();
        return ResponseEntity.ok(academicNews);
    }




}
