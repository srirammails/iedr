package pl.nask.crs.commons.email;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.nask.crs.commons.email.Email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class EmailTest {

    @Test
    public void addElementToBccListTest() throws Exception {
        Email email = new Email();
        email.setBccList(Arrays.asList("bcc@bcc.bcc"));
        String specialBccField = "additional@bbc.bbc";
        List<String> newBccList = new ArrayList<String>(email.getBccList());
        newBccList.add(specialBccField);
        email.setBccList(newBccList);
        Assert.assertEquals(2, email.getBccList().size());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void failModifyBccListTest() throws Exception {
        Email email = new Email();
        email.setBccList(Arrays.asList("bcc@bcc.bcc"));
        String specialBccField = "additional@bbc.bbc";
        List<String> newBccList = email.getBccList();
        newBccList.add(specialBccField);
    }

}
