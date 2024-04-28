package com.fx.knutNotice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventNews {


    @Id
    private Long nttId;
    private Long boardNumber;
    private String title;
    private String contentURL;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String newCheck;

}
