package pl.nask.crs.app;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import pl.nask.crs.app.authorization.aspects.AuthorizationAspect;
import pl.nask.crs.app.commons.CommonAppService;
import pl.nask.crs.app.config.ConfigAppService;
import pl.nask.crs.app.document.DocumentAppService;
import pl.nask.crs.app.domains.BulkTransferAppService;
import pl.nask.crs.app.domains.DomainAppService;
import pl.nask.crs.app.invoicing.InvoicingAppService;
import pl.nask.crs.app.nichandles.NicHandleAppService;
import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.app.reports.ReportsAppService;
import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.app.triplepass.TriplePassAppService;
import pl.nask.crs.app.users.UserAppService;
import pl.nask.crs.commons.OpInfo;
import pl.nask.crs.payment.TransactionSearchCriteria;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.ticket.Ticket;

/**
 * A generic test which will check, if all 'app service' methods have the permission check done by any permission aspect.
 * 
 * @author Artur Gniadzik
 *
 */
@ContextConfiguration(locations = {"/application-services-config.xml"})
public class PermissionCheckTest extends AbstractTestNGSpringContextTests {
    private Set<Method> methodsWithoutAspects = new HashSet<Method>();
    private Set<Class> classesToCheck = new HashSet<Class>(Arrays.asList(
            DomainAppService.class,
            CommonAppService.class,
            ConfigAppService.class,
            DocumentAppService.class,
            BulkTransferAppService.class,
            InvoicingAppService.class,
            NicHandleAppService.class,
            PaymentAppService.class,
            ReportsAppService.class,
            TicketAppService.class,
            TriplePassAppService.class,
            UserAppService.class
//            SchedulerCron.class - cannot be tested here!!!
            ));
    private Set<Method> exclusions = initExclusions();

    private Set<Method> initExclusions() {
        try {
            Set<Method> e = new HashSet<Method>();
            // methods used by the scheduler
            e.add(TriplePassAppService.class.getMethod("triplePass"));
            e.add(CommonAppService.class.getMethod("cleanupTickets", AuthenticatedUser.class, OpInfo.class));
            e.add(DomainAppService.class.getMethod("runNotificationProcess", AuthenticatedUser.class));
            e.add(DomainAppService.class.getMethod("pushQ", AuthenticatedUser.class, OpInfo.class));
            e.add(InvoicingAppService.class.getMethod("runTransactionInvalidation", AuthenticatedUser.class));
            e.add(InvoicingAppService.class.getMethod("runInvoicing", AuthenticatedUser.class));
            e.add(NicHandleAppService.class.getMethod("removeDeletedNichandles"));
            e.add(PaymentAppService.class.getMethod("autorenew", AuthenticatedUser.class, String.class));
            e.add(PaymentAppService.class.getMethod("autorenewAll", AuthenticatedUser.class));
            e.add(TicketAppService.class.getMethod("sendTicketExpirationEmails", AuthenticatedUser.class));
            e.add(DomainAppService.class.getMethod("authCodeCleanup", OpInfo.class));

            // no parameters required
            e.add(UserAppService.class.getMethod("getInternalUsers"));

            // technical methods
            e.add(NicHandleAppService.class.getMethod("getUserAppService"));

            // simple dictionary method: no permission check required here
            e.add(NicHandleAppService.class.getMethod("getStatusList"));
            e.add(DomainAppService.class.getMethod("getCountries", AuthenticatedUser.class));
            e.add(DomainAppService.class.getMethod("getClassCategory", AuthenticatedUser.class));

            // internal...
            e.add(TicketAppService.class.getMethod("internalGetTicketForDomain", String.class));
            e.add(TicketAppService.class.getMethod("create", AuthenticatedUser.class, Ticket.class));

            // manual triplePass for the domain: does the same thing as the method called by the scheduler
            e.add(TriplePassAppService.class.getMethod("triplePass", AuthenticatedUser.class, String.class));
            e.add(TriplePassAppService.class.getMethod("triplePass", AuthenticatedUser.class, String.class, String.class));

            // AuthentiatedUser not passed
            e.add(UserAppService.class.getMethod("getUser", String.class));
            e.add(UserAppService.class.getMethod("generateSecret", String.class));
            e.add(PaymentAppService.class.getMethod("findAllTransactions", TransactionSearchCriteria.class, List.class));
            e.add(CommonAppService.class.getMethod("sendAuthCodeFromPortal", String.class, String.class));

            e.add(NicHandleAppService.class.getMethod("changePassword", String.class, String.class, String.class));
            e.add(NicHandleAppService.class.getMethod("resetPassword", String.class, String.class));
            return e;
        } catch (SecurityException e1) {
            e1.printStackTrace();
            throw new IllegalStateException("SecurityException: " + e1.getMessage(), e1);
        } catch (NoSuchMethodException e1) {
            throw new IllegalStateException("No such method: " + e1.getMessage(), e1);
        }
    }

