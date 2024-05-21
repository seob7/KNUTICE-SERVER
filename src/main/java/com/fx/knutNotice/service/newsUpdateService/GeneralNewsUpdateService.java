package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.BaseNewsRepository;
import com.fx.knutNotice.domain.GeneralNewsRepository;
import com.fx.knutNotice.domain.entity.GeneralNews;
import com.fx.knutNotice.dto.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
final public class GeneralNewsUpdateService extends BaseNewsService<BaseNewsRepository> {
    public GeneralNewsUpdateService(GeneralNewsRepository repository) {
        super(repository);
    }

    @Override
    protected GeneralNews createEntity(final BoardDTO boardDTO) {
        GeneralNews newEntity = GeneralNews.builder()
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
