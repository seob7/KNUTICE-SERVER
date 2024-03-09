package com.fx.knutNotice.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Board {

    private Long nttId;
    private Long boardNumber;
    private String title;
    private String dept;
    private LocalDate date;
    private Long boardViews;
    private String file;

}
