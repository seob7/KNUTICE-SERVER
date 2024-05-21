package com.fx.knutNotice.service.newsUpdateService;
import com.fx.knutNotice.domain.AcademicNewsRepository;
import com.fx.knutNotice.domain.BaseNewsRepository;
import com.fx.knutNotice.domain.entity.AcademicNews;
import com.fx.knutNotice.dto.BoardDTO;
import org.springframework.stereotype.Service;

@Service
final public class AcademicNewsUpdateService extends BaseNewsService<BaseNewsRepository> {
    public AcademicNewsUpdateService(AcademicNewsRepository repository) {
        super(repository);
    }

    @Override
    protected AcademicNews createEntity(final BoardDTO boardDTO) {
        AcademicNews newEntity = AcademicNews.builder()
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
