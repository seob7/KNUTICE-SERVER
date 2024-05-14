package com.fx.knutNotice.domain.entity;

/**
 * Marker Interface.
 * 목적 : 다형성 구현용.
 */
public interface BaseNews {

    Long getNttId();
    Long getBoardNumber();
    String getTitle();
    String getContentURL();
    String getContent();
    String getContentImage();
    String getDepartName();
    String getRegistrationDate();
    String getNewCheck();
}
