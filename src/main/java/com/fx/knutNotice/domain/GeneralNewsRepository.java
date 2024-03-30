package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.GeneralNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralNewsRepository extends JpaRepository<GeneralNews, Long> {

}
