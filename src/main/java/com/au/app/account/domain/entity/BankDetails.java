package com.au.app.account.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "builder")
@Where(clause = "activation_flag = 'true'")
public class BankDetails extends AuditAwareBaseEntity {
    @Column(name = "account_holder_name")
    private String accountHolderName;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "bank_display_name")
    private String bankDisplayName;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "ifsc_code")
    private String ifscCode;
    @Column(name = "verification_flag")
    private Boolean verificationFlag;
    @Column(name = "activation_flag")
    private Boolean activationFlag;
    @Column(name = "is_rtgs_enabled")
    private Boolean isRtgsEnabled;
    @Column(name = "is_neft_enabled")
    private Boolean isNeftEnabled;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payee_id", referencedColumnName = "id")
    private PayeeDetails payeeDetails;


}


