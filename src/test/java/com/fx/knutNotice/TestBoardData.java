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

        for (int i = 0; i < 10; i++) {
            GeneralNews generalNews = GeneralNews.builder()
                .nttId((long) i)
                .boardNumber((long) i)
                .title( i + "번 째 게시글 제목")
                .contentURL( i + "번 째 URL")
                .content( i + "번 째 글내용")
                .newCheck("true")
                .build();
            generalNewsRepository.save(generalNews);

            EventNews eventNews = EventNews.builder()
                .nttId((long) i)
                .boardNumber((long) i)
                .title( i + "번 째 게시글 제목")
                .contentURL( i + "번 째 URL")
                .content( i + "번 째 글내용")
                .newCheck("true")
                .build();
            eventNewsRepository.save(eventNews);

            ScholarshipNews scholarshipNews = ScholarshipNews.builder()
                .nttId((long) i)
                .boardNumber((long) i)
                .title( i + "번 째 게시글 제목")
                .contentURL( i + "번 째 URL")
                .content( i + "번 째 글내용")
                .newCheck("true")
                .build();
            scholarshipNewsRepository.save(scholarshipNews);

            AcademicNews academicNews = AcademicNews.builder()
                .nttId((long) i)
                .boardNumber((long) i)
                .title( i + "번 째 게시글 제목")
                .contentURL( i + "번 째 URL")
                .content( i + "번 째 글내용")
                .newCheck("true")
                .build();
            academicNewsRepository.save(academicNews);
        }
    }


}
