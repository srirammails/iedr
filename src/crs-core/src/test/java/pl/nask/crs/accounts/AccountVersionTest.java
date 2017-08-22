package pl.nask.crs.accounts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import javax.annotation.Resource;

import pl.nask.crs.account.AccountVersionService;
import pl.nask.crs.commons.AbstractTest;
import pl.nask.crs.commons.SequentialNumberGenerator;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */

public class AccountVersionTest extends AbstractTest {

    @Resource(name="accountVersionService")
    AccountVersionService service;
    
    @Resource(name="doaNumberGenerator")
    SequentialNumberGenerator doaNumberGenerator;

//TODO: CRS-72
//    @Test
//    public void getNextTest() {
//        int next = service.getNextAccountVersion();
//        AssertJUnit.assertEquals(1, next);
//        next = service.getNextAccountVersion();
//        AssertJUnit.assertEquals(2, next);
//        next = service.getNextAccountVersion();
//        AssertJUnit.assertEquals(3, next);
//        next = service.getNextAccountVersion();
//        AssertJUnit.assertEquals(4, next);
//    }
//    
//    @Test
//    public void doaGetNextTest() {
//        long next = doaNumberGenerator.getNextId();
//        AssertJUnit.assertEquals(1, next);
//        next = doaNumberGenerator.getNextId();
//        AssertJUnit.assertEquals(2, next);
//        next = doaNumberGenerator.getNextId();
//        AssertJUnit.assertEquals(3, next);
//        next = doaNumberGenerator.getNextId();
//        AssertJUnit.assertEquals(4, next);
//    }
}
