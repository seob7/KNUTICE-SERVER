package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.ScholarshipNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ScholarshipNewsRepository extends JpaRepository<ScholarshipNews, Long> {

    @Modifying
    @Query("UPDATE ScholarshipNews s SET s.newCheck = 'false' WHERE s.newCheck = 'true'")
    void updateNewCheckToFalse();

    void deleteByBoardNumber(Long boardNumber);

    @Query(value = "SELECT MIN(s.boardNumber) FROM ScholarshipNews s")
    Long findMinBoardNumber();

    @Query(value = "SELECT MAX(s.nttId) FROM ScholarshipNews s")
    Long findMaxNttId();
}
