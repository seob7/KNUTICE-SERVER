package com.fx.knutNotice.web;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.service.BoardDetailService;
import com.fx.knutNotice.web.form.ResultForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "BoardDetail Controller", description = "BoardDetail Controller API")
public class BoardDetailController {

    private final BoardDetailService boardDetailService;

    @GetMapping("/generalNews/{nttId}")
    @Operation(summary = "일반소식", description = "일반소식 단일 게시글 정보")
    @ApiResponse(responseCode = "200", description = "일반소식 요청 성공")
    public ResultForm showGeneralNewsDetail(
        @Parameter(name = "nttId",required = true, in = ParameterIn.PATH)
        @PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showGeneralNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_GENERAL_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/scholarshipNews/{nttId}")
    @Operation(summary = "장학안내", description = "장학안내 단일 게시글 정보")
    @ApiResponse(responseCode = "200", description = "장학안내 요청 성공")
    public ResultForm showScholarshipNewsDetail(
        @Parameter(name = "nttId",required = true, in = ParameterIn.PATH)
        @PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showScholarshipNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_SCHOLARSHIP_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/eventNews/{nttId}")
    @Operation(summary = "행사안내", description = "행사안내 단일 게시글 정보")
    @ApiResponse(responseCode = "200", description = "행사안내 요청 성공")
    public ResultForm showEventNewsDetail(
        @Parameter(name = "nttId",required = true, in = ParameterIn.PATH)
        @PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showEventNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_EVENT_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/academicNews/{nttId}")
    @Operation(summary = "학사공지사항", description = "학사공지사항 단일 게시글 정보")
    @ApiResponse(responseCode = "200", description = "학사공지사항 요청 성공")
    public ResultForm showAcademicNewsDetail(
        @Parameter(name = "nttId",required = true, in = ParameterIn.PATH)
        @PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showAcademicNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_ACADEMIC_NEWS.getDescription(), boardDTO);
    }
}
