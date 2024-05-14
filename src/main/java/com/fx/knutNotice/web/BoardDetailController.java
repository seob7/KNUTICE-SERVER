package com.fx.knutNotice.web;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.service.BoardDetailService;
import com.fx.knutNotice.web.form.ResultForm;
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
    public ResultForm showGeneralNewsDetail(@PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showGeneralNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_GENERAL_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/scholarshipNews/{nttId}")
    public ResultForm showScholarshipNewsDetail(@PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showScholarshipNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_SCHOLARSHIP_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/eventNews/{nttId}")
    public ResultForm showEventNewsDetail(@PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showEventNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_EVENT_NEWS.getDescription(), boardDTO);
    }

    @GetMapping("/academicNews/{nttId}")
    public ResultForm showAcademicNewsDetail(@PathVariable Long nttId) {
        BoardDTO boardDTO = boardDetailService.showAcademicNewsDetail(nttId);
        return ResultForm.success(ResponseMessage.SUCCESS_ACADEMIC_NEWS.getDescription(), boardDTO);
    }
}
