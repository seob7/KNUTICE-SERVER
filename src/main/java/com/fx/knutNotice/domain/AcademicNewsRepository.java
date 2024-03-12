package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.AcademicNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicNewsRepository  extends JpaRepository<AcademicNews, Long> {
}
