package com.infosetgroup.amv2.service;


import com.infosetgroup.amv2.entity.MerchantAi;

import java.util.List;
import java.util.Optional;

public interface MerchantAiService {
    List<MerchantAi> listAll();
    Optional<MerchantAi> getById(Long id);
    MerchantAi getByCode(String code);
    MerchantAi saveOrUpdate(MerchantAi merchant);
}
