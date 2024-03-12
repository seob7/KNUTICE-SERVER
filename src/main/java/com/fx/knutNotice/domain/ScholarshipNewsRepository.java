package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.ScholarshipNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScholarshipNewsRepository extends JpaRepository<ScholarshipNews, Long> {
}
