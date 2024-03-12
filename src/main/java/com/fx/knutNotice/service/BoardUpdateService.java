package com.fx.knutNotice.service;

import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
@Service
@RequiredArgsConstructor
@Slf4j
public class BoardUpdateService {


    private final GeneralNewsRepository generalNewsRepository;
    private static PriorityQueue<Long> oldBoardNttIdSet = new PriorityQueue<>();
    private static List<Long> newNttIdSet = new ArrayList<>();


    /**
     * PriorityQueue 를 사용해서, 기존 데이터베이스에 있던 board 가지고 온다고 가정합니다.
     * oldBoardNttIdSet은 항상 인덱스의 첫 번째 값은 가장 작은 nttId가 위치해 있으므로, 페이지 내 가장 끝 게시글의 nttId가 위치합니다.
     *
     * 새로운 게시글이 있는 경우에만, 끝에 있는 게시글을 삭제합니다.
     * 새로은 게시글이 없는 경우에는, 게시글을 삭제하지 않습니다.
     *
     * 따라서, newNttIdSet의 개수가 0보다 크다면, 새로운 게시글이 있으므로, 가장 끝에 있는 게시글의 nttId부터 하나씩 삭제합니다.
     *
     * 마지막으로, 삭제연산 후 새로운 게시글을 데이터베이스에 저장합니다.
     *
     * @param boardDTO
     */
    public void updateCheck(BoardDTO boardDTO) {
        HashMap<Long, GeneralNews> crawlingBoardList = new HashMap<>();
        List<GeneralNews> oldGeneralNewsList = generalNewsRepository.findAll();

        for (GeneralNews newGeneralNews : boardDTO.getGeneralNewsList())  {
            crawlingBoardList.put(newGeneralNews.getNttId(), newGeneralNews);
            newNttIdSet.add(newGeneralNews.getNttId());
        }

        for (GeneralNews olderGeneralNews : oldGeneralNewsList) {
            oldBoardNttIdSet.add(olderGeneralNews.getNttId());
        }

        // O(N)
        newNttIdSet.removeAll(oldBoardNttIdSet);

        if(newNttIdSet.size() > 0) {

            for (Long nttId : newNttIdSet) {
//                generalNewsRepository.deleteBoard(oldBoardNttIdSet.poll());
                generalNewsRepository.save(crawlingBoardList.get(nttId));
                oldBoardNttIdSet.clear();
            }
        }
    }
}
