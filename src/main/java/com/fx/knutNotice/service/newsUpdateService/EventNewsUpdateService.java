package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventNewsUpdateService {

    private final EventNewsRepository eventNewsRepository;

    public Set<Long> extractNttIdsFromEventNewsList(List<EventNews> eventNewsList) {
        return eventNewsList.stream()
            .map(EventNews::getNttId)
            .collect(Collectors.toSet());
    }

    public void newsCheck(List<BoardDTO> newList) {
        List<EventNews> oldList = eventNewsRepository.findAll();
        Set<Long> oldNttIds = extractNttIdsFromEventNewsList(oldList);

        //기존 데이터 false로
        eventNewsRepository.updateNewCheckToFalse();

        int newCount = 0;//새로운 글 개수

        //새로운 데이터 저장
        for (BoardDTO boardDTO : newList) {
            if (!oldNttIds.contains(boardDTO.getNttId())) {
                EventNews newEntity = EventNews.builder()
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
                eventNewsRepository.save(newEntity);
                newCount++;
            }
        }

        //boardNumber가 가장 작은 데이터를 업데이트된 개수만큼 글 삭제
        for (int i = 0; i < newCount; i++) {
            Long minBoardNumber = eventNewsRepository.findMinBoardNumber();
            eventNewsRepository.deleteByBoardNumber(minBoardNumber);
        }
    }
}
