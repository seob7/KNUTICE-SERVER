package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralNewsUpdateService {

    private final GeneralNewsRepository generalNewsRepository;

    public Set<Long> extractNttIdsFromGeneralNewsList(List<GeneralNews> generalNewsList) {
        return generalNewsList.stream()
                .map(GeneralNews::getNttId)
                .collect(Collectors.toSet());
    }

    public void newsCheck(List<BoardDTO> newList) {
        List<GeneralNews> oldList = generalNewsRepository.findAll();
        Set<Long> oldNttIds = extractNttIdsFromGeneralNewsList(oldList);

        //기존 데이터 false로
        generalNewsRepository.updateNewCheckToFalse();

        int newCount = 0;//새로운 글 개수

        //새로운 데이터 저장
        for (BoardDTO boardDTO : newList) {
            if (!oldNttIds.contains(boardDTO.getNttId())) {
                GeneralNews newEntity = GeneralNews.builder()
                        .nttId(boardDTO.getNttId())
                        .boardNumber(boardDTO.getBoardNumber())
                        .title(boardDTO.getTitle())
                        .newCheck("true")
                        .build();
                generalNewsRepository.save(newEntity);
                newCount++;
            }
        }

        //boardNumber가 가장 작은 데이터를 업데이트된 개수만큼 글 삭제
        for (int i = 0; i < newCount; i++) {
            Long minBoardNumber = generalNewsRepository.findMinBoardNumber();
            generalNewsRepository.deleteByBoardNumber(minBoardNumber);
        }
    }
}
