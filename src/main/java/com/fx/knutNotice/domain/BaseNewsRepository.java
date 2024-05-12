package com.fx.knutNotice.domain;

import com.fx.knutNotice.dto.NewsListDTO;
import com.fx.knutNotice.dto.RecentThreeTitleDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value ="SELECT a.nttId as nttId, a.title as title, a.departName as departName, a.registrationDate as registrationDate"
        + " FROM #{#entityName} a ORDER BY a.nttId DESC LIMIT 3")
    List<RecentThreeTitleDTO> findRecent3Title();

    @Query(value ="SELECT a.nttId as nttId, a.title as title, a.departName as departName, a.registrationDate as registrationDate"
        + " FROM #{#entityName} a")
    Page<NewsListDTO> findPaginationList(Pageable pageable);
}
