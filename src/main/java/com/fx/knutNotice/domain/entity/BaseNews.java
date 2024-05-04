package com.fx.knutNotice.domain.entity;

/**
 * 마커 인터페이스. BaseNews 다형성 구현용.
 *
 * 1. Builder 패턴 적용 문제로 인해 marker interface 설정.
 * 2. abstract class에 공통 필드들을 전부 담아서 시도했으나, Builder 패턴 + Lombok 어노테이션에서 부모 Getter의 접근이 안되는 문제 때문에 marker interface 설정.
 */
public interface BaseNews { }
