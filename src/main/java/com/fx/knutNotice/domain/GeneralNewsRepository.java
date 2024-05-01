package com.fx.knutNotice.domain;

import com.fx.knutNotice.domain.entity.GeneralNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GeneralNewsRepository extends JpaRepository<GeneralNews, Long> {

    @Modifying
    @Query("UPDATE GeneralNews g SET g.newCheck = 'false' WHERE g.newCheck = 'true'")
    void updateNewCheckToFalse();

    void deleteByBoardNumber(Long boardNumber);

    @Query(value = "SELECT MIN(g.boardNumber) FROM GeneralNews g")
    Long findMinBoardNumber();

    @Query(value = "SELECT MAX(g.nttId) FROM GeneralNews g")
    Long findMaxNttId();
}
