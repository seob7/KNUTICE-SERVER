package com.fx.knutNotice.config;

public enum KnutURL {

    GENERAL_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardList.do",
            "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000059/selectBoardArticle.do?bbsId=BBSMSTR_000000000059",
            "BBSMSTR_000000000059"), //일반소식
    SCHOLARSHIP_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardList.do",
            "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000060/selectBoardArticle.do",
            "BBSMSTR_000000000060"), //장학안내
    EVENT_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardList.do",
            "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000061/selectBoardArticle.do",
            "BBSMSTR_000000000061"), //행사안내
    ACADEMIC_NEWS("https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardList.do",
            "https://www.ut.ac.kr/cop/bbs/BBSMSTR_000000000055/selectBoardArticle.do",
            "BBSMSTR_000000000055"); //학사공지사항

    private final String url;
    private final String articleURL;
    private final String bbsId;

    KnutURL(String url, String articleURL, String bbsId) {
        this.url = url;
        this.articleURL = articleURL;
        this.bbsId = bbsId;
    }

    public String URL() {
        return this.url;
    }

    public String bbsId() {
        return this.bbsId;
    }

    public String articleURL() {
        return this.articleURL;
    }
}
