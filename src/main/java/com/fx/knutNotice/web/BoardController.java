package com.fx.knutNotice.web;

import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.service.BoardService;
import com.fx.knutNotice.domain.entity.GeneralNews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Board Controller", description = "Board Controller API")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/general-news")
    @Operation(summary = "일반소식", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "일반소식 요청 성공")
    public ResponseEntity showGeneralNews() { //일반소식
        List<GeneralNews> generalNews = boardService.showGeneralNews();
        return new ResponseEntity<>(generalNews, HttpStatus.OK);
    }

    @GetMapping("/scholarship-news")
    @Operation(summary = "장학안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "장학안내 요청 성공")
    public ResponseEntity showScholarshipNews() { //장학안내
        List<ScholarshipNews> scholarshipNews = boardService.showScholarshipNews();
        return new ResponseEntity<>(scholarshipNews, HttpStatus.OK);
    }

    @GetMapping("/event-news")
    @Operation(summary = "행사안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "행사안내 요청 성공")
    public ResponseEntity showEventNews() { //행사안내
        List<EventNews> eventNews = boardService.showEventNews();
        return new ResponseEntity<>(eventNews, HttpStatus.OK);
    }

    @GetMapping("/academic-news") //학사공지사항
    @Operation(summary = "학사공지사항", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "학사공지사항 요청 성공")
    public ResponseEntity showAcademicNews() {
        List<AcademicNews> academicNews = boardService.showAcademicNews();
        return new ResponseEntity<>(academicNews, HttpStatus.OK);
    }




}
