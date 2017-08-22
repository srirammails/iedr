package pl.nask.crs.user.algorithm;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class SaltedAlgorithmTest {

    @Test
    public void test() {
        String plainPass = "123456";
        SaltedAlgorithm saltedAlgorithm = new SaltedAlgorithm();

        String salt = "hwRQ0976UdDZkfmtBsrF4O";
        String hash = "vdQUE4BhWn77fkZ2XDMuKgUn7s64P82";
        AssertJUnit.assertEquals(hash, saltedAlgorithm.hashString(plainPass, salt));

        salt = "FRrT9ELQQNOdwrlQzIQbuu";
        hash = "66m74LXEBp0lQ/60FyZCArRZZZKwPaC";
        AssertJUnit.assertEquals(hash, saltedAlgorithm.hashString(plainPass, salt));

        salt = "CLnMyL/Lvei2DzjDZTWeLe";
        hash = "jiVrqxNmyiUfFnEM3Ha4sKAxOrlxmfO";
        AssertJUnit.assertEquals(hash, saltedAlgorithm.hashString(plainPass, salt));

    }

    @Test(expectedExceptions = Exception.class)
    public void testException() {
        String plainPass = "123456";
        SaltedAlgorithm saltedAlgorithm = new SaltedAlgorithm();
        String salt = null;
        String hash = "jiVrqxNmyiUfFnEM3Ha4sKAxOrlxmfO";
        AssertJUnit.assertEquals(hash, saltedAlgorithm.hashString(plainPass, salt));

    }
}
