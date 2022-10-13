package com.infosetgroup.amv2.service;


import com.infosetgroup.amv2.entity.Merchant;

import java.util.List;
import java.util.Optional;

public interface MerchantService {
    List<Merchant> all();
    List<Merchant> listAll(int type);
    Optional<Merchant> getById(Long id);
    Merchant getByCode(String code);
    Merchant getByShortCode(String shortCode);
    Merchant saveOrUpdate(Merchant merchant);
}
