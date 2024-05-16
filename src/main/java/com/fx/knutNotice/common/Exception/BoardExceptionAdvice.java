package com.fx.knutNotice.common.Exception;

import com.fx.knutNotice.common.ResponseMessage;
import com.fx.knutNotice.web.form.ResultForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BoardExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFountBoardException.class)
    public ResultForm boardNotFoundException() {
        log.info("게시글이 존재하지 않습니다.");
        return ResultForm.fail(HttpStatus.NOT_FOUND.value(),
            ResponseMessage.FAIL_NOT_FOUND_BOARD.getDescription());
    }

}
