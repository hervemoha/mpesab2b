package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.MerchantAi;
import org.springframework.data.repository.CrudRepository;

public interface MerchantAiRepository extends CrudRepository<MerchantAi, Long> {
        MerchantAi findByCode(String code);
}
