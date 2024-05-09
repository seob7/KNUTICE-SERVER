package com.fx.knutNotice.web;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.service.MainHomeService;
import com.fx.knutNotice.web.form.ResultForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
@Tag(name = "Main Home Controller", description = "Main Home Controller API")
public class MainHomeController {

    private final MainHomeService mainHomeService;

    @GetMapping()
    @Operation(summary = "Main Home API", description = "최신 3개의 간단 내용만 보여주는 API ")
    @ApiResponse(responseCode = "200", description = "메인 페이지 요청 성공")
    public ResultForm showMainPage() {
        return ResultForm.success(ResponseMessage.SUCCESS_MAIN_PAGE.getDescription(),
            mainHomeService.showMainTopThreeTitle());
    }

}
