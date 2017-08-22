package pl.nask.crs.api.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.DepositInfo;
import pl.nask.crs.payment.DepositTransactionType;

/**
 * @author: Marcin Tkaczyk
 */

@XmlRootElement
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositVO {

    private String nicHandleId;
    private String nicHandleName;
    private Date transactionDate;
    private Double openBal;

    private Double closeBal;
    private BigDecimal reservedFunds;
    private BigDecimal availableFunds;

    private Double transactionAmount;
    private DepositTransactionType transactionType;

    private String orderId;
    private String remark;

    public DepositVO() {}

    public DepositVO(DepositInfo depositInfo) {
        this.nicHandleId = depositInfo.getNicHandleId();
        this.nicHandleName = depositInfo.getNicHandleName();
        this.transactionDate = depositInfo.getTransactionDate();
        this.openBal = depositInfo.getOpenBal();
        this.closeBal = depositInfo.getCloseBal();
        this.reservedFunds = depositInfo.getReservedFunds();
        this.availableFunds = depositInfo.getCloseBalIncReservaions();
        this.transactionAmount = depositInfo.getTransactionAmount();
        this.transactionType = depositInfo.getTransactionType();
        this.orderId = depositInfo.getOrderId();
        this.remark = depositInfo.getRemark();
    }

    public DepositVO(Deposit deposit) {
        this.nicHandleId = deposit.getNicHandleId();
        this.nicHandleName = deposit.getNicHandleName();
        this.transactionDate = deposit.getTransactionDate();
        this.openBal = deposit.getOpenBal();
        this.closeBal = deposit.getCloseBal();
        this.transactionAmount = deposit.getTransactionAmount();
        this.transactionType = deposit.getTransactionType();
        this.orderId = deposit.getOrderId();
        this.remark = deposit.getRemark();
    }

}
