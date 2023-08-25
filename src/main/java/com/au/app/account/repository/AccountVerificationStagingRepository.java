package com.au.app.account.repository;

import com.au.app.account.domain.entity.AccountVerificationStaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountVerificationStagingRepository extends JpaRepository<AccountVerificationStaging, String> {
}
