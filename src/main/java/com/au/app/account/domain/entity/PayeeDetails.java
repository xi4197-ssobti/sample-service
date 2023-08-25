package com.au.app.account.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "payee_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "builder")
public class PayeeDetails extends AuditAwareBaseEntity {

    @NotBlank
    @Column(name = "payee_Name")
    private String payeeName;
    @NotBlank
    @Column(name = "customer_id")
    private String customerId;
    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "email_Id")
    private String emailId;
    @Column(name = "upi_id")
    private String upiID;
    @Column(name = "billing_name")
    private String billingName;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "pin_code")
    private String pinCode;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "shipping_name")
    private String shippingName;
    @Column(name = "shipping_address_line1")
    private String shippingAddressLine1;
    @Column(name = "shipping_address_line2")
    private String shippingAddressLine2;
    @Column(name = "shipping_pin_code")
    private String shippingPinCode;
    @Column(name = "shipping_city")
    private String shippingCity;
    @Column(name = "shipping_state")
    private String shippingState;
    @Column(name = "address_flag")
    private Boolean addressFlag;
    @Column(name = "pan_number")
    private String panNumber;
    @Column(name = "tan_number")
    private String tanNumber;
    @Column(name = "gst_number")
    private String gstNumber;
    @OneToMany(mappedBy = "payeeDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BankDetails> bankDetails;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || Hibernate.getClass(this) != Hibernate.getClass(object)) {
            return false;
        }
        PayeeDetails that = (PayeeDetails) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}

