package com.fx.knutNotice.config;

public enum KnutURL {

    GENERAL_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardList.do"), //일반소식
    SCHOLARSHIP_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardList.do"), //장학안내
    EVENT_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardList.do"), //행사안내
    ACADEMIC_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardList.do"); //학사공지사항

    private final String url;

    KnutURL(String url) {
        this.url = url;
    }

    public String URL() {
        return this.url;
    }
}
