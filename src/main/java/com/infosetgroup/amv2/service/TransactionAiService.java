package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.TransactionAi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionAiService {

    List<TransactionAi> allSales(int type);
    List<TransactionAi> allSuccessSales(int type);
    List<TransactionAi> allNoSuccessSales(int type);
    List<TransactionAi> allSalesWithoutNotification(int type);
    Optional<TransactionAi> getById(Long id);
    TransactionAi getByCode(String code);
    TransactionAi saveOrUpdate(TransactionAi sales);
    Page<TransactionAi> getSalesByIdDesc(int p);
    Page<TransactionAi> getSalesByType(int p, int type);
    Page<TransactionAi> search(String keyword, Pageable pageable);
    List<TransactionAi> allSalesWithoutNotificationWithStartedTime(int type, LocalDateTime dateTime);
}