	@Test
	public void testIfAllMethodsHavePermissionsChecks() {
		for (Class clazz: classesToCheck) {
			checkAnnotationsForClass(clazz);
		}
		System.out.println();
		methodsWithoutAspects.removeAll(exclusions);
		String msg = methodsWithoutAspectsAsPrintableString();
		System.out.println(msg);
		if (!methodsWithoutAspects.isEmpty()) {
			AssertJUnit.fail("There are " + methodsWithoutAspects.size() + " methods without permission aspects defined for them " + msg);
		}
	}


	private String methodsWithoutAspectsAsPrintableString() {
		
		List<Method> list = new ArrayList<Method>(methodsWithoutAspects);
		Collections.sort(list, new Comparator<Method>() {

			@Override
			public int compare(Method o1, Method o2) {
				return o1.getDeclaringClass().getName().compareTo(o2.getDeclaringClass().getName());
			}
		});
		
		StringBuilder sb = new StringBuilder("Number of methods: ").append(list.size()).append("\n");
		for (Method m: list) {
			sb.append(methodName(m)).append("\n");
		}
		return sb.toString();
	}


	private String methodName(Method m) {
		String name = m.toString();
		name = name.replaceFirst("public abstract ", "");
		int whitespaceIndex = name.indexOf(" ");
		name = name.substring(whitespaceIndex);
		int throwsIndex = name.indexOf(" throws ");
		if (throwsIndex > 0) {
			name = name.substring(0, throwsIndex);
		}
		return name.trim();
	}

	private void checkAnnotationsForClass(Class clazz) {
		inspectClassMethods(clazz);		
		Object bean = applicationContext.getBean(clazz);
		if (! (bean instanceof Advised))
			return;			
		
		
		Advised a = (Advised) bean;		
		for (Advisor adv: a.getAdvisors()) {
			if (adv instanceof AspectJPointcutAdvisor) {
				AspectJPointcutAdvisor ajadv = (AspectJPointcutAdvisor) adv;
				if (isPermissionCheck(ajadv.getAdvice())) {
					Pointcut pcut = ajadv.getPointcut();
					System.out.println("Pointcut: " + pcut);

					for (Method m : new HashSet<Method>(methodsWithoutAspects)) {
						if (pcut.getMethodMatcher().matches(m, clazz)) {
							methodsWithoutAspects.remove(m);
							System.out.println("match on " + m);
						}
					}
				}
			}
		}
	}


	private boolean isPermissionCheck(Advice advice) {
		if (! (advice instanceof AbstractAspectJAdvice)) 
			return false;
		AbstractAspectJAdvice adv = (AbstractAspectJAdvice) advice;
	
		return AuthorizationAspect.class.isAssignableFrom(adv.getAspectJAdviceMethod().getDeclaringClass());
	}

	private void inspectClassMethods(Class clazz) {
		Method[] methods = clazz.getMethods();
		for (Method m: methods) {
			methodsWithoutAspects.add(m);
		}
	}
}

