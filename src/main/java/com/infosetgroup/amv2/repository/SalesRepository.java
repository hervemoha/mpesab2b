package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Application;
import com.infosetgroup.amv2.entity.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SalesRepository extends PagingAndSortingRepository<Sales, Long> {
    Sales findByCode(String code);
    Page<Sales> findAllByTypeOrderByIdDesc(int type, Pageable pageable);

    @Query("SELECT p FROM Sales p WHERE CONCAT(p.reference, p.code, p.customerMsisdn) LIKE %?1%")
    Page<Sales> search(String keyword, Pageable pageable);
    List<Sales> findByTypeAndCodeValueAndApplication(int type, String codeValue, Application application);
}
