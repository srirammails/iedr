package pl.nask.crs.invoicing;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import pl.nask.crs.invoicing.service.impl.MultilinedStringBuilder;

public class MultilinedStringBuilderTest {
	
	@Test
	public void testSimpleTwoLiner() {
		String text = "line1, line2";
		
		String res = MultilinedStringBuilder.buildMultilinedFrom(text, ",");
		AssertJUnit.assertEquals("line1\nline2", res);
	}
	
	@Test
	public void testSimpleTwoLinerEndingWithEmptyLine() {
		String text = "line1, line2\n";
		
		String res = MultilinedStringBuilder.buildMultilinedFrom(text, ",");
		AssertJUnit.assertEquals("line1\nline2", res);
	}
	
	@Test
	public void testSimpleTwoLinerWithManyEmptyLines() {
		String text = "line1,\n, line2\n,";
		
		String res = MultilinedStringBuilder.buildMultilinedFrom(text, ",");
		AssertJUnit.assertEquals("line1\nline2", res);
	}
	
	@Test
	public void testWithEmptyString() {
		String text = "    \n";
		
		String res = MultilinedStringBuilder.buildMultilinedFrom(text, ",");
		AssertJUnit.assertEquals("", res);
	}
	
	@Test
	public void testWithNullString() {
		String text = null;
		
		String res = MultilinedStringBuilder.buildMultilinedFrom(text, ",");
		AssertJUnit.assertEquals("", res);
	}
}
