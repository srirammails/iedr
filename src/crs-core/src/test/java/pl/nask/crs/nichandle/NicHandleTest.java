package pl.nask.crs.nichandle;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.sql.Timestamp;
import java.util.Date;

import pl.nask.crs.accounts.Account;
import pl.nask.crs.commons.utils.CollectionUtils;

public class NicHandleTest {

	@Test
	public void getPhonesAsStringTest() {
		NicHandle nh = createNHAAE359();
				
		AssertJUnit.assertEquals("123, 234", nh.getPhonesAsString());
	}
	
	public static NicHandle createNHAAE359(){
        NicHandle nh = new NicHandle("AAE359-IEDR", "Iron Mountain Inc", new Account(1, "GUEST ACCOUNT"), "Iron Mountain, Inc.", "NHAddress000007", null, null, "Delaware", "USA", "NHEmail000007@server.kom", NicHandle.NHStatus.Active, new Date(1050530400000L), new Date(61558), new Timestamp(1213632700000L), true, "Transfer to Iron Mountain, order id 20080616162237-1052-nicorette", "AAE359-IEDR", new Vat(null), "S");
        nh.setPhones(CollectionUtils.arrayAsSet("123", "234"));
        return nh;
    }
}
