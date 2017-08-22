package pl.nask.crs.commons.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;


public class ValidatorTest {

	@Test
	public void assertNotEmptyShouldFailForNullString() {
		try {			
			Validator.assertNotEmpty((String) null, "valueName");
			Assert.fail("Expected to fail with IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertNotEmptyShouldFailForNullCollection() {
		try {			
			Validator.assertNotEmpty((Collection<?>) null, "valueName");
			Assert.fail("Expected to fail with IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}

	@Test
	public void assertNotEmptyShouldFailForEmptyString() {
		try {			
			Validator.assertNotEmpty("", "valueName");
			Assert.fail("Expected to fail with IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertNotEmptyShouldFailForEmptyCollection() {
		try {			
			Validator.assertNotEmpty(Collections.EMPTY_SET, "valueName");
			Assert.fail("Expected to fail with IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertNotEmptyShouldFailForWhitespaces() {
		try {			
			Validator.assertNotEmpty("  \n\t", "valueName");
			Assert.fail("Expected to fail with IllegalArgumentException!");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}

	@Test
	public void assertNotEmptyShouldNotFailForNotEmptyString() {
		Validator.assertNotEmpty("a string", "valueName");
	}
	
	@Test
	public void assertNotEmptyShouldNotFailForNotEmptyCollection() {
		Validator.assertNotEmpty(Collections.singleton("a"), "valueName");
	}
	
	@Test
	public void isEmptyShouldBeTrueForNullString() {
		Assert.assertTrue(Validator.isEmpty((String) null));
	}
	
	@Test
	public void isEmptyShouldBeTrueForNullCollection() {
		Assert.assertTrue(Validator.isEmpty((Collection<?>) null));
	}
	
	@Test
	public void isEmptyShouldBeTrueForEmptyString() {
		Assert.assertTrue(Validator.isEmpty(""));
	}
	
	@Test
	public void isEmptyShouldBeTrueForEmptyCollection() {
		Assert.assertTrue(Validator.isEmpty(Collections.EMPTY_SET));
	}
	
	@Test
	public void isEmptyShouldBeTrueForWhitespaces() {
		Assert.assertTrue(Validator.isEmpty("  \n\t"));
	}
	
	@Test
	public void isEmptyShouldBeFalseForNotEmptyString() {
		Assert.assertFalse(Validator.isEmpty("a string"));
	}

	@Test
	public void isEmptyShouldBeFalseForNotEmptyCollection() {
		Assert.assertFalse(Validator.isEmpty(Collections.singleton("a")));
	}

	
	private void assertExceptionMessage(IllegalArgumentException e, String paramName) {
		Assert.assertTrue("Exception message should contain info about the validated param name", e.getMessage().contains(paramName));
	}

	@Test
	public void assertNotNullShouldFailForNullValue() {
		try {
			Validator.assertNotNull(null, "valueName");
			Assert.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertNotNullShouldNotFailForNonNullValue() {
		Validator.assertNotNull("aaa", "valueName");
	}

	public void assertNullShouldFailForNonNullValue() {
		try {
			Validator.assertNull("a", "valueName");
			Assert.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertNullShouldNotFailForNullValue() {
		Validator.assertNull(null, "valueName");
	}

	@Test
	public void assertTrueShouldFailForFalseValue() {
		try {
			Validator.assertTrue(false, "valueName");
			Assert.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertExceptionMessage(e, "valueName");
		}
	}
	
	@Test
	public void assertTrueShouldNotFailForTrueValue() {
		Validator.assertTrue(true, "valueName");
	}
	
    @Test
    public void hasDuplicatesShouldReturnFalseIfCollectionElementsAreUnique() {
        Assert.assertFalse(Validator.hasDuplicates(Arrays.asList("a", "b", "c", "d")));
    }
    
    @Test
    public void hasDuplicatesShouldReturnTrueIfCollectionHasDuplicatedElements() {
        Assert.assertTrue(Validator.hasDuplicates(Arrays.asList("a", "b", "c", "b")));
    }
    
    @Test
    public void getDuplicatesShouldReturnNullIfCollectionElementsAreUnique() {
    	Assert.assertNull(Validator.getDuplicates(Arrays.asList("a", "b", "c", "d")));
    }
    
    @Test
    public void getDuplicatesShouldReturnDuplicatedElementIfCollectionHasDuplicatedElements() {
    	Assert.assertEquals("b", Validator.getDuplicates(Arrays.asList("a", "b", "c", "b")));
    }

    
    @Test
    public void isEqualShouldBeTrueForNullArguments() {
    	Assert.assertTrue(Validator.isEqual(null, null));
    }
    
    @Test
    public void isEqualShouldBeTrueForEqualArguments() {
    	Assert.assertTrue(Validator.isEqual("a", "a"));
    }
    
    @Test
    public void isEqualShouldBeFalseIfOneArgumentIsNull() {
    	Assert.assertFalse(Validator.isEqual("a", null));
    	Assert.assertFalse(Validator.isEqual(null, "a"));
    }
    
    @Test
    public void isEqualShouldBeFalseIfArgumentsAreNotEqual() {
    	Assert.assertFalse(Validator.isEqual("a", "b"));
    }
}
