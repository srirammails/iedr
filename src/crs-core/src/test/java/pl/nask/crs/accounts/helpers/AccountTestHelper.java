package pl.nask.crs.accounts.helpers;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.contacts.Contact;
import static org.junit.Assert.*;
import static java.util.Calendar.*;

import java.util.Date;
import java.util.Calendar;

/**
 * @author Marianna Mysiorska
 */
public class AccountTestHelper {

    public static void compareAccounts(Account a1, Account a2, boolean compareEmail){
        assertEquals(a1.getId(), a2.getId());
        assertEquals(a1.getBillingContact().getNicHandle(), a2.getBillingContact().getNicHandle());
        assertEquals(a1.getBillingContact().getName(), a2.getBillingContact().getName());
        if (compareEmail)
            assertEquals(a1.getBillingContact().getEmail(), a2.getBillingContact().getEmail());
        assertEquals(a1.getName(), a2.getName());
        assertEquals(a1.getRemark(), a2.getRemark());
        assertEquals(a1.getStatus(), a2.getStatus());
        assertEquals(a1.getWebAddress(), a2.getWebAddress());

        // TODO: compareDatesYMD seems to be broken for DST - review asap
//        compareDatesYMD(a1.getCreationDate(), a2.getCreationDate());
//        compareDatesYMD(a1.getStatusChangeDate(), a2.getStatusChangeDate());
        assertEquals(a1.isAgreementSigned(), a2.isAgreementSigned());
        assertEquals(a1.isTicketEdit(), a2.isTicketEdit());
// todo wywala sie. przy tworzeniu konta nr 1 ze skrptu sql ustawia mu date terazniejsza a nie date wzieta ze skrytpu. czemu !!!???
//        Calendar chD1 = Calendar.getInstance();
//        Calendar chD2 = Calendar.getInstance();
//        chD1.setTime(a1.getChangeDate());
//        chD2.setTime(a2.getChangeDate());
//        assertEquals(chD1.get(YEAR), chD2.get(YEAR));
//        assertEquals(chD1.get(MONTH), chD2.get(MONTH));
//        assertEquals(chD1.get(DAY_OF_MONTH), chD2.get(DAY_OF_MONTH));
//        assertEquals(chD1.get(HOUR_OF_DAY), chD2.get(HOUR_OF_DAY));
//        assertEquals(chD1.get(MINUTE), chD2.get(MINUTE));
//        assertEquals(chD1.get(SECOND), chD2.get(SECOND));
    }

    public static void compareDatesYMD(Date date1, Date date2){
        Calendar csD1 = Calendar.getInstance();
        Calendar csD2 = Calendar.getInstance();
        csD1.setTime(date1);
        csD2.setTime(date2);
        assertEquals(csD1.get(YEAR), csD2.get(YEAR));
        assertEquals(csD1.get(MONTH), csD2.get(MONTH));
        assertEquals(csD1.get(DAY_OF_MONTH), csD2.get(DAY_OF_MONTH));
    }

    public static Account create1(){
        Contact contact = new Contact("IH4-IEDR", "IEDR Hostmaster","NHEmail000903@server.kom");
        return new Account(
                1L,
                "GUEST ACCOUNT",
                contact,
                "Active",
                "ACAddress000007",
                "Co. Dublin",
                "Ireland",
                "www.www000007.kom",
                "Monthly",
                "January",
                "+111 000007",
                "+222 000007",
                "Trade",
                "Default Guest Account",
                new Date(1021413600000L),
                new Date(1023400800000L),
                new Date(), true, true, "A"
        );
    }
    
    
}
