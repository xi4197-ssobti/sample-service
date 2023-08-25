package com.au.app.account.repository;

import com.au.app.account.domain.entity.MiniStatementStaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniStatementStagingRepository extends JpaRepository<MiniStatementStaging, String> {
}