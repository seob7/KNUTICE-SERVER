package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.AcademicNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AcademicNewsRepository  extends JpaRepository<AcademicNews, Long> {

    @Modifying
    @Query("UPDATE AcademicNews a SET a.newCheck = 'false' WHERE a.newCheck = 'true'")
    void updateNewCheckToFalse();

    void deleteByBoardNumber(Long boardNumber);

    @Query(value = "SELECT MIN(a.boardNumber) FROM AcademicNews a")
    Long findMinBoardNumber();

    @Query(value = "SELECT MAX(a.nttId) FROM AcademicNews a")
    Long findMaxNttId();
}
