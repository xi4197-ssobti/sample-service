package com.au.app.account.repository;

import com.au.app.account.domain.entity.DebitCardDetailsStaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardDetailsRepository extends JpaRepository<DebitCardDetailsStaging, String> {
}