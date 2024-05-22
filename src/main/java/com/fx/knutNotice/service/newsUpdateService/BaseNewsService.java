package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.*;
import com.fx.knutNotice.domain.entity.BaseNews;
import com.fx.knutNotice.dto.BoardDTO;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseNewsService<T extends BaseNewsRepository> {

    private final T repository;
    private static Long ACADEMIC_MAX_NTT_ID = 0L;
    private static Long GENERAL_MAX_NTT_ID = 0L;
    private static Long SCHOLARSHIP_MAX_NTT_ID = 0L;
    private static Long EVENT_MAX_NTT_ID = 0L;
    private byte newCount = 0; // 2^8 = 256개
    private List<String> fcmTitles;

    public BaseNewsService(final T repository) {
        this.repository = repository;
    }


    /**
     * 초기화 작업.
     */


    private void initializeData() {
        fcmTitles = new ArrayList<>();
    }


    /**
     * 새로운 게시글을 업데이트.
     */
    public BaseNewsService updateNews(final List<BoardDTO> newsList, final byte type) {
        initializeData();
        updateNewsTransaction(newsList, type);
        deleteOldestNews();
        return this;
    }

    public List<String> getUpdateTitles() {
        return fcmTitles;
    }
    /**
     * 저장과 추가에 대한 하나의 트랜잭션
     *
     * 크롤링할때
     * delete nttid 아이디로 지워지고 있는것.
     */
    private void updateNewsTransaction(final List<BoardDTO> newsList, final byte type) {
        final Long dbMaxNttID = getMaxNttId();
        for (final BoardDTO boardDTO : newsList) {
            if (boardDTO.getNttId() > dbMaxNttID) {
                saveNewsEntity(boardDTO);
                addNewsTitle(boardDTO);
            }
        }

        /**
         * newsList가 isNotEmpty()인게 보장이 되므로, 따로 체크가 필요 없음.
         */
        changeMaxNttId(newsList.get(0).getNttId(), type);
        
    }

    private void changeMaxNttId(Long newNttId, byte type) {
        switch (type) {
            case 0:
                ACADEMIC_MAX_NTT_ID = newNttId;
                break;
            case 1:
                GENERAL_MAX_NTT_ID = newNttId;
                break;
            case 2:
                EVENT_MAX_NTT_ID = newNttId;
                break;
            case 3:
                SCHOLARSHIP_MAX_NTT_ID = newNttId;
                break;
        }
    }


    /**
     * for 문에서 참조로 전달한 crawling news 객체.
     */
    private void saveNewsEntity(final BoardDTO boardDTO) {
        repository.save(createEntity(boardDTO)); newCount++;
    }

    /**
     * FCM 용 title 추출.
     */
    private void addNewsTitle(final BoardDTO boardDTO) {
        fcmTitles.add(boardDTO.getTitle());
    }


    /**
     * 가장 오래된 news를 newCount(새로운 뉴스)개수 만큼 삭제.
     * 저장을 하고 -> 지운다.
     */
    private void deleteOldNews() {
        // 업데이트가 진행되는 뉴스만큼. minboardnumber를 지우는데
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
        } else if( repository instanceof ScholarshipNewsRepository){
            return checkMaxNttId((byte) 3);
        }
        return 0L;
    }

    public void deleteOldestNews() {
        deleteOldNews();
    }
    


    /**
     * 상속 받는 클래스에서, 엔티티를 생성.
     */
    protected abstract BaseNews createEntity(BoardDTO boardDTO);
}
