package pl.nask.crs.commons.utils;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;


public class CollectionUtilsTest {

	
	@Test
	public void toStringShouldHandleNullCollection() {
		String anySeparator = null;
		String result = CollectionUtils.toString(null, anySeparator );
		
		Assert.assertEquals("", result);
	}
	
	@Test
	public void toStringShouldHandleEmptyCollection() {
		String anySeparator = null;
		String result = CollectionUtils.toString(Collections.EMPTY_SET, anySeparator );
		
		Assert.assertEquals("", result);
	}
	
	@Test
	public void toStringShouldHandleEmptySeparator() {
		String result = CollectionUtils.toString(Arrays.asList("a", "b"), "");
		
		Assert.assertEquals("ab", result);
	}
	
	@Test
	public void toStringShouldHandleNonEmptySeparator() {
		String result = CollectionUtils.toString(Arrays.asList("a", "b"), "bla12312@@@");
		
		Assert.assertEquals("abla12312@@@b", result);
	}
	
	@Test
	public void toStringShouldHandleNullCollectionElements() {
		String result = CollectionUtils.toString(Arrays.asList((String) null), ",");		
		Assert.assertEquals("null", result);
	}
	
	@Test
	public void toStringShouldSkipNullElementsIfAsked() {
		String result = CollectionUtils.toString(Arrays.asList((String) null), true, ",");		
		Assert.assertEquals("", result);
	}
	
	@Test
	public void toStringShouldHandleEmptyCollectionElements() {
		String result = CollectionUtils.toString(Arrays.asList("", "", ""), ",");		
		Assert.assertEquals(",,", result);
	}
	
	@Test
	public void arrayAsStringShouldHandleEmptyArray() {
		Assert.assertTrue(CollectionUtils.arrayAsSet().isEmpty());
	}
	
	@Test
	public void arrayAsStringShouldHandleArrayWithNonNullElements() {
		Assert.assertTrue(CollectionUtils.arrayAsSet("a", "b").size() == 2);
	}
	
	@Test
	public void arrayAsStringShouldHandleArrayWithNullElements() {
		Assert.assertTrue(CollectionUtils.arrayAsSet("a", null).size() == 1);
	}
}
