package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.MerchantAi;
import com.infosetgroup.amv2.repository.MerchantAiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantAiServiceImpl implements MerchantAiService {

    private MerchantAiRepository repository;

    @Autowired
    public MerchantAiServiceImpl(MerchantAiRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MerchantAi> listAll() {
        List<MerchantAi> merchants = new ArrayList<>();
        this.repository.findAll().forEach(merchants::add);
        return merchants;
    }

    @Override
    public Optional<MerchantAi> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public MerchantAi getByCode(String code) {
        return this.repository.findByCode(code);
    }

    @Override
    public MerchantAi saveOrUpdate(MerchantAi merchant) {
        this.repository.save(merchant);
        return merchant;
    }
}
