package pl.nask.crs.payment;

import org.testng.annotations.Test;

public class ExtendedPaymentRequestTest {
	@Test
	public void paymentAccountFieldMayBeEmpty() {
		// regression: not expecting any validation exceptions here! 
		ExtendedPaymentRequest.authenticatedInstance("", "merchantId", "password", "authcode", "passref", "orderId");
	}
}
