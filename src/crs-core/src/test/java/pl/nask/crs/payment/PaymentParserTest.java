package pl.nask.crs.payment;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import pl.nask.crs.commons.utils.ClasspathResourceReader;

@Test
public class PaymentParserTest {

	@BeforeClass
	public void setupXmlUnit() {
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
	}
	
	
	@DataProvider(name="requestsToTest")
	public Object[][] requestsToTest() {
		return new Object[][] {
			{"settle transaction", makePaymentRequestAuthenticatedInstance(TransactionType.TYPE_SETTLE), "payments/settleTransactionRequest.xml"},
			{"cancel transaction", makePaymentRequestAuthenticatedInstance(TransactionType.TYPE_VOID), "payments/cancelTransactionRequest.xml"},
			{"authorize CC payment", makeCCPaymentRequest(), "payments/ccPaymentRequest.xml"},
			{"authorize CC payment", makeCCPaymentRequest2DigitCVN(), "payments/ccPaymentRequest2digitCvn.xml"},
			{"authorize Debit payment", makeDebitPaymentRequest(), "payments/debitPaymentRequest.xml"}
		};
	}
	
	private ExtendedPaymentRequest makeCCPaymentRequest() {
		PaymentRequest paymentRequest = new PaymentRequest("EUR", 1000, "cardNumber", "expDate", "holderName", "VISA", 111, 1);
		return ExtendedPaymentRequest.newCreditInstance("account", "merchantId", "password", paymentRequest );
	}
	
	private ExtendedPaymentRequest makeCCPaymentRequest2DigitCVN() {
		PaymentRequest paymentRequest = new PaymentRequest("EUR", 1000, "cardNumber", "expDate", "holderName", "VISA", 11, 1);
		return ExtendedPaymentRequest.newCreditInstance("account", "merchantId", "password", paymentRequest );
	}
	
	private ExtendedPaymentRequest makeDebitPaymentRequest() {
		PaymentRequest paymentRequest = new PaymentRequest("EUR", 1000, "cardNumber", "expDate", "holderName", "VISA", 111, 1);
		return ExtendedPaymentRequest.newDebitInstance("account", "merchantId", "password", paymentRequest);
	}


	@Test(dataProvider="requestsToTest")
	public void testToXML(String testName, ExtendedPaymentRequest request, String expectedXmlPath) {		
		String result = PaymentParser.toXML(request);
		String expected = ClasspathResourceReader.readString(expectedXmlPath);
		assertResultMatches(expected, result, testName);
	}
	
	@Test
	public void testFromXML() throws ParserException {
		String response = ClasspathResourceReader.readString("payments/sampleResponse.xml");
		PaymentResponse paymentResponse = PaymentParser.fromXML(response);
		AssertJUnit.assertEquals("authcode", "125443", paymentResponse.getAuthcode());
		AssertJUnit.assertEquals("result", "00", paymentResponse.getResult());
		AssertJUnit.assertEquals("message", "[ test system ] AUTH CODE 125443", paymentResponse.getMessage());
		AssertJUnit.assertEquals("pasref", "12895664833336", paymentResponse.getPasref());
		AssertJUnit.assertEquals("bank", "AIB BANK", paymentResponse.getBank());
		AssertJUnit.assertEquals("country", "IRELAND", paymentResponse.getCountry());
	}

	private void assertResultMatches(String expected, String result, final String testName) {
		try {
			Diff diff = new Diff(expected, result);
						diff.overrideDifferenceListener(new MyDifferenceListener() );
			AssertJUnit.assertTrue(testName + " failed: " + diff, diff.similar());
		} catch (IOException e) {
			AssertJUnit.fail("IOException while running test " + testName + ": " + e);
		} catch (SAXException e) {
			AssertJUnit.fail("SAXException while running test " + testName + ": " + e);
		}
	}

	private ExtendedPaymentRequest makePaymentRequestAuthenticatedInstance(TransactionType type) {
		ExtendedPaymentRequest req = ExtendedPaymentRequest.authenticatedInstance("account", "merchantId", "password", "authcode", "passref", "orderId");
		req.setType(type);
		return req;
	}

	class MyDifferenceListener implements DifferenceListener {
		private Set<String> xpathsToSkip = new HashSet<String>(
				Arrays.asList(
						"/request[1]/@timestamp",
						"/request[1]/md5hash[1]/text()[1]",
						"/request[1]/orderid[1]/text()[1]"
						));
		
		
		@Override
		public void skippedComparison(Node control, Node test) {

		}
		
		@Override
		public int differenceFound(Difference difference) {
			
			if (shouldBeSkipped(difference)) {
				return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
			} else {
				return 0;
			}
		}

		private boolean shouldBeSkipped(Difference difference) {
			return difference.getControlNodeDetail().getXpathLocation().equals(difference.getTestNodeDetail().getXpathLocation())
					&&
				   xpathsToSkip.contains(difference.getControlNodeDetail().getXpathLocation());
		}
	};


}
