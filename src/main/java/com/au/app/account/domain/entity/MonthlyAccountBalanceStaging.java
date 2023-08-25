package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "monthly_account_balance_esb_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class MonthlyAccountBalanceStaging extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "account_no")
    private String accountNo;

    @NotBlank
    @Column(name = "monthly_account_balance_request", length = 10485760)
    private String monthlyAccountBalanceEsbRequest;

    @NotBlank
    @Column(name = "monthly_account_balance_response", length = 10485760)
    private String monthlyAccountBalanceResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MonthlyAccountBalanceStaging that = (MonthlyAccountBalanceStaging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
