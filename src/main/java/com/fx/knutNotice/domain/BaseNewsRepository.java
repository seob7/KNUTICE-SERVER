package com.fx.knutNotice.domain;

import java.util.List;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * T : Entity Class
 * ID : Primary key type
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseNewsRepository<T, ID> extends JpaRepository<T, ID> {
    @Modifying
    @Query("UPDATE #{#entityName} a SET a.newCheck = 'false' WHERE a.newCheck = 'true'")
    void updateNewCheckToFalse();

    void deleteByBoardNumber(Long boardNumber);

    @Query(value = "SELECT MIN(a.boardNumber) FROM #{#entityName} a")
    Long findMinBoardNumber();

    @Query(value = "SELECT MAX(a.nttId) FROM #{#entityName} a")
    Long findMaxNttId();

    @Query(value ="SELECT a.title FROM #{#entityName} a ORDER BY a.nttId DESC LIMIT 3")
    List<String> findRecent3Title();
}
