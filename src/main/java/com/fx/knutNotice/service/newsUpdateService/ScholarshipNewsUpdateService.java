package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipNewsUpdateService {

    private final ScholarshipNewsRepository scholarshipNewsRepository;

    public Set<Long> extractNttIdsFromScholarshipNewsList(
        List<ScholarshipNews> scholarshipNewsList) {
        return scholarshipNewsList.stream()
            .map(ScholarshipNews::getNttId)
            .collect(Collectors.toSet());
    }

    public void newsCheck(List<BoardDTO> newList) {
        List<ScholarshipNews> oldList = scholarshipNewsRepository.findAll();
        Set<Long> oldNttIds = extractNttIdsFromScholarshipNewsList(oldList);

        //기존 데이터 false로
        scholarshipNewsRepository.updateNewCheckToFalse();

        int newCount = 0;//새로운 글 개수

        //새로운 데이터 저장
        for (BoardDTO boardDTO : newList) {
            if (!oldNttIds.contains(boardDTO.getNttId())) {
                ScholarshipNews newEntity = ScholarshipNews.builder()
                    .nttId(boardDTO.getNttId())
                    .boardNumber(boardDTO.getBoardNumber())
                    .title(boardDTO.getTitle())
                    .contentURL(boardDTO.getContentURL())
                    .content(boardDTO.getContent())
                    .contentImage(boardDTO.getContentImage())
                    .departName(boardDTO.getDepartName())
                    .registrationDate(boardDTO.getRegistrationDate())
                    .newCheck("true")
                    .build();
                scholarshipNewsRepository.save(newEntity);
                newCount++;
            }
        }

        //boardNumber가 가장 작은 데이터를 업데이트된 개수만큼 글 삭제
        for (int i = 0; i < newCount; i++) {
            Long minBoardNumber = scholarshipNewsRepository.findMinBoardNumber();
            scholarshipNewsRepository.deleteByBoardNumber(minBoardNumber);
        }
    }
}
