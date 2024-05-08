package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.*;
import com.fx.knutNotice.domain.entity.BaseNews;
import com.fx.knutNotice.dto.BoardDTO;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseNewsService<T extends BaseNewsRepository> {

    private T repository;
    private static Long ACADEMIC_MAX_NTTID = 0L;
    private static Long GENERAL_MAX_NTTID = 0L;
    private static Long SCHOLARSHIP_MAX_NTTID = 0L;
    private static Long EVENT_MAX_NTTID = 0L;
    private static Long COMPARE_MAX_NTTID = -1L;
    public BaseNewsService(T repository) {
        this.repository = repository;
    }

    public List<String> newsCheck(List<BoardDTO> newList) {

        List<String> titleList = new ArrayList<>();


        /**
         * repository 인스턴스 체크.
         */
        checkRepositoryInstance();

        //기존 데이터 false로
        repository.updateNewCheckToFalse();

        int newCount = 0;//새로운 글 개수

        for (BoardDTO boardDTO : newList) {
            //DB의 nttId(최신글)보다 크롤링한 nttId가 큰 경우 신규게시글로 판단
            if (boardDTO.getNttId() > COMPARE_MAX_NTTID) {
                System.out.println(boardDTO.getTitle());
                BaseNews newEntity = createEntity(boardDTO);
                repository.save(newEntity);
                newCount++;

                // FCM발송을 위해 업데이트된 제목만 LIST에 추가.
                titleList.add(boardDTO.getTitle());
            }
        }

        COMPARE_MAX_NTTID = newList.get(0).getNttId();

        //boardNumber가 가장 작은 데이터를 업데이트된 개수만큼 글 삭제
        for (int i = 0; i < newCount; i++) {
            Long minBoardNumber = repository.findMinBoardNumber();
            repository.deleteByBoardNumber(minBoardNumber);
        }

        return titleList;
    }

    private Long findMaxNttId() {
        Long maxNttId = repository.findMaxNttId();
        System.out.println("max " + maxNttId);
        return maxNttId != null ? maxNttId : 0L;
    }

    private Long checkMaxNttId(final byte type) {
        switch (type) {
            case 0:
                if(ACADEMIC_MAX_NTTID == 0L) ACADEMIC_MAX_NTTID = findMaxNttId();
                return ACADEMIC_MAX_NTTID;
            case 1:
                if(GENERAL_MAX_NTTID == 0L) GENERAL_MAX_NTTID = findMaxNttId();
                return GENERAL_MAX_NTTID;
            case 2:
                if(EVENT_MAX_NTTID == 0L) EVENT_MAX_NTTID = findMaxNttId();
                return EVENT_MAX_NTTID;
            case 3:
                if(SCHOLARSHIP_MAX_NTTID == 0L) SCHOLARSHIP_MAX_NTTID = findMaxNttId();
                return SCHOLARSHIP_MAX_NTTID;
        }
        return -1L;
    }

    private void checkRepositoryInstance (){
        if(repository instanceof AcademicNewsRepository) {
            COMPARE_MAX_NTTID = checkMaxNttId((byte) 0);
        } else if( repository instanceof GeneralNewsRepository) {
            COMPARE_MAX_NTTID = checkMaxNttId((byte) 1);
        } else if( repository instanceof EventNewsRepository) {
            COMPARE_MAX_NTTID = checkMaxNttId((byte) 2);
        } else if(repository instanceof ScholarshipNewsRepository){
            COMPARE_MAX_NTTID = checkMaxNttId((byte) 3);
        }
    }

    protected abstract BaseNews createEntity(BoardDTO boardDTO);
}
