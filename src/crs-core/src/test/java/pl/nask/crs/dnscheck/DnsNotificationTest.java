package pl.nask.crs.dnscheck;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.nask.crs.dnscheck.DnsNotification;

public class DnsNotificationTest {

    @Test
    public void parseErrorMessageTest() {
        DnsNotification notif = new DnsNotification();

        notif.setErrorMessage("[11:12]\nError message");

        Assert.assertEquals(notif.getTimeOfCheck(), "11:12");
        Assert.assertEquals(notif.getCheckOutput(), "Error message");
    }

    @Test
    public void composeErrorMessageTest() {
        DnsNotification notif = new DnsNotification("nicHandleId", "email", "domainName", "nsName", "11:13", "Full check output");
        Assert.assertEquals(notif.getErrorMessage(), "[11:13]\nFull check output");
    }

    @Test
    public void handleMissingTimeTest() {
        DnsNotification notif = new DnsNotification();

        notif.setErrorMessage("[11:12almostGood");

        Assert.assertNull(notif.getTimeOfCheck());
        Assert.assertEquals(notif.getCheckOutput(), "[11:12almostGood");
    }
}
