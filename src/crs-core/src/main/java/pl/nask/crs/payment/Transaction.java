package pl.nask.crs.payment;

import java.util.Date;
import java.util.List;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class Transaction {

    private final long id;
    private final String billNicHandleId;
    private Integer invoiceId;
    private String invoiceNumber;
    private boolean settlementStarted;
    private boolean settlementEnded;
    private final Integer totalCost;
    private final Integer totalNetAmount;
    private final Integer totalVatAmount;
    private boolean cancelled;
    private Date cancelledDate;
    private Date financiallyPassedDate;
    private List<Reservation> reservations;
    private boolean invalidated;
    private Date invalidatedDate;
    private Long reauthorisedId;

    private final Date settlementDate;
    private final PaymentMethod paymentMethod;
    private final OperationType operationType;
    
    private String realexAuthCode;
    private String realexPassRef;
/*
 * A string Formatted as: yyyymmddhhmmss-X-yyyyyyy where:
 * yyyymmddhhmmss is the string representation of the timestamp of when the transaction is created (and will therefore be the same as the timestamp of the RealEx pre-authorisation, for credit card payments)
 * X is either 'D' for deposit account transactions, or 'C' for credit / debit card transactions
 * yyyyyyy is a unique integer, padded to seven digits, which is used to ensure that no two order ids are identical
 * 
 * E.g.: 20120417140458-C-0007239
 * 
 * This Order ID  is to be used on invoices (under the column Payment Ref) - not the transaction id or the transaction 'financiallyPassed' timestamp.
 * This Order ID is also to be used as the order id for all RealEx transactions.
 */    
    private final String orderId;

    public static Transaction newInstance(Integer totalCost, Integer totalNetAmount, Integer totalVatAmount, String orderId, Long reauthorisedId) {
        return new Transaction(-1, null, null, null, orderId, false, false, totalCost, totalNetAmount, totalVatAmount, false, null, null, null, false, null, reauthorisedId, null, null, null, null, null);
    }
    
    public static Transaction newInstance(Integer totalCost, Integer totalNetAmount, Integer totalVatAmount, String orderId, Long reauthorisedId, String realexAuthCode, String realexPassref) {
        return new Transaction(-1, null, null, null, orderId, false, false, totalCost, totalNetAmount, totalVatAmount, false, null, null, null, false, null, reauthorisedId, null, null, null, realexAuthCode, realexPassref);
    }

    public static Transaction recreatedInstance(Integer totalCost, Integer totalNetAmount, Integer totalVatAmount, String orderId, Date financiallyPassedDate, String realexAuthcode, String realexPassref) {
        return new Transaction(-1, null, null, null, orderId, false, false, totalCost, totalNetAmount, totalVatAmount, false, null, financiallyPassedDate, null, false, null, null, null, null, null, realexAuthcode, realexPassref);
    }

    /**
     * Only for tests
     */
    public static Transaction newInstance(Integer invoiceId, String invoiceNumber, boolean settlementStarted, boolean settlementEnded, Integer totalCost, Integer totalNetAmount, Integer totalVatAmount, String orderId, Long reauthorisedId) {
        return new Transaction(-1, null, invoiceId, invoiceNumber, orderId, settlementStarted, settlementEnded, totalCost, totalNetAmount, totalVatAmount, false, null, null, null, false, null, reauthorisedId, null, null, null, null, null);
    }

    public Transaction(long id,
                       String billNicHandleId,
                       Integer invoiceId,
                       String invoiceNumber,
                       String orderId,
                       boolean settlementStarted,
                       boolean settlementEnded,
                       Integer totalCost,
                       Integer totalNetAmount,
                       Integer totalVatAmount,
                       boolean cancelled,
                       Date cancelledDate,
                       Date financiallyPassedDate,
                       List<Reservation> reservations,
                       boolean invalidated,
                       Date invalidatedDate,
                       Long reauthorisedId,
                       Date settlementDate,
                       PaymentMethod paymentMethod,
                       OperationType operationType,
                       String realexAuthCode,
                       String realexPassRef) {
        //generated by db
        this.id = id;
        this.billNicHandleId = billNicHandleId;
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.settlementStarted = settlementStarted;
        this.settlementEnded = settlementEnded;
        this.totalCost = totalCost;
        this.totalNetAmount = totalNetAmount;
        this.totalVatAmount = totalVatAmount;
        this.cancelled = cancelled;
        this.cancelledDate = cancelledDate;
        this.financiallyPassedDate = financiallyPassedDate;
        this.reservations = reservations;
        this.invalidated = invalidated;
        this.invalidatedDate = invalidatedDate;
        this.reauthorisedId = reauthorisedId;
        this.settlementDate = settlementDate;
        this.paymentMethod = paymentMethod;
        this.operationType = operationType;
        this.realexAuthCode = realexAuthCode;
        this.realexPassRef = realexPassRef;
    }

    public long getId() {
        return id;
    }

    public String getBillNicHandleId() {
        return billNicHandleId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public boolean isSettlementStarted() {
        return settlementStarted;
    }

    public boolean isSettlementEnded() {
        return settlementEnded;
    }

    public Integer getTotalCost() {
        return totalCost;
    }
    
    public Integer getTotalNetAmount() {
        return totalNetAmount;
    }

    public Integer getTotalVatAmount() {
        return totalVatAmount;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setSettlementStarted(boolean settlementStarted) {
        this.settlementStarted = settlementStarted;
    }

    public void setSettlementEnded(boolean settlementEnded) {
        this.settlementEnded = settlementEnded;
    }

    public void markCancelled() {
        this.cancelled = true;
        this.cancelledDate = new Date();
    }

    public Date getFinanciallyPassedDate() {
        return financiallyPassedDate;
    }

    public void setFinanciallyPassedDate(Date financiallyPassedDate) {
        this.financiallyPassedDate = financiallyPassedDate;
    }

    public boolean isFinanciallyPassed() {
        return financiallyPassedDate != null;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public String getOrderId() {
   		return orderId;
   	}

    public void markInvalidated() {
        this.invalidated = true;
        this.invalidatedDate = new Date();
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public Date getInvalidatedDate() {
        return invalidatedDate;
    }

    public Long getReauthorisedId() {
        return reauthorisedId;
    }

    public void setReauthorisedId(Long reauthorisedId) {
        this.reauthorisedId = reauthorisedId;
    }
    
    public String getRealexAuthCode() {
		return realexAuthCode;
	}
    
    public String getRealexPassRef() {
		return realexPassRef;
	}

    @Override
    public String toString() {
        return String.format("Transaction[id=%s, invoiceId=%s, invoiceNumber=%s, billingNH=%s, orderId=%s, cancelled=%s, invalidated=%s, paymentMethod=%s, operationType=%s]",
                id, invoiceId, invoiceNumber, billNicHandleId, orderId, cancelled, invalidated, getPaymentMethod(), getOperationType());
    }
    
    public boolean isADPTransaction() {
    	for (Reservation reservation : getReservations()) {
            if (reservation.getPaymentMethod() != PaymentMethod.ADP) {
            	return false;
            }
        }
        return true;
    }

	public String getInvoiceNumber() {
		return invoiceNumber;
	}
}
