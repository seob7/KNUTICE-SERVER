package com.fx.knutNotice.factory;

import com.fx.knutNotice.common.KnutURL;
import com.fx.knutNotice.service.newsUpdateService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ServiceFactory {
    private final AcademicNewsUpdateService academicNewsUpdateService;
    private final GeneralNewsUpdateService generalNewsUpdateService;
    private final EventNewsUpdateService eventNewsUpdateService;
    private final ScholarshipNewsUpdateService scholarshipNewsUpdateService;
    public BaseNewsService getService(final byte type) {
        switch (type) {
            case 0:
                return generalNewsUpdateService;
            case 1:
                return scholarshipNewsUpdateService;
            case 2:
                return eventNewsUpdateService;
            case 3:
                return academicNewsUpdateService;
            default:
                throw new IllegalArgumentException("Not valid type");
        }
    }
}
