package com.fx.knutNotice.common;

public enum FcmMessage {

    SUCCESS_TOKEN_REGISTRATION("Device Token 저장 성공"),
    SUCCESS_MESSAGE_SENT_TO_ALL("전체 발송 성공"),
    SUCCESS_ALL_TOKENS_RETRIEVED("전체 토큰 조회 성공");

    private final String description;

    FcmMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
