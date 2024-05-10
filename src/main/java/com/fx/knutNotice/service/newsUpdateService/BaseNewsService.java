package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.*;
import com.fx.knutNotice.domain.entity.BaseNews;
import com.fx.knutNotice.dto.BoardDTO;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseNewsService<T extends BaseNewsRepository> {

    private T repository;
    private static Long ACADEMIC_MAX_NTT_ID = 0L;
    private static Long GENERAL_MAX_NTT_ID = 0L;
    private static Long SCHOLARSHIP_MAX_NTT_ID = 0L;
    private static Long EVENT_MAX_NTT_ID = 0L;
    private byte newCount = 0; // 2^8 = 256개
    private List<String> fcmTitles;

    public BaseNewsService(T repository) {
        this.repository = repository;
    }



    /**
     * 초기화 작업.
     */

    private void init() {
        repository.updateNewCheckToFalse();
        fcmTitles = new ArrayList<>();
    }


    /**
     * 새로운 게시글을 업데이트.
     */
    public List<String> newsCheck(final List<BoardDTO> newList) {
        init();
        transaction(newList);
        after();
        return fcmTitles;
    }

    /**
     * 저장과 추가에 대한 하나의 트랜잭션
     */
    private void transaction(final List<BoardDTO> newList) {
        final Long dbMaxNttID = getMaxNttId();
        for (final BoardDTO boardDTO : newList) {
            if (boardDTO.getNttId() > dbMaxNttID) {
                saveNewsEntity(boardDTO);
                addNewsTitle(boardDTO);
            }
        }
    }

    /**
     * for 문에서 참조로 전달한 crawling news 객체.
     */
    private void saveNewsEntity(final BoardDTO boardDTO) {

        BaseNews newEntity = createEntity(boardDTO);
        repository.save(newEntity);
        newCount++;
    }

    /**
     * FCM 용 title 추출.
     */
    private void addNewsTitle(final BoardDTO boardDTO) {
        fcmTitles.add(boardDTO.getTitle());
    }


    /**
     * 가장 오래된 news를 newCount(새로운 뉴스)개수 만큼 삭제.
     */
    private void deleteOldestNews() {
        for (int i = 0; i < newCount; i++) {
            final Long minBoardNumber = repository.findMinBoardNumber();
            repository.deleteByBoardNumber(minBoardNumber);
        }
    }



    /**
     * DB에서 maxNttId를 쿼리.
     * @return maxNttId
     */
    private Long findMaxNttId() {
        Long maxNttId = repository.findMaxNttId();
        return maxNttId != null ? maxNttId : 0L;
    }




    /**
     * DB에서 매번 가지고 오지 않고, 특정 뉴스의 nttID가 0L인 경우만 쿼리.
     * @return 뉴스 타입의 저장 nttId
     */
    private Long checkMaxNttId(final byte type) {
        switch (type) {
            case 0:
                if(ACADEMIC_MAX_NTT_ID == 0L) ACADEMIC_MAX_NTT_ID = findMaxNttId();
                return ACADEMIC_MAX_NTT_ID;
            case 1:
                if(GENERAL_MAX_NTT_ID == 0L) GENERAL_MAX_NTT_ID = findMaxNttId();
                return GENERAL_MAX_NTT_ID;
            case 2:
                if(EVENT_MAX_NTT_ID == 0L) EVENT_MAX_NTT_ID = findMaxNttId();
                return EVENT_MAX_NTT_ID;
            case 3:
                if(SCHOLARSHIP_MAX_NTT_ID == 0L) SCHOLARSHIP_MAX_NTT_ID = findMaxNttId();
                return SCHOLARSHIP_MAX_NTT_ID;
        }
        return -1L;
    }



    /**
     * 인스턴스 타입에 따라 가장 큰 nttId를 반환.
     * @return 특정 뉴스의 maxNttId
     */
    private Long getMaxNttId (){
        if(repository instanceof AcademicNewsRepository) {
            return checkMaxNttId((byte) 0);
        } else if( repository instanceof GeneralNewsRepository) {
            return checkMaxNttId((byte) 1);
        } else if( repository instanceof EventNewsRepository) {
            return checkMaxNttId((byte) 2);
        } else if(repository instanceof ScholarshipNewsRepository){
            return checkMaxNttId((byte) 3);
        }
        return 0L;
    }

    public void after () {
        deleteOldestNews();
    }


    /**
     * 상속 받는 클래스에서, 엔티티를 생성.
     */
    protected abstract BaseNews createEntity(BoardDTO boardDTO);
}