package com.fx.knutNotice.service.newsUpdateService;

import com.fx.knutNotice.domain.BaseNewsRepository;
import com.fx.knutNotice.domain.EventNewsRepository;
import com.fx.knutNotice.domain.entity.EventNews;
import com.fx.knutNotice.dto.BoardDTO;
import org.springframework.stereotype.Service;

@Service
final public class EventNewsUpdateService extends BaseNewsService<BaseNewsRepository>{
    public EventNewsUpdateService(EventNewsRepository repository) {
        super(repository);
    }
    @Override
    protected EventNews createEntity(final BoardDTO boardDTO) {
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
        return newEntity;
    }
}

