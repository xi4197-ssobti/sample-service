package com.au.app.account.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AccountServiceConstants {

    public static final String CHANNEL = "AUMB";

    public static final String PAYMENT_METHOD_TYPE = "P2A";


    public static final String FLG_INTRA_BANK_ALLOWED = "N";

    public static final String INQUIRY_TYPE = "BNI";

    public static final String REMARK = "Validate Bank Account";


    public static final String ERROR_CODE = "99";


    public static final String ERROR_MESSAGE = "Failure";


    public static final String NOT_REQUIRED = "";
    public static final String MAB_MONTHLY = "Monthly";
    public static final String DEBIT_CARD_STATUS = "A";
    public static final String ACTIVE_CIF_STATUS = "N";
    public static final int MAX_ACCOUNT_ALLOWED = 3;

}
