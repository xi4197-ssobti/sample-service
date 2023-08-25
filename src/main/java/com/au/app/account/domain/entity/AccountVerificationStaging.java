package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "account_verification_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class AccountVerificationStaging extends AuditAwareBaseEntity {

    @NonNull
    @Column(name = "account_number")
    private String accountNumber;

    @NotBlank
    @Column(name = "account_verification_request", length = 10485760)
    private String accountVerificationRequest;

    @NotBlank
    @Column(name = "account_verification_response", length = 10485760)
    private String accountVerificationResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountVerificationStaging that = (AccountVerificationStaging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
