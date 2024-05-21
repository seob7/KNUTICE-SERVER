package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.BaseNewsRepository;
import com.fx.knutNotice.domain.ScholarshipNewsRepository;
import com.fx.knutNotice.domain.entity.ScholarshipNews;
import com.fx.knutNotice.dto.BoardDTO;
import org.springframework.stereotype.Service;

@Service
final public class ScholarshipNewsUpdateService extends BaseNewsService<BaseNewsRepository> {
    public ScholarshipNewsUpdateService(ScholarshipNewsRepository repository) {
        super(repository);
    }

    @Override
    protected ScholarshipNews createEntity(final BoardDTO boardDTO) {
        ScholarshipNews newEntity = ScholarshipNews.builder()
                .nttId(boardDTO.getNttId())
                .boardNumber(boardDTO.getBoardNumber())
                .title(boardDTO.getTitle())
                .contentURL(boardDTO.getContentURL())
                .content(boardDTO.getContent())
                .contentImage(boardDTO.getContentImage())
                .departName(boardDTO.getDepartName())
                .registrationDate(boardDTO.getRegistrationDate())
                .build();
        return newEntity;
    }
}
