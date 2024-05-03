package com.fx.knutNotice.web.form;

import com.google.api.Http;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ResultForm {

    private int statusCode;
    private String message;
    private Object data;

    public static ResultForm success(String message, Object data) {
        return ResultForm.builder()
            .data(data)
            .statusCode(HttpStatus.OK.value())
            .message(message)
            .build();
    }

    public static ResultForm success(String message) {
        return ResultForm.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .message(message)
            .build();
    }

    public static ResultForm fail(int statusCode, String message) {
        return ResultForm.builder()
            .statusCode(statusCode)
            .message(message)
            .build();
    }

}
