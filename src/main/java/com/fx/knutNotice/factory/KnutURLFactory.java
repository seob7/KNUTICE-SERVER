package com.fx.knutNotice.factory;


import com.fx.knutNotice.common.KnutURL;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class KnutURLFactory {
    public static List<String> getBoardURL(byte type) {
        switch (type) {
            case 0:
                return List.of(KnutURL.ACADEMIC_NEWS.boardURL(), KnutURL.ACADEMIC_NEWS.articleURL());
            case 1:
                return List.of(KnutURL.GENERAL_NEWS.boardURL(), KnutURL.GENERAL_NEWS.articleURL());
            case 2:
                return List.of(KnutURL.EVENT_NEWS.boardURL(), KnutURL.EVENT_NEWS.articleURL());
            case 3:
                return List.of(KnutURL.SCHOLARSHIP_NEWS.boardURL(), KnutURL.SCHOLARSHIP_NEWS.articleURL());
            default:
                throw new IllegalArgumentException("Not valid type");
        }
    }

}
