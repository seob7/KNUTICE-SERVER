package com.fx.knutNotice;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestBoardData {

    @Autowired
    GeneralNewsRepository generalNewsRepository;
    @Autowired
    EventNewsRepository eventNewsRepository;
    @Autowired
    ScholarshipNewsRepository scholarshipNewsRepository;
    @Autowired
    AcademicNewsRepository academicNewsRepository;

    @Test
    void addBoardData() {

        long generalCount = generalNewsRepository.count();
        long eventCount = eventNewsRepository.count();
        long scholarshipCount = scholarshipNewsRepository.count();
        long academicCount = academicNewsRepository.count();

        if (generalCount != 100) {
            for (int i = 1; i <= 100; i++) {
                GeneralNews generalNews = GeneralNews.builder()
                    .nttId((long) i)
                    .boardNumber((long) i)
                    .title(i + "번 째 게시글 제목")
                    .contentURL(i + "번 째 URL")
                    .content(i + "번 째 글내용")
                    .newCheck("true")
                    .build();
                generalNewsRepository.save(generalNews);
            }
        }

        if (eventCount != 100) {
            for (int i = 1; i <= 100; i++) {
                EventNews eventNews = EventNews.builder()
                    .nttId((long) i)
                    .boardNumber((long) i)
                    .title(i + "번 째 게시글 제목")
                    .contentURL(i + "번 째 URL")
                    .content(i + "번 째 글내용")
                    .newCheck("true")
                    .build();
                eventNewsRepository.save(eventNews);
            }
        }

        if (scholarshipCount != 100) {
            for (int i = 1; i <= 100; i++) {
                ScholarshipNews scholarshipNews = ScholarshipNews.builder()
                    .nttId((long) i)
                    .boardNumber((long) i)
                    .title(i + "번 째 게시글 제목")
                    .contentURL(i + "번 째 URL")
                    .content(i + "번 째 글내용")
                    .newCheck("true")
                    .build();
                scholarshipNewsRepository.save(scholarshipNews);
            }
        }

        if (academicCount != 100) {
            for (int i = 1; i <= 100; i++) {
                AcademicNews academicNews = AcademicNews.builder()
                    .nttId((long) i)
                    .boardNumber((long) i)
                    .title(i + "번 째 게시글 제목")
                    .contentURL(i + "번 째 URL")
                    .content(i + "번 째 글내용")
                    .newCheck("true")
                    .build();
                academicNewsRepository.save(academicNews);
            }
        }
    }
}
