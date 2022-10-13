package com.infosetgroup.amv2.repository;

import com.infosetgroup.amv2.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LogRepository extends PagingAndSortingRepository<Log, Long> {
}
