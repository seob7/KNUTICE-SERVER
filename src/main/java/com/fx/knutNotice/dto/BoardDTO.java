package com.fx.knutNotice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long nttId;
    private Long boardNumber;
    private String title;
    private String contentURL;
    private String content;
    private String contentImage;
    private String departName;
    private String registrationDate;
}
