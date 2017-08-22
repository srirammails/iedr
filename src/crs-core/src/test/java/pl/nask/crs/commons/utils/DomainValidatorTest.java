package pl.nask.crs.commons.utils;

import org.testng.annotations.Test;
import pl.nask.crs.commons.dns.validator.DomainNameValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;

/**
 * (C) Copyright 2011 NASK
 * Software Research & Development Department
 */
public class DomainValidatorTest {

    @Test
    public void domainValidatorTest() {
        DomainNameValidator.validateName("q.qq.valid.uk");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void invalidDomain1() {
        DomainNameValidator.validateName("Passw0rd!");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void invalidDomain2() {
        DomainNameValidator.validateName(".qq");
    }

    @Test
    public void ieDomainValidatorTest() {
        DomainNameValidator.validateIedrName("valid.ie");
        DomainNameValidator.validateIedrName("1a.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void oneLetterDomain() {
        DomainNameValidator.validateIedrName("q.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void twoLetterDomain() {
        DomainNameValidator.validateIedrName("qq.ie");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void ieDomainWithIncorectPostfix() {
        DomainNameValidator.validateIedrName("domain.qq");
    }

    @Test(expectedExceptions = InvalidDomainNameException.class)
    public void idnDomain() {
        DomainNameValidator.validateIedrName("xn--domain.ie");
    }
    
    @Test
    public void domainWithHyphens() {
        DomainNameValidator.validateName("ns1.d-n-a.net");
    }
    
    @Test
    public void ieDomainWithHyphens() {
        DomainNameValidator.validateIedrName("d-n-a.ie");
    }
    


}
