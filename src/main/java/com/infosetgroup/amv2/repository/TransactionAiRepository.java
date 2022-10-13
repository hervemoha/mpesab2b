package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.TransactionAi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionAiRepository extends PagingAndSortingRepository<TransactionAi, Long> {

    TransactionAi findByCode(String code);
    Page<TransactionAi> findAllByTypeOrderByIdDesc(int type, Pageable pageable);
    @Query("SELECT p FROM TransactionAi p WHERE CONCAT(p.reference, p.code, p.customerMsisdn) LIKE %?1%")
    Page<TransactionAi> search(String keyword, Pageable pageable);
    List<TransactionAi> findAllByTypeAndCompletedAndCreatedAfter(int type, boolean completed, LocalDateTime dateTime);
}
