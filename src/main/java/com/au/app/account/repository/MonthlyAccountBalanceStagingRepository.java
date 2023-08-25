package com.au.app.account.repository;

import com.au.app.account.domain.entity.MonthlyAccountBalanceStaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyAccountBalanceStagingRepository extends JpaRepository<MonthlyAccountBalanceStaging, String> {
}