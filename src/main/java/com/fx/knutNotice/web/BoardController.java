package com.fx.knutNotice.web;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.domain.entity.*;
import com.fx.knutNotice.service.BoardService;
import com.fx.knutNotice.web.form.ResultForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Board Controller", description = "Board Controller API")
public class BoardController {
    private final BoardService boardService;

    private final static byte GENERALNEWS = 0;
    private final static byte SCHOLARSHIPNEWS = 1;
    private final static byte EVENTNEWS = 2;
    private final static byte ACADEMICNEWS = 3;


    @GetMapping("/generalNews")
    @Operation(summary = "일반소식", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "일반소식 요청 성공")
    public ResultForm showGeneralNews() { //일반소식
        List<BaseNews> generalNews = boardService.showNews(GENERALNEWS);
        return ResultForm.success(ResponseMessage.SUCCESS_GENERAL_NEWS.getDescription(), generalNews);
    }



    @GetMapping("/scholarshipNews")
    @Operation(summary = "장학안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "장학안내 요청 성공")
    public ResultForm showScholarshipNews() { //장학안내
        List<BaseNews> scholarshipNews = boardService.showNews(SCHOLARSHIPNEWS);
        return ResultForm.success(ResponseMessage.SUCCESS_SCHOLARSHIP_NEWS.getDescription(), scholarshipNews);
    }

    @GetMapping("/eventNews")
    @Operation(summary = "행사안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "행사안내 요청 성공")
    public ResultForm showEventNews() { //행사안내
        List<BaseNews> eventNews = boardService.showNews(EVENTNEWS);
        return ResultForm.success(ResponseMessage.SUCCESS_EVENT_NEWS.getDescription(), eventNews);
    }

    @GetMapping("/academicNews") //학사공지사항
    @Operation(summary = "학사공지사항", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "학사공지사항 요청 성공")
    public ResultForm showAcademicNews() {
        List<BaseNews> academicNews = boardService.showNews(ACADEMICNEWS);
        return ResultForm.success(ResponseMessage.SUCCESS_ACADEMIC_NEWS.getDescription(), academicNews);
    }

}
