package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.EventNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventNewsRepository extends JpaRepository<EventNews, Long> {

    @Modifying
    @Query("UPDATE EventNews e SET e.newCheck = 'false' WHERE e.newCheck = 'true'")
    void updateNewCheckToFalse();

    void deleteByBoardNumber(Long boardNumber);

    @Query(value = "SELECT MIN(e.boardNumber) FROM EventNews e")
    Long findMinBoardNumber();

}
