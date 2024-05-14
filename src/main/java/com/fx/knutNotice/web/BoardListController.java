package com.fx.knutNotice.web;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.dto.NewsListDTO;
import com.fx.knutNotice.service.BoardListService;
import com.fx.knutNotice.web.form.ResultForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Board Controller", description = "Board Controller API")
public class BoardListController {

    private final BoardListService boardListService;

    @GetMapping("/generalNews")
    @Operation(summary = "일반소식", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "일반소식 요청 성공")
    public ResultForm showGeneralNews(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0", in = ParameterIn.QUERY) @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "한 페이지당 항목 수", example = "20", in = ParameterIn.QUERY) @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nttId").descending());
        List<NewsListDTO> generalNewsList = boardListService.showGeneralNewsList(pageable).getContent();
        return ResultForm.success(ResponseMessage.SUCCESS_GENERAL_NEWS.getDescription(), generalNewsList);
    }

    @GetMapping("/scholarshipNews")
    @Operation(summary = "장학안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "장학안내 요청 성공")
    public ResultForm showScholarshipNews(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0", in = ParameterIn.QUERY) @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "한 페이지당 항목 수", example = "20", in = ParameterIn.QUERY) @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nttId").descending());
        List<NewsListDTO> scholarshipNewsList = boardListService.showScholarshipNews(pageable).getContent();
        return ResultForm.success(ResponseMessage.SUCCESS_SCHOLARSHIP_NEWS.getDescription(), scholarshipNewsList);
    }

    @GetMapping("/eventNews")
    @Operation(summary = "행사안내", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "행사안내 요청 성공")
    public ResultForm showEventNews(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0", in = ParameterIn.QUERY) @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "한 페이지당 항목 수", example = "20", in = ParameterIn.QUERY) @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nttId").descending());
        List<NewsListDTO> eventNewsList = boardListService.showEventNews(pageable).getContent();
        return ResultForm.success(ResponseMessage.SUCCESS_EVENT_NEWS.getDescription(), eventNewsList);
    }

    @GetMapping("/academicNews")
    @Operation(summary = "학사공지사항", description = "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardList.do")
    @ApiResponse(responseCode = "200", description = "학사공지사항 요청 성공")
    public ResultForm showAcademicNews(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0", in = ParameterIn.QUERY) @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "한 페이지당 항목 수", example = "20", in = ParameterIn.QUERY) @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nttId").descending());
        List<NewsListDTO> academicNewsList = boardListService.showAcademicNews(pageable).getContent();
        return ResultForm.success(ResponseMessage.SUCCESS_ACADEMIC_NEWS.getDescription(), academicNewsList);
    }

}
