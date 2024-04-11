package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcademicNewsUpdateService {

    private final AcademicNewsRepository academicNewsRepository;

    public Set<Long> extractNttIdsFromAcademicNewsList(List<AcademicNews> academicNewsList) {
        return academicNewsList.stream()
                .map(AcademicNews::getNttId)
                .collect(Collectors.toSet());
    }

    public void newsCheck(List<BoardDTO> newList) {
        List<AcademicNews> oldList = academicNewsRepository.findAll();
        Set<Long> oldNttIds = extractNttIdsFromAcademicNewsList(oldList);

        //기존 데이터 false로
        academicNewsRepository.updateNewCheckToFalse();

        int newCount = 0;//새로운 글 개수

        //새로운 데이터 저장
        for (BoardDTO boardDTO : newList) {
            if (!oldNttIds.contains(boardDTO.getNttId())) {
                AcademicNews newEntity = AcademicNews.builder()
                        .nttId(boardDTO.getNttId())
                        .boardNumber(boardDTO.getBoardNumber())
                        .title(boardDTO.getTitle())
                        .newCheck("true")
                        .build();
                academicNewsRepository.save(newEntity);
                newCount++;
            }
        }

        //boardNumber가 가장 작은 데이터를 업데이트된 개수만큼 글 삭제
        for (int i = 0; i < newCount; i++) {
            Long minBoardNumber = academicNewsRepository.findMinBoardNumber();
            academicNewsRepository.deleteByBoardNumber(minBoardNumber);
        }
    }
}
