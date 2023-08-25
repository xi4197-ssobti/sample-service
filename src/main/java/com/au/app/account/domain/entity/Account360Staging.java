package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "account_360_integration_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class Account360Staging extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "account_number")
    private String accountNo;
    @NotBlank
    @Column(name = "account_360_request", length = 10485760)
    private String accountEsbRequest;
    @NotBlank
    @Column(name = "account_360_response", length = 10485760)
    private String accountEsbResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Account360Staging that = (Account360Staging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
