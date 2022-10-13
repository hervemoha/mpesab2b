package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Application;
import com.infosetgroup.amv2.entity.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SalesService {

    List<Sales> allSales(int type);
    List<Sales> allSuccessSales(int type);
    List<Sales> allNoSuccessSales(int type);
    List<Sales> allSalesWithoutNotification(int type);
    Optional<Sales> getById(Long id);
    Sales getByCode(String code);
    Sales saveOrUpdate(Sales sales);
    void delete(Sales sales);
    Page<Sales> getSalesByIdDesc(int p);
    Page<Sales> getSalesByType(int p, int type);
    Page<Sales> search(String keyword, Pageable pageable);
    List<Sales> findByTypeAndCodeValueAndApplication(int type, String codeValue, Application application);

}
