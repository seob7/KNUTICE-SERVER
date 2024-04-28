package com.fx.knutNotice.common;

public enum ResponseMessage {


    SUCCESS_GENERAL_NEWS("일반소식 요청 성공"),
    SUCCESS_SCHOLARSHIP_NEWS("장학안내 요청 성공"),
    SUCCESS_EVENT_NEWS("행사안내 요청 성공"),
    SUCCESS_ACADEMIC_NEWS("학사공지사항 요청 성공");

    private final String description;

    ResponseMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
