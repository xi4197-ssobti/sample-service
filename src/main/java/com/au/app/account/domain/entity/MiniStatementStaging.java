package com.au.app.account.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "mini_statement_esb_staging")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class MiniStatementStaging extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "account_id")
    private String accountID;

    @NotBlank
    @Column(name = "mini_statement_request", length = 10485760)
    private String miniStatementEsbRequest;

    @NotBlank
    @Column(name = "mini_statement_response", length = 10485760)
    private String miniStatementResponse;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        MiniStatementStaging that = (MiniStatementStaging) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
