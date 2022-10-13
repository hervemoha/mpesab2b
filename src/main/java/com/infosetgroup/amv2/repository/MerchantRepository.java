package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Merchant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {

    Merchant findByCode(String code);
    Merchant findByShortCode(String shortCode);
    List<Merchant> findByOrderByCreatedDesc();
}
