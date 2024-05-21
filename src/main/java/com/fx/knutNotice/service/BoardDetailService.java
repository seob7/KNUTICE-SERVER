package com.fx.knutNotice.service;

import com.fx.knutNotice.common.Exception.BoardExceptionAdvice;
import com.fx.knutNotice.common.Exception.NotFountBoardException;
import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.domain.entity.BaseNews;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.dto.BoardDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardDetailService {

    private final GeneralNewsRepository generalNewsRepository;
    private final ScholarshipNewsRepository scholarshipNewsRepository;
    private final EventNewsRepository eventNewsRepository;
    private final AcademicNewsRepository academicNewsRepository;

    public BoardDTO showGeneralNewsDetail(Long nttId) {
        Optional<GeneralNews> generalNewsOptional = generalNewsRepository.findById(nttId);
        GeneralNews detail = generalNewsOptional.orElseThrow(NotFountBoardException::new);

        BoardDTO boardDTO = convertToBoardDTO(detail);

        return boardDTO;
    }

    public BoardDTO showScholarshipNewsDetail(Long nttId) { //장학안내
        Optional<ScholarshipNews> scholarshipNewsOptional = scholarshipNewsRepository.findById(nttId);
        ScholarshipNews detail = scholarshipNewsOptional.orElseThrow(NotFountBoardException::new);
        BoardDTO boardDTO = convertToBoardDTO(detail);

        return boardDTO;
    }

    public BoardDTO showEventNewsDetail(Long nttId) { //행사안내
        Optional<EventNews> eventNewsOptional = eventNewsRepository.findById(nttId);
        EventNews detail = eventNewsOptional.orElseThrow(NotFountBoardException::new);
        BoardDTO boardDTO = convertToBoardDTO(detail);

        return boardDTO;
    }

    public BoardDTO showAcademicNewsDetail(Long nttId) { //학사공지사항
        Optional<AcademicNews> academicNewsOptional = academicNewsRepository.findById(nttId);
        AcademicNews detail = academicNewsOptional.orElseThrow(NotFountBoardException::new);
        BoardDTO boardDTO = convertToBoardDTO(detail);

        return boardDTO;
    }



    private static BoardDTO convertToBoardDTO(BaseNews detail) {
        BoardDTO boardDTO = BoardDTO.builder()
            .nttId(detail.getNttId())
            .boardNumber(detail.getBoardNumber())
            .title(detail.getTitle())
            .contentURL(detail.getContentURL())
            .content(detail.getContent())
            .contentImage(detail.getContentImage())
            .departName(detail.getDepartName())
            .registrationDate(detail.getRegistrationDate())
            .build();
        return boardDTO;
    }

}
