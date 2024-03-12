package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.EventNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventNewsRepository extends JpaRepository<EventNews, Long> {
}
