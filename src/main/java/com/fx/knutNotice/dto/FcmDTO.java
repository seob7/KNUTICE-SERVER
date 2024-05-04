package com.fx.knutNotice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmDTO {

    private String targetToken;
    private String title;
    private String content;

    public static FcmDTO of(String targetToken, String title, String content) {
        FcmDTO dto = new FcmDTO();
        dto.targetToken = targetToken;
        dto.title = title;
        dto.content = content;
        return dto;
    }
}
