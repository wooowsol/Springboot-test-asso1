package org.zerock.asso1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.asso1.domain.ReBoard;

public interface ReBoardRepository extends JpaRepository<ReBoard, Long>{

    @Query("select b, b.writer from ReBoard b inner join b.writer order by b desc")
    List<ReBoard> getList();

    @Query(
        value="select b, b.writer from ReBoard b inner join b.writer order by b desc", 
        countQuery = "select count(b) from ReBoard b")
    Page<Object[]> getListPage(Pageable pageable);

    @Query(
        value="select b, count(r) from ReBoard b left outer join Reply r on r.board = b group by b", 
        countQuery = "select count(b) from ReBoard b")
    Page<Object[]> getListWithReply(Pageable pageble);
    
}