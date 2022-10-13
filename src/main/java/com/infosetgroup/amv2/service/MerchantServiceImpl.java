package com.infosetgroup.amv2.service;

import com.infosetgroup.amv2.entity.Merchant;
import com.infosetgroup.amv2.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService{

    private MerchantRepository merchantRepository;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> all() {

        List<Merchant> merchants = new ArrayList<>();
        this.merchantRepository.findByOrderByCreatedDesc().forEach(merchants::add);
        return merchants;
    }

    @Override
    public List<Merchant> listAll(int type) {
        List<Merchant> merchants = new ArrayList<>();
        //this.merchantRepository.findAll().forEach(merchants::add);
        this.merchantRepository.findByOrderByCreatedDesc().forEach(merchant -> {
            if (merchant.getType() == type)
                merchants.add(merchant);
        });
        return merchants;
    }

    @Override
    public Optional<Merchant> getById(Long id) {
        return this.merchantRepository.findById(id);
    }

    @Override
    public Merchant getByCode(String code) {
        return this.merchantRepository.findByCode(code);
    }

    @Override
    public Merchant getByShortCode(String shortCode) {
        return this.merchantRepository.findByShortCode(shortCode);
    }

    @Override
    public Merchant saveOrUpdate(Merchant merchant) {
        this.merchantRepository.save(merchant);
        return merchant;
    }
}
