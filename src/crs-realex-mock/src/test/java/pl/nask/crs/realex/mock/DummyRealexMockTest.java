package pl.nask.crs.realex.mock;

import org.testng.Assert;
import org.testng.annotations.Test;



public class DummyRealexMockTest {
	DummyRealexMock realexMock;
		
	@Test
	public void testConstructorWillNotFail() {
		realexMock = new DummyRealexMock();		
	}
	
	@Test(dependsOnMethods="testConstructorWillNotFail")
	public void testMockReturnsAMessage() {
		String anyMessage = "any message, doesn't matter";
		String response = realexMock.processMessage(anyMessage );
		
		Assert.assertNotNull(response);
	}
}
