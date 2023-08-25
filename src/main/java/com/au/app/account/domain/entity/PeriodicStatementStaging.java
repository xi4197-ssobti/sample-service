package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "periodic_statement_esb_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class PeriodicStatementStaging extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "account_id")
    private String accountID;

    @NotBlank
    @Column(name = "transaction_count")
    private String transactionCount;

    @Column(name = "transaction_date")
    private String transactionDate;

    @NotBlank
    @Column(name = "periodic_statement_request", length = 10485760)
    private String periodicStatementEsbRequest;

    @NotBlank
    @Column(name = "periodic_statement_response", length = 10485760)
    private String periodicStatementResponse;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PeriodicStatementStaging that = (PeriodicStatementStaging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
