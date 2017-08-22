package pl.nask.crs.payment;

import pl.nask.crs.commons.utils.Validator;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Date;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * @author: Marcin Tkaczyk
 */
public class ExtendedPaymentRequest {

	private final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

    private final PaymentRequest paymentRequest;

	// Realex credentials
    private final String account;
    private final String merchantId;
    private final String password;

    // generated with #generateMDHash()
    private String md5Hash;
	private String timestamp;

    private String orderId;
    
    private TransactionType type;
    
    private boolean autosettle;
    private String passref;
    private String authcode;
    private String bank;
    private String country;

    public static ExtendedPaymentRequest newCreditInstance(String account, String merchantId, String password, PaymentRequest paymentRequest) {
        return new ExtendedPaymentRequest(account, merchantId, password, TransactionType.TYPE_AUTH, paymentRequest, false);
    }

    public static ExtendedPaymentRequest newDebitInstance(String account, String merchantId, String password, PaymentRequest paymentRequest) {
        return new ExtendedPaymentRequest(account, merchantId, password, TransactionType.TYPE_AUTH, paymentRequest, true);
    }

    public static ExtendedPaymentRequest authenticatedInstance(String account, String merchantId, String password, String authcode, String passref, String orderId) {
        return new ExtendedPaymentRequest(account, merchantId, password, authcode, passref, orderId);
    }

    private ExtendedPaymentRequest(String account, String merchantId, String password, TransactionType transactionType, PaymentRequest paymentRequest, boolean autosettle) {
        Validator.assertNotEmpty(merchantId, "merchant id");
        Validator.assertNotEmpty(password, "password");
        Validator.assertNotNull(transactionType, "transaction type");
        this.paymentRequest = paymentRequest;
        this.account = account;
        this.merchantId = merchantId;
        this.password = password;
        this.type = transactionType;
        this.autosettle = autosettle;
        resetTimestamp();
        generateOrderId();
    }

    private ExtendedPaymentRequest(String account, String merchantId, String password, String authcode, String passref, String orderId) {
        Validator.assertNotEmpty(merchantId, "merchant id");
        Validator.assertNotEmpty(password, "password");
        Validator.assertNotEmpty(authcode, "authcode");
        Validator.assertNotEmpty(passref, "passref");
        Validator.assertNotEmpty(orderId, "orderId");
        this.paymentRequest = null;
        this.account = account;
        this.merchantId = merchantId;
        this.password = password;
        this.authcode = authcode;
        this.passref = passref;
        this.orderId = orderId;
        resetTimestamp();
    }

    public String getCurrency() {
        return paymentRequest.getCurrency();
    }

    public Integer getAmount() {
        return paymentRequest.getAmountInLowestCurrencyUnit();
    }

    public String getCardNumber() {
        return paymentRequest.getCardNumber();
    }

    public String getCardExpDate() {
        return paymentRequest.getCardExpDate();
    }

    public String getCardType() {
        return paymentRequest.getCardType();
    }

    public String getCardHolderName() {
        return paymentRequest.getCardHolderName();
    }

    public Integer getCvnNumber() {
        return paymentRequest.getCvnNumber();
    }

    public Integer getCvnPresenceIndicator() {
        return paymentRequest.getCvnPresenceIndicator();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAccount() {
        return account;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getPassword() {
        return password;
    }
    
    public String getMd5Hash() {
        generateMD5Hash();
    	return md5Hash;
    }

    public String getOrderId() {
    	return orderId;
    }
    
    public boolean isAutosettle() {
    	return autosettle;
    }
    
    public String getPassref() {
    	return passref;
    }
    
    public String getAuthcode() {
    	return authcode;
    }
    
    public String getBank() {
    	return bank;
    }
    
    public String getCountry() {
    	return country;
    }

    public void setAuthorisationData(String authcode, String passref, String bank, String country) {
        this.authcode = authcode;
        this.passref = passref;
        this.bank = bank;
        this.country = country;
    }

    public TransactionType getType() {
    	return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    private void generateMD5Hash() {
        String tmp = this.timestamp + "." + this.merchantId + "." + this.orderId;

        if (this.getType().equals(TransactionType.TYPE_AUTH))
            tmp += ("." + this.paymentRequest.getAmountInLowestCurrencyUnit() + "." + this.paymentRequest.getCurrency()+ "." + this.paymentRequest.getCardNumber());
        else
            tmp += "...";

        this.md5Hash = getMD5(tmp);
        tmp = this.md5Hash + "." + this.password;
        this.md5Hash = getMD5(tmp);
    }

    private String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateOrderId() {
        Random r = new Random();
        this.orderId = this.timestamp + "-C-" + r.nextInt(9999999);
    }

    private void resetTimestamp() {
        this.timestamp = FORMATTER.format(new Date());
    }

}
