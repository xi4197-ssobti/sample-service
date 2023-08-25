package com.au.app.account.repository;

import com.au.app.account.domain.entity.PeriodicStatementStaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicStatementStagingRepository extends JpaRepository<PeriodicStatementStaging, String> {
}