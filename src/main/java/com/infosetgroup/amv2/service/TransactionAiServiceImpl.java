package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.TransactionAi;
import com.infosetgroup.amv2.repository.TransactionAiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionAiServiceImpl implements TransactionAiService {
    
    @Autowired
    TransactionAiRepository repository;

    @Override
    public List<TransactionAi> allSales(int type) {
        List<TransactionAi> sales = new ArrayList<>();
        //salesRepository.findAll().forEach(sales::add);
        for (TransactionAi sale:repository.findAll())
        {
            System.out.println(sale.getCode());
            if (sale.getType() == type)
                sales.add(sale);
        }

        /*
        salesRepository.findAll().forEach(sale ->{
            System.out.println(sale.getCode());
            if (sale.getType() == type)
                sales.add(sale);
        });
        */
        return sales;
    }

    @Override
    public List<TransactionAi> allSuccessSales(int type) {
        List<TransactionAi> sales = new ArrayList<>();
        for (TransactionAi sale:repository.findAll()){
            if(sale.isCompleted() && sale.getCodeValue().equalsIgnoreCase("200") && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public List<TransactionAi> allNoSuccessSales(int type) {
        List<TransactionAi> sales = new ArrayList<>();
        for (TransactionAi sale:repository.findAll()){
            if(sale.isCompleted() && !sale.getCodeValue().equalsIgnoreCase("200") && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public List<TransactionAi> allSalesWithoutNotification(int type) {
        List<TransactionAi> sales = new ArrayList<>();
        for (TransactionAi sale:repository.findAll()){
            if(!sale.isCompleted() && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public Optional<TransactionAi> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public TransactionAi getByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public TransactionAi saveOrUpdate(TransactionAi sales) {
        repository.save(sales);
        return sales;
    }

    @Override
    public Page<TransactionAi> getSalesByIdDesc(int p) {
        Page<TransactionAi> sales = repository.findAll(PageRequest.of(p, 200, Sort.by("id").descending()));
        return sales;
    }

    @Override
    public Page<TransactionAi> getSalesByType(int p, int type) {
        Page<TransactionAi> sales = repository.findAllByTypeOrderByIdDesc(type, PageRequest.of(p, 200));
        return sales;
    }

    @Override
    public Page<TransactionAi> search(String keyword, Pageable pageable) {
        return repository.search(keyword, pageable);
    }

    @Override
    public List<TransactionAi> allSalesWithoutNotificationWithStartedTime(int type, LocalDateTime now) {
        List<TransactionAi> sales = new ArrayList<>();
        for (TransactionAi sale:repository.findAllByTypeAndCompletedAndCreatedAfter(type, false, now)){
            sales.add(sale);
        }
        return sales;
    }
}
