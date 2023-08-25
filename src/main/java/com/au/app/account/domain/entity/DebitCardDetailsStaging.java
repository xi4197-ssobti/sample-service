package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "debit_card_details_esb_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class DebitCardDetailsStaging extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "customer_id")
    private String customerId;

    @NotBlank
    @Column(name = "debit_card_details_request", length = 10485760)
    private String debitCardDetailsEsbRequest;

    @NotBlank
    @Column(name = "debit_card_details_response", length = 10485760)
    private String debitCardDetailsEsbResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DebitCardDetailsStaging that = (DebitCardDetailsStaging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
