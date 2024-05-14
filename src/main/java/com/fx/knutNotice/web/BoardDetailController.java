package com.fx.knutNotice.web;

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
        return ResultForm.success("성공", boardDTO);
    }
}
