package pl.nask.crs.commons.email.service;

import java.util.ArrayList;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public enum ParameterNameEnum implements ParameterName {

    DOMAIN("DOMAIN"),
    ADMIN_C_EMAIL("ADMIN-C_EMAIL"),
    BILL_C_NIC("BILL-C_NIC"),
    CREATOR_C_EMAIL("CREATOR-C_EMAIL"),
    REMARK("REMARK"),
    HOLDER("HOLDER"),
    HOLDER_OLD("HOLDER_OLD"),
    DOMAIN_CLASS("DOMAIN_CLASS"),
    DOMAIN_CLASS_OLD("DOMAIN_CLASS_OLD"),
    DOMAIN_CATEGORY("DOMAIN_CATEGORY"),
    DOMAIN_CATEGORY_OLD("DOMAIN_CATEGORY_OLD"),
    LOSING_BILL_C_EMAIL("LOSING_BILL-C_EMAIL"),
    DAYS_TO_RENEWAL("DAYS_TO_RENEWAL"),
    GAINING_BILL_C_EMAIL("GAINING_BILL-C_EMAIL"),
    POSTMASTER("POSTMASTER"),
    WEBMASTER("WEBMASTER"),
    INFO("INFO"),
    REGISTRATION_DATE("REGISTRATION_DATE"),
    RENEWAL_DATE("RENEWAL_DATE"),
    SUSPENSION_DATE("SUSPENSION_DATE"),
    DELETION_DATE("DELETION_DATE"),
    AUTHCODE("AUTHCODE"),
    AUTHCODE_EXP_DATE("AUTHCODE_EXP_DATE"),
    PASSWORD_TOKEN("PASSWORD_TOKEN"),
    BILL_C_NAME("BILL-C_NAME"),
    BILL_C_EMAIL("BILL-C_EMAIL"),
    TRANSACTION_VALUE("TRANSACTION_VALUE"),
    ORDER_ID("ORDER_ID"),
    TRANSACTION_DETAIL("TRANSACTION_DETAIL"),
    INVOICE_DATE("INVOICE_DATE"),
    DATE("DATE"),
    ADMIN_C_NAME("ADMIN-C_NAME"),
    ADMIN_C_NIC("ADMIN-C_NIC"),
    ADMIN_C_NIC_OLD("ADMIN-C_NIC_OLD"),
    BILL_C_TEL("BILL-C_TEL"),
    LOSING_BILL_C_NAME("LOSING_BILL-C_NAME"),
    GAINING_BILL_C_NAME("GAINING_BILL-C_NAME"),
    GAINING_BILL_C_NIC("GAINING_BILL-C_NIC"),
    VAT_TABLE_CHANGE_DETAIL("VAT_TABLE_CHANGE_DETAIL"),
    TICKET_ID("TICKET_ID"),
    TICKET_DAYS_REMAINING("TICKET_DAYS_REMAINING"),
    TICKET_CREATION_DATE("TICKET_CREATION_DATE"),
    DNS1("DNS1"),
    DNS2("DNS2"),
    REGISTRAR_NAME("REGISTRAR_NAME"),
    PRODUCT_TABLE("PRODUCT_TABLE"),
    PRODUCT_VALID_FROM("PRODUCT_VALID_FROM"),
    BILL_C_EMAIL_NEW("BILL-C_EMAIL_NEW"),
    BILL_C_EMAIL_OLD("BILL-C_EMAIL_OLD"),
    BILL_C_ADDRESS_NEW("BILL-C_ADDRESS_NEW"),
    BILL_C_ADDRESS_OLD("BILL-C_ADDRESS_OLD"),
    BILL_C_NIC_OLD("BILL-C_NIC_OLD"),
    DNS_FAILURES("DNS_FAILURES"),

    NIC("NIC"),
    NIC_NAME("NIC_NAME"),
    NIC_EMAIL("NIC_EMAIL"),
    TECH_C_NIC("TECH-C_NIC"),
    TECH_C_NIC_OLD("TECH-C_NIC_OLD"),
    TECH_C_NAME("TECH-C_NAME"),
    TECH_C_EMAIL("TECH-C_EMAIL"),
    SOURCE("SOURCE"),
    TICKET_TYPE("TICKET_TYPE"), 
    CREATOR_C_NAME("CREATOR-C_NAME"), 
    DNS_LIST_OLD("DNS_LIST_OLD"),
    DNS_LIST_NEW("DNS_LIST_NEW"), 
    DSM_EVENT_NAME("DSM_EVENT_NAME"),
    BILL_C_CO_NAME("BILL-C_CO_NAME"), 
    SCHEDULER_JOB_NAME("SCHEDULER_JOB_NAME"), 
    SCHEDULER_JOB_ERRORS("SCHEDULER_JOB_ERRORS"), 
    DEPOSIT_ACCOUNT_CORRECTION_DESCRIPTION("DEPOSIT_ACCOUNT_CORRECTION_DESCRIPTION"),
    EMAILS_LIST("EMAILS_LIST"),
    DOMAIN_LIST("DOMAIN_LIST"),
    DOMAIN_TABLE_WITH_AUTHCODES("DOMAIN_TABLE_WITH_AUTHCODES")
    ;

    private String name;

    private ParameterNameEnum(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static ParameterNameEnum forName(String name) {
        for (ParameterNameEnum param : ParameterNameEnum.values()) {
            if (param.getName().equals(name)) {
                return param;
            }
        }
        throw new IllegalArgumentException("Invalid parameter name:" + name);
    }

    public static List<ParameterName> asList() {
        List<ParameterName> paramList = new ArrayList<ParameterName>();
        for (ParameterNameEnum param : ParameterNameEnum.values()) {
            paramList.add(param);
        }
        return paramList;
    }
}

