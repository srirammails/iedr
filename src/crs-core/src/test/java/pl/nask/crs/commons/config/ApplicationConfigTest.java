package pl.nask.crs.commons.config;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.utils.CollectionUtils;

public class ApplicationConfigTest extends AbstractTest {
	@Autowired
	ApplicationConfig applicationConfig;
	
	@Test
	public void allValuesShouldBeSet() {
		List<String> errors = new LinkedList<String>();
		for (Method m: ApplicationConfig.class.getMethods()) {
			if (Modifier.isPublic(m.getModifiers()) && (m.getName().startsWith("get") || m.getName().startsWith("is"))) {
				checkIfMethodReturnsValue(m, errors);
			}
		}
		
		if (!errors.isEmpty()) {
			AssertJUnit.fail("Following configuration errors (configuration missing) detected: \n" + CollectionUtils.toString(errors, "\n"));
		}
	}

	private void checkIfMethodReturnsValue(Method m, List<String> errors) {
		try {
			m.invoke(applicationConfig);
		} catch (InvocationTargetException e) {
			errors.add(m.getName() + " : " + e.getTargetException());
		} catch (IllegalArgumentException e) {
			errors.add(m.getName() + " : " + e);
		} catch (IllegalAccessException e) {
			errors.add(m.getName() + " : " + e);
		}
	}
}
