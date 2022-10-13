package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Application;
import com.infosetgroup.amv2.entity.Sales;
import com.infosetgroup.amv2.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {

    private SalesRepository salesRepository;

    @Autowired
    public SalesServiceImpl(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Override
    public List<Sales> allSales(int type) {
        List<Sales> sales = new ArrayList<>();
        //salesRepository.findAll().forEach(sales::add);
        for (Sales sale:salesRepository.findAll())
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
    public List<Sales> allSuccessSales(int type) {
        List<Sales> sales = new ArrayList<>();
        for (Sales sale:salesRepository.findAll()){
            if(sale.isCompleted() && sale.getCodeValue().equalsIgnoreCase("0") && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public List<Sales> allNoSuccessSales(int type) {
        List<Sales> sales = new ArrayList<>();
        for (Sales sale:salesRepository.findAll()){
            if(sale.isCompleted() && !sale.getCodeValue().equalsIgnoreCase("0") && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public List<Sales> allSalesWithoutNotification(int type) {
        List<Sales> sales = new ArrayList<>();
        for (Sales sale:salesRepository.findAll()){
            if(!sale.isCompleted() && sale.getType() == type)
                sales.add(sale);
        }
        return sales;
    }

    @Override
    public Optional<Sales> getById(Long id) {
        return salesRepository.findById(id);
    }

    @Override
    public Sales getByCode(String code) {
        return salesRepository.findByCode(code);
    }

    @Override
    public Sales saveOrUpdate(Sales sales) {
        salesRepository.save(sales);
        return sales;
    }

    @Override
    public void delete(Sales sales) {
        salesRepository.delete(sales);
    }

    @Override
    public Page<Sales> getSalesByIdDesc(int p) {
        Page<Sales> sales = salesRepository.findAll(PageRequest.of(p, 200, Sort.by("id").descending()));
        return sales;
    }

    @Override
    public Page<Sales> getSalesByType(int p, int type) {
        Page<Sales> sales = salesRepository.findAllByTypeOrderByIdDesc(type, PageRequest.of(p, 200));
        return sales;
    }

    @Override
    public Page<Sales> search(String keyword, Pageable pageable) {
        return salesRepository.search(keyword, pageable);
    }

    @Override
    public List<Sales> findByTypeAndCodeValueAndApplication(int type, String codeValue, Application application) {
        return salesRepository.findByTypeAndCodeValueAndApplication(type, codeValue, application);
    }

}
